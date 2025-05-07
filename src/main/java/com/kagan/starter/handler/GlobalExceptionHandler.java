package com.kagan.starter.handler;

import com.kagan.starter.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request)
    {
       return ResponseEntity.badRequest().body(createApiError(ex.getMessage(),request));
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request)
    {
        Map<String, List<String>> map = new HashMap<>();
        for (ObjectError objectError: ex.getBindingResult().getAllErrors())
        {
            String fieldName = ((FieldError)objectError).getField();

            if(map.containsKey(fieldName))
            {
                map.put(fieldName,addValue(map.get(fieldName),objectError.getDefaultMessage()));
            }
            else
            {
                map.put(fieldName,addValue(new ArrayList<>(),objectError.getDefaultMessage()));
            }
        }


       return ResponseEntity.badRequest().body(createApiError(map,request));
    }



    private List<String> addValue(List<String> list,String newValue)
    {
        list.add(newValue);
        return list;
    }


    public <E> ApiError<E> createApiError(E message, WebRequest request)
    {
        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());

        ExceptionDetails<E> exceptionDetails = new ExceptionDetails<>();
        exceptionDetails.setPath(request.getDescription(false).substring(4));
        exceptionDetails.setCreateTime(new Date());
        exceptionDetails.setMessage(message);

        apiError.setExceptionDetails(exceptionDetails);

        return apiError;
    }





}
