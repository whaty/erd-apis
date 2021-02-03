package com.java2e.martin.erd.config;

import cn.fisok.pdman.command.ExecResult;
import com.java2e.martin.erd.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/10/29
 * @describtion ControllerExceptionHandleAdvice
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandleAdvice {
    @ExceptionHandler
    public ExecResult handler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        log.info("Restful Http请求发生异常...");

        if (res.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            log.info("修改返回状态值为200");
            res.setStatus(HttpStatus.OK.value());
        }

        if (e instanceof NullPointerException) {
            log.error("代码00：" + e.getMessage(), e);
            return new ExecResult("FAILED", "发生空指针异常");
        } else if (e instanceof IllegalArgumentException) {
            log.error("代码01：" + e.getMessage(), e);
            return new ExecResult("FAILED", "请求参数类型不匹配");
        } else if (e instanceof SQLException) {
            log.error("代码02：" + e.getMessage(), e);
            return new ExecResult("FAILED", "数据库访问异常");
        } else if (e instanceof HttpMessageNotReadableException) {
            log.error("代码02：" + e.getMessage(), e);
            return new ExecResult("FAILED", "请求body体不存在或不为json");
        } else {
            log.error("代码99：" + e.getMessage(), e);
            return new ExecResult("FAILED", "服务器代码发生异常,请联系管理员");
        }
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result defaultErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) throws Exception {
        log.error("", e);
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<Map> errorMsgs = new ArrayList<>();

        allErrors.forEach(objectError -> {
            HashMap<Object, Object> errorMsg = new HashMap<>(3);
            FieldError fieldError = (FieldError) objectError;
            errorMsg.put("field", fieldError.getField());
            errorMsg.put("objectName", fieldError.getObjectName());
            errorMsg.put("message", fieldError.getDefaultMessage());
            errorMsgs.add(errorMsg);
        });
        return new Result(errorMsgs);
    }
}
