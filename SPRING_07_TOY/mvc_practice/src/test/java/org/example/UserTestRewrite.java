package org.example;



import org.example.passwordRewrite.CorrectFixedPasswordGeneratorRewrite;
import org.example.passwordRewrite.WrongFixedPasswordGeneratorRewrite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class UserTestRewrite {

    @DisplayName("패스워드 초기화한다.")
    @Test
    void passwordTest() {
        // given
        UserRewrite user = new UserRewrite();

        // when
        user.initPassword(new CorrectFixedPasswordGeneratorRewrite());

        // then
        assertThat(user.getPassword()).isNotNull();
    }

    @DisplayName("패스워드 요구사항에 부합하지 않아 초기화 X")
    @Test
    void passwordTest2() {
        // given
        UserRewrite user = new UserRewrite();

        // when
        user.initPassword(new WrongFixedPasswordGeneratorRewrite());

        // then
        assertThat(user.getPassword()).isNull();
    }

    @DisplayName("어노테이션FunctionalInterface 사용시 람다 사용가능")
    @Test
    void passwordTest3() {
        // given
        UserRewrite user = new UserRewrite();

        // when
        user.initPassword(() -> "abc");

        // then
        assertThat(user.getPassword()).isNull();
    }

}