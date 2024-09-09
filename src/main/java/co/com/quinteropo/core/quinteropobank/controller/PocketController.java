package co.com.quinteropo.core.quinteropobank.controller;

import co.com.quinteropo.core.quinteropobank.core.service.PocketService;
import co.com.quinteropo.core.quinteropobank.domain.model.PocketRecord;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pocket")
public class PocketController {

    private final PocketService pocketService;

    public PocketController(PocketService pocketService) {
        this.pocketService = pocketService;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePocket(@PathVariable long id) {
        pocketService.deletePocket(id);
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PocketRecord findById(@PathVariable long id) {
        return pocketService.findById(id);
    }

    @GetMapping("/find-all-bank-account-id/{bankAccountId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PocketRecord> findAllByBankAccountId(@PathVariable long bankAccountId, int page, int size) {
        return pocketService.findAllByBankAccountId(bankAccountId, page, size);
    }

    @PostMapping("/create/{bankAccountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPocket(@PathVariable long bankAccountId, String name, double balance) {
        pocketService.createPocket(bankAccountId, name, balance);
    }
}
