package pl.gatomek.booklibrary.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.gatomek.booklibrary.dto.ErrorMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidObjectException;

@RestControllerAdvice(basePackages = "pl.gatomek.booklibrary.rest")
public class BookLibRestControllerAdvice {

    @ExceptionHandler(value = FileNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage bookNotFoundException(FileNotFoundException ex) {
        return new ErrorMessage("Book not found: " + ex.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage internalServerErrorException() {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler(value = InvalidObjectException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage invalidObjectException( InvalidObjectException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
