package co.com.quinteropo.core.quinteropobank.controller;

import co.com.quinteropo.core.quinteropobank.common.request.CreateTransferRequest;
import co.com.quinteropo.core.quinteropobank.core.service.TransferService;
import co.com.quinteropo.core.quinteropobank.domain.model.TransferRecord;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTransfer(@PathVariable long id) {
        transferService.deleteTransfer(id);
    }

    @GetMapping("/find-all")
    public Page<TransferRecord> findAll(int page, int size) {
        return transferService.findAll(page, size);
    }

    @GetMapping("/find-by-id/{id}")
    public TransferRecord findById(@PathVariable long id) {
        return transferService.findById(id);
    }

    @PostMapping("/create")
    public TransferRecord createTransfer(@RequestBody CreateTransferRequest createTransferRequest) {
        return transferService.createTransfer(createTransferRequest);
    }

    @GetMapping("/find-all-by-bank-account/{bankAccountOriginId}")
    public List<TransferRecord> findAllByBankAccountOriginId(@PathVariable long bankAccountOriginId) {
        return transferService.findAllByBankAccountOriginId(bankAccountOriginId);
    }
}
