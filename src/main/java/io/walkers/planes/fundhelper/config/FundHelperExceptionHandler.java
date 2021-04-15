package io.walkers.planes.fundhelper.config;

import com.google.common.collect.Lists;
import io.walkers.planes.fundhelper.entity.pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理
 *
 * @author Planeswalker23
 */
@Slf4j
@RestControllerAdvice
public class FundHelperExceptionHandler {

    /**
     * 拦截捕捉业务性自定义异常 {@link RuntimeException}
     *
     * @param e 业务性自定义异常
     * @return {@link Response}
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Response<String> windfallExceptionHandler(RuntimeException e) {
        log.warn(e.getMessage());
        return Response.failed(e.getMessage());
    }

    /**
     * 拦截捕捉所有异常 {@link Exception}
     *
     * @param e 其他所有未定义的异常
     * @return {@link Response}
     */
    @ExceptionHandler(value = Exception.class)
    public Response<String> allExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return Response.failed("系统异常，请联系管理员");
    }

    /**
     * 拦截捕捉参数校验异常 {@link BindException}
     *
     * @param e 参数校验异常
     * @return {@link Response}
     */
    @ExceptionHandler({BindException.class})
    public Response<String> bindExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        // 拼接校验异常信息
        StringBuilder errorMessage = new StringBuilder("Parameter validate failed: ");
        List<String> defaultMessages = Lists.transform(bindingResult.getFieldErrors(), FieldError::getDefaultMessage);
        errorMessage.append(String.join(", ", defaultMessages));
        // 参数校验异常
        log.warn(errorMessage.toString());
        return Response.failed(errorMessage.toString());
    }
}
