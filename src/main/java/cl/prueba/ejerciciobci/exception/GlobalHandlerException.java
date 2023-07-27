package cl.prueba.ejerciciobci.exception;

import cl.prueba.ejerciciobci.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException  extends ResponseEntityExceptionHandler {

    @ExceptionHandler( value = {Exception.class})
    public ResponseEntity<ErrorDTO> handleException(Exception ex){
        return new ResponseEntity<>(
                new ErrorDTO(Constants.GENERIC_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler( value = {NullPointerException.class})
    public ResponseEntity<ErrorDTO> handleException(NullPointerException ex){
        return new ResponseEntity<>(
                new ErrorDTO(Constants.NULL_POINTER_EX),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UserException.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(UserException e){
        HttpStatus status=null;
        if(e.getCode()==400){
            status = HttpStatus.BAD_REQUEST;
        } else if (e.getCode()==401) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (e.getCode()==404) {
            status = HttpStatus.NOT_FOUND;
        } else if(e.getCode()==409){
            status = HttpStatus.CONFLICT;
        } else if (e.getCode()==500) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new ErrorDTO(e.getMessage()),null,status);
    }
}
