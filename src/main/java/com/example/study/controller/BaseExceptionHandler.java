package com.example.study.controller;

import com.example.study.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler  {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
         e.getStackTrace();
         return new Result(1,e.getMessage());
    }
}
