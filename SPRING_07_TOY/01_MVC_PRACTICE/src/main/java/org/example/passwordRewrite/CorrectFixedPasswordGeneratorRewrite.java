package org.example.passwordRewrite;

public class CorrectFixedPasswordGeneratorRewrite implements PasswordGeneratorRewrite {
    @Override
    public String generatePassword() {
        return "12345678";
    }
}
