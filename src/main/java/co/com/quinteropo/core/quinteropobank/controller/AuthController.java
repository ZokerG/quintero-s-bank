package co.com.quinteropo.core.quinteropobank.controller;

import co.com.quinteropo.core.quinteropobank.core.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(String email, String password) {
        log.info("Inside AuthController::login email: {}", email);
        return ResponseEntity.ok(authService.login(email, password));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<?> loginWithOAuth2AndOIDC() {
        log.info("Inside AuthController::loginWithOAuth2AndOIDC");
        return ResponseEntity.ok(authService.loginWithOAuth2AndOIDC());
    }
}
