package co.com.quinteropo.core.quinteropobank.controller;

import co.com.quinteropo.core.quinteropobank.common.request.CreateClientRequest;
import co.com.quinteropo.core.quinteropobank.core.service.ClientService;
import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client", description = "REST API for Client")
@Slf4j
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @Operation(summary = "Find client by id", description = "Find client by id")
    @GetMapping("/find-by-id/{id}")
    public ClientRecord findById(@PathVariable long id) {
        return clientService.findById(id);
    }

    @GetMapping("/find-all")
    public List<ClientRecord> findAll(int page, int size) {
        log.info("Inside ClientController::findAll page: {} size: {}", page, size);
        return clientService.findAll(page, size);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable long id) {
        log.info("Inside ClientController::deleteClient id: {}", id);
        clientService.deleteClient(id);
    }

    @PostMapping("/create")
    public void createClient(@RequestBody CreateClientRequest createClientRequest) {
        log.info("Inside ClientController::createClient createClientRequest: {}", createClientRequest);
        clientService.createClient(createClientRequest);
    }
}
