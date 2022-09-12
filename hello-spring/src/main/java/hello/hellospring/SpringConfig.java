package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 스프링이 시작할 때 이 두개를 스프링 컨테이너에 올림
    // 컨트롤러는 스프링이 관리하는 것이라 컴포넌트 스캔으로 사용(Autowired 사용)
    /*
    컴포넌트 방식이 편한데 두개 장단점이 있음
    자바 코드로 직접 스프링 빈을 등록
    과거에는 자바코드로 설정하는 것이 아닌 XML 문서로 설정
    지금은 사용하지않고 자바코드로 사용
     */
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
