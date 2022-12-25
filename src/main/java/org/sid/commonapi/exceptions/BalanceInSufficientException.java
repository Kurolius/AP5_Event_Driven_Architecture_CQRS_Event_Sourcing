package org.sid.commonapi.exceptions;

public class BalanceInSufficientException extends RuntimeException {
    public BalanceInSufficientException(String message) {
        super(message);
    }
}
