package co.com.quinteropo.core.quinteropobank.domain.repository;

import co.com.quinteropo.core.quinteropobank.domain.model.MovementRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<MovementRecord, Long> {

    Page<MovementRecord> findAllByBankAccountId(long bankAccountId, Pageable pageable);
}
