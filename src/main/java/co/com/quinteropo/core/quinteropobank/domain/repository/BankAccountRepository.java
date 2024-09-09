package co.com.quinteropo.core.quinteropobank.domain.repository;

import co.com.quinteropo.core.quinteropobank.domain.model.BankAccountRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccountRecord, Long> {
    boolean existsByAccountNumber(String accountNumber);
    Optional<BankAccountRecord> findByAccountNumber(String accountNumber);

    boolean existsByClientId(long clientId);
}
