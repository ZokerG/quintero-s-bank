package co.com.quinteropo.core.quinteropobank.core.service;

import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CustomOidcUserService extends OidcUserService {

    private final ClientRepository clientRepository;

    public CustomOidcUserService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> claims = oidcUser.getClaims();
        log.info("Claims del usuario OIDC (Google): {}", claims);
        String email = (String) claims.get("email");
        String name = (String) claims.get("name");
        Optional<ClientRecord> clientRecord = clientRepository.findByEmail(email);
        if(clientRecord.isPresent()){
            log.info("Usuario encontrado en la base de datos: {}", clientRecord.get());
        } else {
            ClientRecord newClient = new ClientRecord();
            newClient.setEmail(email);
            newClient.setName(name);
            clientRepository.save(newClient);
        }
        return oidcUser;
    }
}