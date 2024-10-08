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

import java.util.List;

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
        double balanceAccountOrigin = bankAccountService.getBalanceByBankAccount(createTransferRequest.getBankAccountOriginId());
        double balanceAccountDestination = bankAccountService.getBalanceByBankAccount(createTransferRequest.getBankAccountDestinationId());
        String description = generateDescription(createTransferRequest.getBankAccountOriginId(), createTransferRequest.getBankAccountDestinationId());
        double balance = balanceAccountOrigin - amount;

        if (balanceAccountOrigin < amount) {
            throw new ArithmeticException("Insufficient funds");
        }
        log.info("balance {}", balance);
        movementService.createMovement(createTransferRequest.getBankAccountOriginId(), MovementType.WITHDRAWAL.name(), amount, createTransferRequest.getDescription());
        movementService.createMovement(createTransferRequest.getBankAccountDestinationId(), MovementType.DEPOSIT.name(), amount, createTransferRequest.getDescription());
        bankAccountService.updateBalance(createTransferRequest.getBankAccountOriginId(), balance);
        bankAccountService.updateBalance(createTransferRequest.getBankAccountDestinationId(), balanceAccountDestination + amount);
        return transferRepository.save(TransferMapper.toCreateTransferRecord(
                createTransferRequest.getCategoryId(), createTransferRequest.getBankAccountOriginId(), createTransferRequest.getBankAccountDestinationId(),
                createTransferRequest.getAmount(), description, MovementType.EXTERNAL_TRANSFER.name()
        ));
    }


    private String generateDescription(long origin, long destination){
        return "Se registro una transferencia desde la cuenta origen: " + origin + " hacia la cuenta destino: " + destination;
    }

    public List<TransferRecord> findAllByBankAccountOriginId(long bankAccountOriginId) {
        return transferRepository.findAllByBankAccountOriginIdOrderByCreatAtDesc(bankAccountOriginId);
    }
}
