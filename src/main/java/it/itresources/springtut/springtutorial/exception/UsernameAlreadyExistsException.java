package it.itresources.springtut.springtutorial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(){
        super();
    }

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
