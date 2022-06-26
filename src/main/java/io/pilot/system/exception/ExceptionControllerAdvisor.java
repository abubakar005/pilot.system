package io.pilot.system.exception;

import io.pilot.system.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto> handleValidationException(BadRequestException exception) {
        log.error("BadRequest exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ResponseDto(exception.getCode(), exception.getMsg()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ElementWithSameIDAlreadyExistsException.class)
    public ResponseEntity<ResponseDto> handleElementAlreadyExistException(ElementWithSameIDAlreadyExistsException exception) {
        log.error("ElementWithSameIDAlreadyExists exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ResponseDto(exception.getCode(), exception.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFoundException(ElementNotFoundException exception) {
        log.error("NotFound exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ResponseDto(exception.getCode(), exception.getMsg()), HttpStatus.NOT_FOUND);
    }
}
