package org.example.administrationservice.exception;

public class ApplicationException extends RuntimeException {
    private String message;
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums.getMessage());
    }
}
