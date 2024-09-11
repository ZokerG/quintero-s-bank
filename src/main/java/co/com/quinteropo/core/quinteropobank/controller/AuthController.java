package co.com.quinteropo.core.quinteropobank.controller;

import co.com.quinteropo.core.quinteropobank.common.request.AuthRequest;
import co.com.quinteropo.core.quinteropobank.core.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        log.info("Inside AuthController::login email: {}", authRequest.getUsername());
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<?> loginWithOAuth2AndOIDC() {
        log.info("Inside AuthController::loginWithOAuth2AndOIDC");
        return ResponseEntity.ok(authService.loginWithOAuth2AndOIDC());
    }
}
