package co.com.quinteropo.core.quinteropobank.core.service;


import co.com.quinteropo.core.quinteropobank.common.enums.MovementType;
import co.com.quinteropo.core.quinteropobank.common.mapper.TransferMapper;
import co.com.quinteropo.core.quinteropobank.common.request.CreateTransferRequest;
import co.com.quinteropo.core.quinteropobank.domain.model.TransferRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final MovementService movementService;
    private final BankAccountService bankAccountService;

    public TransferService(TransferRepository transferRepository, MovementService movementService, BankAccountService bankAccountService) {
        this.transferRepository = transferRepository;
        this.movementService = movementService;
        this.bankAccountService = bankAccountService;
    }

    public void deleteTransfer(long id) {
        log.info("Inside TransferService::deleteTransfer id: {}", id);
        transferRepository.deleteById(id);
    }

    public Page<TransferRecord> findAll(int page, int size) {
        log.info("Inside TransferService::findAll page: {} size: {}", page, size);
        return transferRepository.findAll(PageRequest.of(page, size));
    }

    public TransferRecord findById(long id) {
        return transferRepository.findById(id).orElseThrow(() -> new RuntimeException("Transfer not found"));
    }

    @Transactional(rollbackOn = Exception.class)
    public TransferRecord createTransfer(CreateTransferRequest createTransferRequest) {
        log.info("Inside TransferService::createTransfer createTransferRequest: {}", createTransferRequest);
        double amount = createTransferRequest.getAmount();
        double balanceAccount = bankAccountService.getBalanceByBankAccount(createTransferRequest.getBankAccountOriginId());
        String description = generateDescription(createTransferRequest.getBankAccountOriginId(), createTransferRequest.getBankAccountDestinationId());

        if (balanceAccount < amount) {
            throw new ArithmeticException("Insufficient funds");
        }

        movementService.createMovement(createTransferRequest.getBankAccountOriginId(), MovementType.WITHDRAWAL.name(), amount, createTransferRequest.getDescription());
        movementService.createMovement(createTransferRequest.getBankAccountDestinationId(), MovementType.DEPOSIT.name(), amount, createTransferRequest.getDescription());
        return transferRepository.save(TransferMapper.toCreateTransferRecord(
                createTransferRequest.getCategoryId(), createTransferRequest.getBankAccountOriginId(), createTransferRequest.getBankAccountDestinationId(),
                createTransferRequest.getAmount(), description, MovementType.EXTERNAL_TRANSFER.name()
        ));
    }


    private String generateDescription(long origin, long destination){
        return "Se registro una transferencia desde la cuenta origen: " + origin + " hacia la cuenta destino: " + destination;
    }
}
