package com.example.project.advice;

import com.example.project.exception.NotFoundException;
import com.example.project.model.dto.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class APIControllerAdvice {
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,Object> forbidden(AccessDeniedException e){
        Map<String,Object> map = new HashMap<>();
        map.put("error",new ResponseError(403,"FOR_BIDDEN",e.getMessage()));
        return  map;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> handelDataNotFoundException(NotFoundException e){
        Map<String,Object> map = new HashMap<>();
        map.put("error", new ResponseError(400,"BAD_REQUEST",e.getMessage()));
        return map;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> handelDataNotValidException(MethodArgumentNotValidException e){
        Map<String,Object> map = new HashMap<>();
        Map<String,String> detailErr = new HashMap<>();
        e.getFieldErrors().forEach((err)->{
            detailErr.put(err.getField(),err.getDefaultMessage());
        });
        map.put("error", new ResponseError(400,"BAD_REQUEST",detailErr));
        return map;
    }
}


