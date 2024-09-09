package co.com.quinteropo.core.quinteropobank.domain.repository;

import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientRecord, Long> {
    Optional<ClientRecord> findByDocument(String username);
    Optional<ClientRecord> findByEmail(String email);
}
