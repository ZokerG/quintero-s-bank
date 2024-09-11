package co.com.quinteropo.core.quinteropobank.domain.repository;

import co.com.quinteropo.core.quinteropobank.domain.model.TransferRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<TransferRecord, Long> {

    List<TransferRecord> findAllByBankAccountOriginIdOrderByCreatAtDesc(long bankAccountOriginId);
}
