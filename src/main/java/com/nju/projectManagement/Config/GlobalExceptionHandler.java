package com.nju.projectManagement.Config;

import com.nju.projectManagement.Exception.ScheduleException;
import com.nju.projectManagement.Exception.StatisticException;
import com.nju.projectManagement.Exception.UserException;
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
 * @author WangYuxiao
 * @date 2022/2/7 16:56
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理使用@Valid注解校验参数所产生的异常
     * @param e 异常
     * @return 包装结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        return new ResponseVO<>(false, message);
    }

    @ExceptionHandler(UserException.class)
    public ResponseVO<Object> handleUserException(UserException e) {
        return new ResponseVO<>(false, e.getMessage());
    }

    @ExceptionHandler(StatisticException.class)
    public ResponseVO<Object> handleStatisticException(StatisticException e) {
        return new ResponseVO<>(false, e.getMessage());
    }

    public ResponseVO<Object> handleScheduleException(ScheduleException e) {
        return new ResponseVO<>(false, e.getMessage());
    }
}
