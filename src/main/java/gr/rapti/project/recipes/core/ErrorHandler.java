package gr.rapti.project.recipes.core;

import gr.rapti.project.recipes.core.exceptions.EntityAlreadyExistsException;
import gr.rapti.project.recipes.dto.ResponseMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(EntityAlreadyExistsException e){
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.CONFLICT);
    }

}
