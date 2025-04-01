package com.psevdo00.RestAPiICallboard.exception;

public class UserAlreadyExistsException  extends RuntimeException{

    public UserAlreadyExistsException(String massage){

        super(massage);

    }

}
