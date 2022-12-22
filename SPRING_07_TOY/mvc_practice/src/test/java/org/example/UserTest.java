package org.example;


import org.example.password.CorrectFixedPasswordGenerator;
import org.example.password.WrongFixedPasswordGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class UserTest {

    /**
     * 랜덤으로 0 ~ 12자리 패스워드가 나오기 때문에 테스트코드가 성공할 수도 있고 실패할 수도 있음
     * 이걸 해결하려면 랜덤값이 들어오는 부분을 컨트롤 할 수 있어야함 - 인터페이스 생성
     * 운영에서는 RandomPasswordGenerator지만 테스트를 위해 Wrong, Correct를 통해서 항상 통과하는 경우와 항상 실패하는 경우를 만듬
     */

    @DisplayName("패스워드 초기화한다.")
    @Test
    void passwordTest() {
        // given
        User user = new User();

        // when
        user.initPassword(new CorrectFixedPasswordGenerator());

        // then
        assertThat(user.getPassword()).isNotNull();
    }

    @DisplayName("패스워드 요구사항에 부합하지 않아 초기화가 되지 않는다.")
    @Test
    void passwordTest2() {
        // given
        User user = new User();

        // when
        user.initPassword(new WrongFixedPasswordGenerator());

        // then
        assertThat(user.getPassword()).isNull();
    }
    @DisplayName("어노테이션FunctionalInterface 사용시 람다 사용해서도 해결가능")
    @Test
    void passwordTest3() {
        // given
        User user = new User();

        // when
        user.initPassword(() -> "abc");

        // then
        assertThat(user.getPassword()).isNull();
    }
}