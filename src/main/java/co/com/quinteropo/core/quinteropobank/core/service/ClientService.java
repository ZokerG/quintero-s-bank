package co.com.quinteropo.core.quinteropobank.core.service;

import ch.qos.logback.core.net.server.Client;
import co.com.quinteropo.core.quinteropobank.common.mapper.ClientMapper;
import co.com.quinteropo.core.quinteropobank.common.request.CreateClientRequest;
import co.com.quinteropo.core.quinteropobank.common.response.UserInfoResponse;
import co.com.quinteropo.core.quinteropobank.core.jwt.JwtUtil;
import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public ClientService(ClientRepository clientRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.clientRepository = clientRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ClientRecord findById(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public List<ClientRecord> findAll(int page, int size) {
        log.info("Inside ClientService::findAll page: {} size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<ClientRecord> resultPage = clientRepository.findAll(pageable);

        log.info("Inside ClientService::findAll resultPage: {}", resultPage.getContent());
        return resultPage.getContent();
    }

    public void deleteClient(long id) {
        log.info("Inside ClientService::deleteClient id: {}", id);
        clientRepository.deleteById(id);
    }

    public void createClient(CreateClientRequest createClientRequest) {
        log.info("Inside ClientService::createClient createClientRequest: {}", createClientRequest);

        if (clientRepository.findByEmail(createClientRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Client already exists");
        }

        if (!createClientRequest.getPassword().equals(createClientRequest.getConfirmPassword())){
            throw new RuntimeException("Passwords do not match");
        }

        clientRepository.save(ClientMapper.mapToCreateClientRecord(createClientRequest.getName(), createClientRequest.getLastName(), createClientRequest.getPhone(), createClientRequest.getEmail(), createClientRequest.getDocumentType(), createClientRequest.getDocument(), createClientRequest.getProvince(), createClientRequest.getCity(), bCryptPasswordEncoder(createClientRequest.getPassword())));
    }

    private String bCryptPasswordEncoder(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

}
