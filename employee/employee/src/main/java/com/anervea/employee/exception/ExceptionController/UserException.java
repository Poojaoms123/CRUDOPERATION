package com.anervea.employee.exception.ExceptionController;

public class UserException extends RuntimeException {

    public static class SaveException extends RuntimeException{
    }
    public static class UpdateException extends RuntimeException{
    }

}
