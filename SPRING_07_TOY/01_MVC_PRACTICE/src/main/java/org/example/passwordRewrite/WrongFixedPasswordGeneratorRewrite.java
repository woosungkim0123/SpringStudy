package org.example.passwordRewrite;

public class WrongFixedPasswordGeneratorRewrite implements PasswordGeneratorRewrite {
    @Override
    public String generatePassword() {
        return "12";
    }
}
