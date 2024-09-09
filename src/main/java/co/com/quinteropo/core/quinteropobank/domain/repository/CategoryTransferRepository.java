package co.com.quinteropo.core.quinteropobank.domain.repository;

import co.com.quinteropo.core.quinteropobank.domain.model.CategoryTransferRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryTransferRepository extends JpaRepository<CategoryTransferRecord, Long> {
    Optional<CategoryTransferRecord> findByName(String name);
    boolean existsByName(String name);
}
