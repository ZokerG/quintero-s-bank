package co.com.quinteropo.core.quinteropobank.common.mapper;

import co.com.quinteropo.core.quinteropobank.domain.model.BankAccountRecord;

import java.time.LocalDate;

public class BankAccountMapper {

    private BankAccountMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static BankAccountRecord mapToCreateBankAccount(long clientId, String accountType, double totalBalance, String accountNumber){
        BankAccountRecord bankAccountRecord = new BankAccountRecord();
        bankAccountRecord.setClientId(clientId);
        bankAccountRecord.setAccountType(accountType);
        bankAccountRecord.setTotalBalance(totalBalance);
        bankAccountRecord.setAccountNumber(accountNumber);
        bankAccountRecord.setCreatAt(LocalDate.now());
        return bankAccountRecord;

    }
}
