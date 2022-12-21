package org.example;

public class PasswordValidator {

    private static final String WRONG_PASSWORD_LENGTH_EXCEPTION_MESSAGE = "비밀번호는 최소 8자 이상 12자 이하여야 한다.";

    public static void validate(String password) {
        int length = password.length();
        
        // ctrl + alt + v로 한번에 변수로 바꾸기
        if(length < 8 || length > 12) {
            throw new IllegalArgumentException(WRONG_PASSWORD_LENGTH_EXCEPTION_MESSAGE);
        }


    }

}
