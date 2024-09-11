package co.com.quinteropo.core.quinteropobank.common.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashCardResponse {
    private double totalBalance;
    private String accountNumber;
    private String accountType;
    private String clientFullName;
    private long numberOfTransactions;
    private long withdrawal;
    private long deposit;
}
