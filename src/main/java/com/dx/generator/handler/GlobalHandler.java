package com.dx.generator.handler;

import com.dx.generator.model.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CompletableFuture;

@ControllerAdvice
@Slf4j
public class GlobalHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO<String> exceptionHandler(Exception e){
        CompletableFuture.runAsync(e::printStackTrace);
        return ResultVO.error(e.getCause() + e.getMessage());
    }
}
