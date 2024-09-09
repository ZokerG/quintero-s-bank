package co.com.quinteropo.core.quinteropobank.common.mapper;

import co.com.quinteropo.core.quinteropobank.domain.model.PocketRecord;

import java.time.LocalDate;

public class PocketMapper {

    private PocketMapper() {
    }

    public static PocketRecord toCreatePocketRecord(long bankAccountId, String name, String pocketNumber, double balance) {
        PocketRecord pocketRecord = new PocketRecord();
        pocketRecord.setBankAccountId(bankAccountId);
        pocketRecord.setName(name);
        pocketRecord.setPocketNumber(pocketNumber);
        pocketRecord.setTotalBalance(balance);
        pocketRecord.setCreatAt(LocalDate.now());
        return pocketRecord;
    }
}
