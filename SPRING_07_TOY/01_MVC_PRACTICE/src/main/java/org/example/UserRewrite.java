package org.example;

import org.example.password.PasswordGenerator;
import org.example.passwordRewrite.PasswordGeneratorRewrite;
import org.example.passwordRewrite.RandomPasswordGenerator;

public class UserRewrite {

    private String password;


    public void initPassword(PasswordGeneratorRewrite passwordGenerator) {

        String password = passwordGenerator.generatePassword();

        if(password.length() >= 8 && password.length() <= 12) {
            this.password = password;
        }
    }

    public void initPasswordOld() {

        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        String password = randomPasswordGenerator.generatePassword();

        if(password.length() >= 8 && password.length() <= 12) {
            this.password = password;
        }
    }
    public String getPassword() {
        return password;
    }
}
