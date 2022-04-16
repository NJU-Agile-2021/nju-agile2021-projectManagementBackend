package com.nju.projectManagement.Config.Handler;

import com.nju.projectManagement.VO.ResponseVO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Toby Fu
 * @date 2022/2/8
 **/

@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseVO exceptionHandler(Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
        return ResponseVO.buildFailure(e.getMessage());
    }
}