package com.systemsoftrust.thinkcell;

public class ThinkCellException extends RuntimeException {

    public ThinkCellException() {
    }

    public ThinkCellException(String message) {
        super(message);
    }

    public ThinkCellException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThinkCellException(Throwable cause) {
        super(cause);
    }
}
