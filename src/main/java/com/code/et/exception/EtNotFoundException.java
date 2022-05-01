package com.code.et.exception;

public class EtNotFoundException extends RuntimeException {

    public EtNotFoundException() {
    }

    public EtNotFoundException(String arg0) {
        super(arg0);
    }

    public EtNotFoundException(Throwable arg0) {
        super(arg0);
    }

    public EtNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public EtNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }
    
}
