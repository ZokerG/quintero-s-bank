package co.com.quinteropo.core.quinteropobank.common.mapper;

import co.com.quinteropo.core.quinteropobank.domain.model.TransferRecord;

import java.time.LocalDate;

public class TransferMapper {

    private TransferMapper() {
    }

    public static TransferRecord toCreateTransferRecord(long categoryId, long bankAccountOriginId, long bankAccountDestinationId, double amount, String description, String type) {
        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setCategoryId(categoryId);
        transferRecord.setBankAccountOriginId(bankAccountOriginId);
        transferRecord.setBankAccountDestinationId(bankAccountDestinationId);
        transferRecord.setAmount(amount);
        transferRecord.setDescription(description);
        transferRecord.setType(type);
        transferRecord.setCreatAt(LocalDate.now());
        return transferRecord;
    }
}
