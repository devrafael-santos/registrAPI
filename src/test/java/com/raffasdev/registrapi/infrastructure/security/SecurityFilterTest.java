package com.raffasdev.registrapi.infrastructure.security;

import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.repository.UserJpaRepository;
import com.raffasdev.registrapi.util.UserEntityCreator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @Mock
    private TokenService tokenServiceMock;

    @Mock
    private UserJpaRepository userRepositoryMock;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private FilterChain filterChainMock;

    @InjectMocks
    private SecurityFilter securityFilter;

    @Test
    @DisplayName("doFilterInternal sets Authentication in SecurityContext when token is valid")
    void doFilterInternal_SetsAuthentication_whenTokenIsValid() throws ServletException, IOException {

        String token = "valid.jwt.token";
        String userEmail = "teste@email.com";
        UserEntity userEntity = UserEntityCreator.createEntity();

        when(requestMock.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenServiceMock.validateToken(token)).thenReturn(userEmail);
        when(userRepositoryMock.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));

        securityFilter.doFilterInternal(requestMock, responseMock, filterChainMock);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo(userEntity);

        verify(filterChainMock).doFilter(requestMock, responseMock);
    }

    @Test
    @DisplayName("doFilterInternal does not set Authentication when token is invalid")
    void doFilterInternal_DoesNotSetAuthentication_whenTokenIsInvalid() throws ServletException, IOException {

        String token = "invalid.jwt.token";
        when(requestMock.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenServiceMock.validateToken(token)).thenReturn(null);

        securityFilter.doFilterInternal(requestMock, responseMock, filterChainMock);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();

        verify(filterChainMock).doFilter(requestMock, responseMock);
    }
}