package co.com.quinteropo.core.quinteropobank.core.service;

import co.com.quinteropo.core.quinteropobank.common.mapper.PocketMapper;
import co.com.quinteropo.core.quinteropobank.domain.model.PocketRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.PocketRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PocketService {

    private final PocketRepository pocketRepository;
    private final EntityManager entityManager;

    public PocketService(PocketRepository pocketRepository, EntityManager entityManager) {
        this.pocketRepository = pocketRepository;
        this.entityManager = entityManager;
    }

    public void deletePocket(long id) {
        log.info("Inside PocketService::deletePocket id: {}", id);
        pocketRepository.deleteById(id);
    }

    public PocketRecord findById(long id) {
        return pocketRepository.findById(id).orElseThrow(() -> new RuntimeException("Pocket not found"));
    }

    public Page<PocketRecord> findAllByBankAccountId(long bankAccountId, int page, int size) {
        log.info("Inside PocketService::findAllByBankAccountId bankAccountId: {} page: {} size: {}", bankAccountId, page, size);
        return pocketRepository.findAllByBankAccountId(bankAccountId, PageRequest.of(page, size));
    }

    public void createPocket(long bankAccountId, String name, double balance){
        log.info("Inside PocketService::createPocket bankAccountId: {} name: {} balance: {}", bankAccountId, name, balance);
        Long sequence = 0L;
        try {
            sequence = (Long) entityManager.createNativeQuery("SELECT nextval('pocket_number_seq')").getSingleResult();
        } catch (Exception e) {
            log.error("Error creating pocket", e);
            throw new RuntimeException("Error creating pocket");
        }
        pocketRepository.save(PocketMapper.toCreatePocketRecord(bankAccountId, name, String.format("%06d" ,sequence),balance));
    }
}
