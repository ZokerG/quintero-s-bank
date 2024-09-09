package co.com.quinteropo.core.quinteropobank.common.mapper;

import co.com.quinteropo.core.quinteropobank.domain.model.MovementRecord;

import java.time.LocalDateTime;

public class MovementMapper {

    private MovementMapper() {
    }

    public static MovementRecord mapToCreateMovement(long bankAccountId, String movementReference, String type, double amount, String description) {
        MovementRecord movementRecord = new MovementRecord();
        movementRecord.setBankAccountId(bankAccountId);
        movementRecord.setMovementReference(movementReference);
        movementRecord.setType(type);
        movementRecord.setAmount(amount);
        movementRecord.setDescription(description);
        movementRecord.setCreatAt(LocalDateTime.now());
        return movementRecord;
    }
}
