package com.allen.base.exception;

/**
 * Created by Allen on 2016/12/19.
 */
public class BusinessException extends AbstractI18NMessageException {
    private static final long serialVersionUID = -9018571104185955115L;

    public BusinessException() {
    }

    public BusinessException(String errorCode, String[] errorArgs, String debugMessage, Throwable cause) {
        super(errorCode, errorArgs, debugMessage, cause);
    }

    public BusinessException(String errorCode, String[] errorArgs, Throwable cause) {
        super(errorCode, errorArgs, cause);
    }

    public BusinessException(String errorCode, String[] errorArgs) {
        super(errorCode, errorArgs);
    }

    public BusinessException(String debugMessage, Throwable cause) {
        super(debugMessage, cause);
    }

    public BusinessException(String debugMessage) {
        super(debugMessage);
    }
}
