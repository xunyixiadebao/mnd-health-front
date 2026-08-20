package org.example.djiankang.exception;

import lombok.Getter;

@Getter
public class HisException extends RuntimeException {

    private final int code;

    public HisException(String message) {
        this(message, 500);
    }

    public HisException(String message, int code) {
        super(message);
        this.code = code;
    }

    public HisException(String message, Throwable cause) {
        this(message, 500, cause);
    }

    public HisException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public HisException(Throwable cause) {
        this(cause.getMessage() != null ? cause.getMessage() : "系统异常", 500, cause);
    }
}