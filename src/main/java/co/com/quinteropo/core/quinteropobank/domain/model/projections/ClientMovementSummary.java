package co.com.quinteropo.core.quinteropobank.domain.model.projections;

public interface ClientMovementSummary {
    String getFullName();
    Long getTotalMovements();
    Long getTotalWithdrawals();
    Long getTotalDeposits();
}
