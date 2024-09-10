package co.com.quinteropo.core.quinteropobank.core.service;


import co.com.quinteropo.core.quinteropobank.common.request.AuthRequest;
import co.com.quinteropo.core.quinteropobank.common.response.AuthUserResponse;
import co.com.quinteropo.core.quinteropobank.common.response.UserInfoResponse;
import co.com.quinteropo.core.quinteropobank.common.response.UserResponse;
import co.com.quinteropo.core.quinteropobank.core.jwt.JwtUtil;
import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class AuthService {

    private final ClientRepository clientRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomDetailService customDetailService;

    public AuthService(ClientRepository clientRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, CustomDetailService customDetailService) {
        this.clientRepository = clientRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customDetailService = customDetailService;
    }

    public ResponseEntity<?> login(AuthRequest authRequest) {
        log.info("Inside ClientService::login email: {}", authRequest.getUsername());

        ClientRecord clientRecord = clientRepository.findByEmail(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        UserDetails userDetails =  customDetailService.loadUserByUsername(authRequest.getUsername());
        UserResponse user = new UserResponse(clientRecord.getId(), clientRecord.getEmail(), clientRecord.getPassword(), userDetails.isEnabled(), userDetails.isAccountNonExpired(), userDetails.isAccountNonExpired(), userDetails.isAccountNonLocked(), userDetails.isEnabled());
        validateUserPassword.accept(authRequest, user, "Invalid username or password");
        AuthUserResponse userResponse = getAuthResponse(user, authRequest.getUsername(), authRequest.getPassword(), user.getAuthorities());
        log.info("Inside ClientService::login userResponse: {}", userResponse);

        String jwt = jwtUtil.generateToken(authRequest.getUsername());

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

    private boolean bCryptPasswordEncoderMatch(String rawPassword, String encodePassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodePassword);
    }

    private final TriConsumer<AuthRequest, UserDetails, String> validateUserPassword = (request, user, message) -> {
        if (!bCryptPasswordEncoderMatch(request.getPassword(), user.getPassword())) {
            throw new RuntimeException(message);
        }
    };

    private AuthUserResponse getAuthResponse(UserResponse user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        if (!user.isEnabled() || !user.isAccountNonLocked() || !user.isEnable()) {
            throw new RuntimeException("User is not enabled");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password,
                        authorities
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUserResponse.AuthUserResponseBuilder authResponse = AuthUserResponse.builder()
                .accessToken(jwtUtil.generateToken(username));

        return authResponse.build();
    }
}
