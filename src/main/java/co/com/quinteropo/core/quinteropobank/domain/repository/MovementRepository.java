package co.com.quinteropo.core.quinteropobank.domain.repository;

import co.com.quinteropo.core.quinteropobank.domain.model.MovementRecord;
import co.com.quinteropo.core.quinteropobank.domain.model.projections.ClientMovementSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovementRepository extends JpaRepository<MovementRecord, Long> {

    Page<MovementRecord> findAllByBankAccountId(long bankAccountId, Pageable pageable);

    List<MovementRecord> findAllByBankAccountIdOrderByCreatAtDesc(long bankAccountId);

    @Query(value = "SELECT " +
            "CONCAT(c.name, ' ', c.last_name) AS fullName, " +
            "COUNT(m.id) AS totalMovements, " +
            "SUM(CASE WHEN m.type = 'WITHDRAWAL' THEN 1 ELSE 0 END) AS totalWithdrawals, " +
            "SUM(CASE WHEN m.type = 'DEPOSIT' THEN 1 ELSE 0 END) AS totalDeposits " +
            "FROM bank_account b " +
            "JOIN movement m ON b.id = m.bank_account_id " +
            "JOIN client c ON c.id = b.client_id " +
            "WHERE b.client_id = :clientId " +
            "GROUP BY fullName",
            nativeQuery = true)
    ClientMovementSummary findClientMovementSummaryByClientId(@Param("clientId") Long clientId);

}
