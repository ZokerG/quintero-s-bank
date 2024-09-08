package co.com.quinteropo.core.quinteropobank.core.service;

import ch.qos.logback.core.net.server.Client;
import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomDetailService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public CustomDetailService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientRecord client = clientRepository.findByDocument(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with document: " + username));
        return new User(String.valueOf(client.getId()), client.getDocument(), new ArrayList<>());
    }
}
