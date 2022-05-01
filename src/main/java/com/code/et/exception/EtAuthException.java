package com.code.et.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class EtAuthException extends RuntimeException {

    public EtAuthException() {
    }

    public EtAuthException(String arg0) {
        super(arg0);
    }

    public EtAuthException(Throwable arg0) {
        super(arg0);
    }

    public EtAuthException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public EtAuthException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }
    
}
