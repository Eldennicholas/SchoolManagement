package com.example.student_service.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestfullExceptionHandler {

    @ExceptionHandler(value = StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    //if there is no particular department
    @ExceptionHandler(value = FeignException.NotFound.class)
    public ResponseEntity<String> handleFeignNotFoundException(FeignException.NotFound e){
        return new ResponseEntity<>(e.contentUTF8(), HttpStatus.valueOf(e.status()));
    }

    // if the department service (server went down)
    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException e){
        return new ResponseEntity<>("Something went wrong", HttpStatus.valueOf(e.status()));
    }
}
