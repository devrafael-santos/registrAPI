package com.raffasdev.registrapi.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private final String secret = "123";
    private final long expirationHours = 1;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secretKey", secret);
        ReflectionTestUtils.setField(tokenService, "expirationHours", expirationHours);
    }

    @Test
    @DisplayName("generateToken creates a valid JWT when user is valid")
    void generateToken_returnsValidJwt_whenUserIsValid() {

        String userEmail = "teste@email.com";

        String token = tokenService.generateToken(userEmail);

        assertThat(token).isNotNull().isNotEmpty();

        String subject = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("registrApi")
                .build()
                .verify(token)
                .getSubject();

        assertThat(subject).isEqualTo(userEmail);
    }

    @Test
    @DisplayName("validateToken returns subject when token is valid and not expired")
    void validateToken_returnsSubject_whenTokenIsValid() {

        String userEmail = "teste@email.com";
        String validToken = tokenService.generateToken(userEmail);

        String subject = tokenService.validateToken(validToken);

        assertThat(subject).isEqualTo(userEmail);
    }

    @Test
    @DisplayName("validateToken returns null when token signature is invalid")
    void validateToken_returnsNull_whenTokenIsInvalid() {

        String otherSecret = "321";
        String invalidToken = JWT.create()
                .withIssuer("registrApi")
                .withSubject("teste@exemplo.com")
                .sign(Algorithm.HMAC256(otherSecret));

        String subject = tokenService.validateToken(invalidToken);

        assertThat(subject).isNull();
    }

    @Test
    @DisplayName("validateToken returns null when token is expired")
    void validateToken_returnsNull_whenTokenIsExpired() {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String expiredToken = JWT.create()
                .withIssuer("registrApi")
                .withSubject("teste@exemplo.com")
                .withExpiresAt(Instant.now().minusSeconds(1)) // <-- Expiração no passado
                .sign(algorithm);

        String subject = tokenService.validateToken(expiredToken);

        assertThat(subject).isNull();
    }

    @Test
    @DisplayName("generateToken throws RuntimeException for JWTCreationException")
    void generateToken_throwsRuntimeException_whenCreationFails() {

        String userEmail = "teste@email.com";

        ReflectionTestUtils.setField(tokenService, "secretKey", null);

        assertThatThrownBy(() -> tokenService.generateToken(userEmail))
                .isInstanceOf(RuntimeException.class);
    }
}