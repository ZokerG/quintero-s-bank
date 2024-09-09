package co.com.quinteropo.core.quinteropobank.controller;


import co.com.quinteropo.core.quinteropobank.core.service.MovementService;
import co.com.quinteropo.core.quinteropobank.domain.model.MovementRecord;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movement")
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovement(@PathVariable long id) {
        movementService.deleteMovement(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MovementRecord createMovement(long bankAccountId, String type, double amount, String description) {
        return movementService.createMovement(bankAccountId, type, amount, description);
    }

    @GetMapping("/find-all")
    public Page<MovementRecord> findAll(int page, int size) {
        return movementService.findAll(page, size);
    }

    @GetMapping("/find-by-bank-account-id/{bankAccountId}")
    public Page<MovementRecord> findByBankAccountId(@PathVariable long bankAccountId, int page, int size) {
        return movementService.findByBankAccountId(bankAccountId, page, size);
    }
}
