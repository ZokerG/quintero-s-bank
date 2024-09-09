package co.com.quinteropo.core.quinteropobank.core.service;


import co.com.quinteropo.core.quinteropobank.common.response.UserInfoResponse;
import co.com.quinteropo.core.quinteropobank.core.jwt.JwtUtil;
import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final ClientRepository clientRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(ClientRepository clientRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.clientRepository = clientRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<?> login(String email, String password) {
        log.info("Inside ClientService::login email: {}", email);
        ClientRecord clientRecord = clientRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Client not found"));
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(email);
        return ResponseEntity.ok(new UserInfoResponse(clientRecord.getName(), jwt));
    }

    public ResponseEntity<?> loginWithOAuth2AndOIDC() {
        log.info("Inside ClientService::loginWithOAuth2AndOIDC");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            email = oauthToken.getPrincipal().getAttribute("email");
        } else if (authentication.getPrincipal() instanceof OidcUser){
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            email = oidcUser.getEmail();
        }
        ClientRecord clientRecord = clientRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Client not found"));
        return ResponseEntity.ok(new UserInfoResponse(clientRecord.getName(), jwtUtil.generateToken(email)));
    }
}
