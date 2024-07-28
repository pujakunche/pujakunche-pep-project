package DAO;

public class BadRequestException extends Exception { 

    private Integer errorCode;

    public BadRequestException(String message) {
        super(message);
    }

    // public BadRequestException(ErrorCodes errorCode, String message) {
    //     super(errorCode, message);
    // }

    public BadRequestException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode.getCode();
    }

    public BadRequestException(String message, int i) {
    }

    public Integer getErrorCode() {
        return errorCode;
    }


}