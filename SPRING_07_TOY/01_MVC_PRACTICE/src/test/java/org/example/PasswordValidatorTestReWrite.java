package org.example;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

class PasswordValidatorTestReWrite {
    @DisplayName("패스워드 8자이상 12자 이하면 예외가 발생하지 않는다.")
    @Test
    void validatePasswordTest() {
        assertThatCode(() -> PasswordValidatorReWrite.validate("abcdefghi"))
                .doesNotThrowAnyException();
    }

    @DisplayName("비밀번호가 최소 8자 미만, 12자 초과면 IllegalArgumentException 예외가 발생")
    @ParameterizedTest
    @ValueSource(strings = {"abcd", "abcdefghijklm"})
    void validatePasswordTest2(String password) {
        assertThatCode(() -> PasswordValidatorReWrite.validate(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 최소 8자 이상 12자 이하여야 한다.");

    }
}
