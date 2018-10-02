package com.bbs.exception;

/**
 * 自定义异常
 */

public class CustomerException extends Exception {
    private String msg;
    private int code;

    public CustomerException() {
        super();
    }

    public CustomerException(String msg){
        super(msg);
        this.msg=msg;
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerException(Throwable cause) {
        super(cause);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
