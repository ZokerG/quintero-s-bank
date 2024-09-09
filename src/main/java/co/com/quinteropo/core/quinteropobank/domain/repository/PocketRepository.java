package co.com.quinteropo.core.quinteropobank.domain.repository;

import co.com.quinteropo.core.quinteropobank.domain.model.PocketRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PocketRepository extends JpaRepository<PocketRecord, Long> {

    Page<PocketRecord> findAllByBankAccountId(long bankAccountId, Pageable pageable);
}
