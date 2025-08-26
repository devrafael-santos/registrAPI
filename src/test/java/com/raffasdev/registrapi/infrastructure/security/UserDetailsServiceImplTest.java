package com.raffasdev.registrapi.infrastructure.security;

import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.repository.UserJpaRepository;
import com.raffasdev.registrapi.util.UserEntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserJpaRepository userJpaRepositoryMock;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    @DisplayName("loadUserByUsername returns UserDetails when user is found")
    void loadUserByUsername_returnsUserDetails_whenUserIsFound() {

        String userEmail = "teste@email.com";
        UserEntity userEntity = UserEntityCreator.createEntity();

        given(userJpaRepositoryMock.findByEmail(userEmail)).willReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userEmail);

        verify(userJpaRepositoryMock).findByEmail(userEmail);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(userEmail);
        assertThat(userDetails.getPassword()).isEqualTo("encodedPassword");
        assertEquals(0, userDetails.getAuthorities().toArray().length);
    }

    @Test
    @DisplayName("loadUserByUsername throws UsernameNotFoundException when user is not found")
    void loadUserByUsername_throwsUsernameNotFoundException_whenUserIsNotFound() {

        String userEmail = "notfound@user.com";
        given(userJpaRepositoryMock.findByEmail(userEmail)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userDetailsServiceImpl.loadUserByUsername(userEmail))
                .isInstanceOf(UsernameNotFoundException.class);

        verify(userJpaRepositoryMock).findByEmail(userEmail);
    }
}