package co.com.quinteropo.core.quinteropobank.core.service;

import co.com.quinteropo.core.quinteropobank.domain.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final ClientRepository clientRepository;

    public CustomOAuth2Service(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("OAuth2UserRequest: {}", userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User: {}", oAuth2User);
        return oAuth2User;
    }
}
