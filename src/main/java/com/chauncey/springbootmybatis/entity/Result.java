package com.chauncey.springbootmybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    //业务状态码 0-成功 1-失败 401-没有权限
    private Integer code;
    private String message;
    private T data;
    public static <E> Result<E> success(E data){return new Result<>(0,"操作成功",data);}
    public static Result success(){return new Result(0,"操作成功",null);}
    public static <E> Result<E> error(Integer code){return new Result<>(code,"无效的用户信息",null);}
    public static Result error(String message){return new Result(1,message,null);}
}
