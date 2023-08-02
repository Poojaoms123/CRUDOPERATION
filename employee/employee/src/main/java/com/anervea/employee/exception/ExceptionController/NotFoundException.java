package com.anervea.employee.exception.ExceptionController;

import com.anervea.employee.model.response.CustomResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotFoundException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> NotFoundExceptionMainMethod(UserException notFoundExceptionMain){
        HandleNotFoundException exception=new HandleNotFoundException();
        exception.setResponse(" Id Not Found In Database");
        exception.setStatus(-1);
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.SaveException.class)
    public ResponseEntity<Object> SaveException(UserException.SaveException saveOrUpdateException){
        return new ResponseEntity<>(new CustomResponseMessage("Saved",0), HttpStatus.OK);
    }

    @ExceptionHandler(UserException.UpdateException.class)
    public ResponseEntity<Object> UpdateException(UserException.UpdateException updateException){
        return new ResponseEntity<>(new CustomResponseMessage("Update",0), HttpStatus.OK);
    }



}
