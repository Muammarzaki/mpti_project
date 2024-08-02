package uin.helpers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uin.dto.ErrorsDTO;

@RestControllerAdvice
public class ErrorHandlers {
    @ExceptionHandler(AccountExitsException.class)
    public ErrorsDTO userRegisterError(AccountExitsException exception) {
        return new ErrorsDTO(exception.getMessage());
    }

}
