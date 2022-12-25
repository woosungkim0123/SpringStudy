package org.example;

import org.example.password.PasswordGenerator;
import org.example.password.RandomPasswordGenerator;

public class User {
    private String password;

    /**
     * to-be
     */
    public void initPassword(PasswordGenerator passwordGenerator) {
        String password = passwordGenerator.generatePassword();

        /**
         * 비밀번호는 최소 8자 이상 12자 이하여야 한다.
         */
        if(password.length() >= 8 && password.length() <= 12) {
            this.password = password;
        }

    }

    /**
     * 이전 방법 as-is
     * 내부에서 생성 = 강한 결합
     */
    public void initPasswordOld() {
        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        String password = randomPasswordGenerator.generatePassword();

        /**
         * 비밀번호는 최소 8자 이상 12자 이하여야 한다.
         */
        if(password.length() >= 8 && password.length() <= 12) {
            this.password = password;
        }

    }

    public String getPassword() {
        return password;
    }

}
