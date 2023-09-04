package com.bank.transfer.handler;

import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.validator.ValidationErrorResponse;
import com.bank.transfer.validator.Violation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferExceptionHandlerTest {
    @InjectMocks
    private TransferExceptionHandler transferExceptionHandler;

    @BeforeEach
    public void setUp() {
        transferExceptionHandler = new TransferExceptionHandler();
    }

    @Test
    public void testHandlerException_WhenGenericException_ThenBadRequest() {
        String exceptionMessage = "This is a generic exception";
        Exception exception = new Exception(exceptionMessage);
        ResponseEntity<TransferIncorrectData> responseEntity = transferExceptionHandler.handlerException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody().getInfo());
    }

    @Test
    public void testHandlerException_WhenNoSuchTransferException_ThenNotFound() {
        String exceptionMessage = "No such transfer exception";
        NoSuchTransferException noSuchTransferException = new NoSuchTransferException(exceptionMessage);
        ResponseEntity<TransferIncorrectData> responseEntity = transferExceptionHandler.handlerException(noSuchTransferException);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody().getInfo());
    }

    @Test
    public void testOnConstraintValidation_ExceptionWhenConstraintViolationException_ThenCorrectViolationMapping() {
        Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        Path path1 = mock(Path.class);
        Mockito.when(violation1.getPropertyPath()).thenReturn(path1);
        Mockito.when(path1.toString()).thenReturn("field1");
        Mockito.when(violation1.getMessage()).thenReturn("message1");
        constraintViolations.add(violation1);
        ConstraintViolationException exception = new ConstraintViolationException(constraintViolations);
        ValidationErrorResponse response = transferExceptionHandler.onConstraintValidationException(exception);
        Violation responseViolation1 = response.getViolations().get(0);
        assertEquals("field1", responseViolation1.getFieldName());
        assertEquals("message1", responseViolation1.getMessage());
        assertEquals(1, response.getViolations().size());
    }

    @Test
    public void testOnMethodArgumentNotValidException_WhenCalledWithMethodArgumentNotValidException_ThenReturnsCorrectValidationErrorResponse() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        ValidationErrorResponse response = transferExceptionHandler.onMethodArgumentNotValidException(exception);
        Violation responseViolation = response.getViolations().get(0);
        assertEquals("field", responseViolation.getFieldName());
        assertEquals("defaultMessage", responseViolation.getMessage());
        assertEquals(1, response.getViolations().size());
    }
}