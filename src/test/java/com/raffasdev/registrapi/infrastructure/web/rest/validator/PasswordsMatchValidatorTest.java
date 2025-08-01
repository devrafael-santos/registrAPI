package com.raffasdev.registrapi.infrastructure.web.rest.validator;

import com.raffasdev.registrapi.infrastructure.web.rest.dto.request.RegisterUserRequest;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordsMatchValidatorTest {

    @Mock
    private ConstraintValidatorContext contextMock;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilderMock;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderMock;

    @InjectMocks
    private PasswordsMatchValidator validator;

    @Test
    @DisplayName("isValid returns true when passwords match")
    void isValid_ReturnsTrue_WhenPasswordsMatch() {

        var request = new RegisterUserRequest(
                "user",
                "user@test.com",
                "123",
                "123");

        boolean result = validator.isValid(request, contextMock);

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid returns false when passwords do not match")
    void isValid_ReturnsFalse_WhenPasswordsDoNotMatch() {

        when(contextMock.buildConstraintViolationWithTemplate(ArgumentMatchers.anyString())).thenReturn(violationBuilderMock);
        when(violationBuilderMock.addPropertyNode(ArgumentMatchers.anyString())).thenReturn(nodeBuilderMock);

        var request = new RegisterUserRequest(
                "user",
                "user@test.com",
                "123",
                "321");

        boolean result = validator.isValid(request, contextMock);

        assertFalse(result);

        verify(contextMock, times(1)).disableDefaultConstraintViolation();
        verify(contextMock, times(1)).buildConstraintViolationWithTemplate("Passwords do not match");
        verify(violationBuilderMock, times(1)).addPropertyNode("confirmPassword");
        verify(nodeBuilderMock, times(1)).addConstraintViolation();

    }

}