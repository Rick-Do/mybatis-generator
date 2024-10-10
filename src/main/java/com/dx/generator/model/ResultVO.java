package com.dx.generator.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResultVO<T> {

    /**
     * 编码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public ResultVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public  static <R> ResultVO<R> success(R data){
        return new ResultVO<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static ResultVO<Void> success(){
        return new ResultVO<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
    }

    public static <T>  ResultVO<T> error(T message){
        return new ResultVO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message);
    }


}
