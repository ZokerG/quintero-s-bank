package co.com.quinteropo.core.quinteropobank.controller;


import co.com.quinteropo.core.quinteropobank.core.service.BankAccountService;
import co.com.quinteropo.core.quinteropobank.domain.model.BankAccountRecord;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBankAccount(@PathVariable long id) {
        bankAccountService.deleteBankAccount(id);
    }

    @PostMapping("/create/{clientId}")
    public void createBankAccount(@PathVariable long clientId, @RequestParam int accountType) {
        bankAccountService.createBankAccount(clientId, accountType);
    }

    @GetMapping("/find-all")
    public Page<BankAccountRecord> findAll(int page, int size) {
        return bankAccountService.findAll(page, size);
    }

    @GetMapping("/find-by-id/{id}")
    public BankAccountRecord findById(@PathVariable long id) {
        return bankAccountService.findById(id);
    }

    @GetMapping("/find-by-account-number")
    public BankAccountRecord findByAccountNumber(@RequestParam String accountNumber) {
        return bankAccountService.findByAccountNumber(accountNumber);
    }
}
