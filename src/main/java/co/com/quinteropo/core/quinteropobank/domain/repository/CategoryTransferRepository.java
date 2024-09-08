package co.com.quinteropo.core.quinteropobank.domain.repository;

import co.com.quinteropo.core.quinteropobank.domain.model.CategoryTransferRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTransferRepository extends JpaRepository<CategoryTransferRecord, Long> {
}
