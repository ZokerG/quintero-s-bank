package co.com.quinteropo.core.quinteropobank.core.service;

import co.com.quinteropo.core.quinteropobank.common.enums.AccountType;
import co.com.quinteropo.core.quinteropobank.common.mapper.BankAccountMapper;
import co.com.quinteropo.core.quinteropobank.common.response.DashCardResponse;
import co.com.quinteropo.core.quinteropobank.domain.model.BankAccountRecord;
import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import co.com.quinteropo.core.quinteropobank.domain.model.projections.ClientMovementSummary;
import co.com.quinteropo.core.quinteropobank.domain.repository.BankAccountRepository;
import co.com.quinteropo.core.quinteropobank.domain.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final ClientService clientService;
    private final MovementRepository movementRepository;


    private static final String PREFIX = "411001";

    private static final double INITIAL_BALANCE = 1000;

    public BankAccountService(BankAccountRepository bankAccountRepository, ClientService clientService, MovementRepository movementRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientService = clientService;
        this.movementRepository = movementRepository;
    }

    public void deleteBankAccount(long id) {
        bankAccountRepository.deleteById(id);
    }

    public void createBankAccount(long clientId, int accountType){

        if (bankAccountRepository.existsByClientId(clientId)){
            throw new RuntimeException("Client already has a bank account");
        }

        ClientRecord client = clientService.findById(clientId);
        if (accountType == 0){
            String accountNumber = generateAccountNumber();
            String accountTypeString = AccountType.SAVINGS.name();
            bankAccountRepository.save(BankAccountMapper.mapToCreateBankAccount(client.getId(), accountTypeString, INITIAL_BALANCE, accountNumber));
        } else if (accountType == 1){
            String accountNumber = generateAccountNumber();
            String accountTypeString = AccountType.CURRENT.name();
            bankAccountRepository.save(BankAccountMapper.mapToCreateBankAccount(client.getId(), accountTypeString, INITIAL_BALANCE, accountNumber));
        } else {
            throw new RuntimeException("Invalid account type");
        }
    }

    public Page<BankAccountRecord> findAll(int page, int size) {
        return bankAccountRepository.findAll(PageRequest.of(page, size));
    }

    public BankAccountRecord findById(long id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank account not found"));
    }

    public BankAccountRecord findByAccountNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Bank account not found"));
    }

    private String generateAccountNumber(){
        Random random = new Random();
        String accountNumber;
        boolean exists;

        do {
            StringBuilder sb = new StringBuilder(PREFIX);
            for (int i = 0; i < 5; i++) {
                sb.append(random.nextInt(10));
            }
            accountNumber = sb.toString();
            exists = bankAccountRepository.existsByAccountNumber(accountNumber);
        } while (exists);

        return accountNumber;
    }

    public double getBalanceByBankAccount(long bankAccountId){
        BankAccountRecord bankAccount = findById(bankAccountId);
        return bankAccount.getTotalBalance();
    }

    public void updateBalance(long bankAccountId, double amount){
        BankAccountRecord bankAccount = findById(bankAccountId);
        bankAccount.setTotalBalance(amount);
        bankAccountRepository.save(bankAccount);
    }

    public DashCardResponse findDashCardByClientId(long clientId){
        BankAccountRecord bankAccount = bankAccountRepository.findByClientId(clientId).orElseThrow(() ->
                new RuntimeException("Bank account not found"));
        ClientMovementSummary clientMovementSummary = movementRepository.findClientMovementSummaryByClientId(clientId);
        return BankAccountMapper.mapToDashCardResponse(bankAccount, clientMovementSummary.getFullName(), clientMovementSummary.getTotalMovements(), clientMovementSummary.getTotalWithdrawals(), clientMovementSummary.getTotalDeposits());
    }
}
