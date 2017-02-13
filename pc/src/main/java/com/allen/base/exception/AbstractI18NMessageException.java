package com.allen.base.exception;

/**
 * Created by Allen on 2016/12/19.
 */
public abstract class AbstractI18NMessageException extends RuntimeException{
    private static final long serialVersionUID = 2148374270769534530L;
    private String errorCode;
    protected String[] errorArgs;

    public AbstractI18NMessageException() {
        this((String)null, (String[])null, (Throwable)null);
    }

    public AbstractI18NMessageException(String debugMessage) {
        this((String)null, (String[])null, debugMessage, (Throwable)null);
    }

    public AbstractI18NMessageException(String debugMessage, Throwable cause) {
        this((String)null, (String[])null, debugMessage, cause);
    }

    public AbstractI18NMessageException(String errorCode, String[] errorArgs) {
        this(errorCode, errorArgs, (String)null, (Throwable)null);
    }

    public AbstractI18NMessageException(String errorCode, String[] errorArgs, Throwable cause) {
        this(errorCode, errorArgs, (String)null, cause);
    }

    public AbstractI18NMessageException(String errorCode, String[] errorArgs, String debugMessage, Throwable cause) {
        super(debugMessage, cause);
        this.errorCode = "UNKNOW_ERROR";
        this.errorCode = errorCode;
        this.errorArgs = errorArgs;
    }
}
