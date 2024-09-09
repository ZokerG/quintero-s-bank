package co.com.quinteropo.core.quinteropobank.common.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferRequest {
        private long categoryId;
        private long bankAccountOriginId;
        private long bankAccountDestinationId;
        private double amount;
        private String description;
}
