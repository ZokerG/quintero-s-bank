package co.com.quinteropo.core.quinteropobank.core.service;


import co.com.quinteropo.core.quinteropobank.common.enums.MovementType;
import co.com.quinteropo.core.quinteropobank.common.mapper.MovementMapper;
import co.com.quinteropo.core.quinteropobank.common.response.DashResponse;
import co.com.quinteropo.core.quinteropobank.common.response.SeriesResponse;
import co.com.quinteropo.core.quinteropobank.domain.model.MovementRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.MovementRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Slf4j
@Service
public class MovementService {

    private final MovementRepository movementRepository;
    private final EntityManager entityManager;

    public MovementService(MovementRepository movementRepository, EntityManager entityManager) {
        this.movementRepository = movementRepository;
        this.entityManager = entityManager;
    }

    public void deleteMovement(long id) {
        log.info("Inside MovementService::deleteMovement id: {}", id);
        movementRepository.deleteById(id);
    }

    public MovementRecord findById(long id) {
        return movementRepository.findById(id).orElseThrow(() -> new RuntimeException("Movement not found"));
    }

    @Transactional
    public MovementRecord createMovement(long bankAccountId, String type, double amount, String description){
        log.info("Inside MovementService::createMovement bankAccountId: {} type: {} amount: {} description: {}", bankAccountId, type, amount, description);
        Long nextVal = 0L;
        try {
            nextVal = (Long) entityManager.createNativeQuery("SELECT nextval('movement_reference_seq')").getSingleResult();
        } catch (Exception e) {
            log.error("Error creating movement", e);
            throw new RuntimeException("Error creating movement");
        }
        String movementReference = String.format("%010d", nextVal);
        if (type.equals(MovementType.DEPOSIT.name())){
            return movementRepository.save(MovementMapper.mapToCreateMovement(bankAccountId, movementReference, MovementType.DEPOSIT.name(), amount, description));
        }
        return movementRepository.save(MovementMapper.mapToCreateMovement(bankAccountId, movementReference, MovementType.WITHDRAWAL.name(), amount, description));
    }

    public Page<MovementRecord> findAll(int page, int size) {
        log.info("Inside MovementService::findAll page: {} size: {}", page, size);
        return movementRepository.findAll(PageRequest.of(page, size));
    }

    public Page<MovementRecord> findByBankAccountId(long bankAccountId, int page, int size) {
        log.info("Inside MovementService::findByBankAccountId bankAccountId: {} page: {} size: {}", bankAccountId, page, size);
        return movementRepository.findAllByBankAccountId(bankAccountId, PageRequest.of(page, size));
    }

    public List<DashResponse> getDashInfo(long bankAccountId) {
        log.info("Inside MovementService::getDashInfo bankAccountId: {}", bankAccountId);
        List<MovementRecord> movements = movementRepository.findAllByBankAccountIdOrderByCreatAtDesc(bankAccountId);

        Map<String, Map<Integer, Double>> depositMap = new HashMap<>();
        Map<String, Map<Integer, Double>> withdrawalMap = new HashMap<>();

        for (MovementRecord movement : movements) {
            LocalDateTime date = movement.getCreatAt();
            int month = date.getMonthValue();
            String type = movement.getType();
            double amount = movement.getAmount();

            if (type.equals(MovementType.DEPOSIT.name())) {
                depositMap.computeIfAbsent(type, k -> new HashMap<>()).merge(month, amount, Double::sum);
            } else if (type.equals(MovementType.WITHDRAWAL.name())) {
                withdrawalMap.computeIfAbsent(type, k -> new HashMap<>()).merge(month, amount, Double::sum);
            }
        }

        List<SeriesResponse> depositSeries = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : depositMap.getOrDefault(MovementType.DEPOSIT.name(), new HashMap<>()).entrySet()) {
            depositSeries.add(SeriesResponse.builder().name(getMonthName(entry.getKey())).value(entry.getValue()).build());
        }

        List<SeriesResponse> withdrawalSeries = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : withdrawalMap.getOrDefault(MovementType.WITHDRAWAL.name(), new HashMap<>()).entrySet()) {
            withdrawalSeries.add(SeriesResponse.builder().name(getMonthName(entry.getKey())).value(entry.getValue()).build());
        }

        List<DashResponse> dashResponses = new ArrayList<>();
        dashResponses.add(DashResponse.builder().name("Deposits").series(depositSeries).build());
        dashResponses.add(DashResponse.builder().name("Withdrawals").series(withdrawalSeries).build());

        return dashResponses;
    }

    private String getMonthName(int month) {
        return Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }


}
