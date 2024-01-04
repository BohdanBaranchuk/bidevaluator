package com.basis.exception;

public class BidEvaluatorException extends RuntimeException {

    public BidEvaluatorException(String message) {
        super(message);
    }

    public BidEvaluatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BidEvaluatorException(Throwable cause) {
        super(cause);
    }
}
