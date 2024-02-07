package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    //private final DataSource dataSource;

    //@Autowired
    //public SpringConfig(DataSource dataSource) {
    //    this.dataSource = dataSource;
    //}

    // JPA

    //private EntityManager em;

    //@Autowired
    //public SpringConfig(EntityManager em) {
    //    this.em = em;
    //}

    // 스프링 데이터 jpa가 jpaRepository를 받고있는 interface를 구현체를 자동으로 만들어서 스프링빈에 자동으로 등록해줌
    // 우린 그걸 자동으로 가져와서 사용하면 됨(인젝션 받으면됨), spring data jpa가 만든 구현체가 등록이됨
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 스프링이 시작할 때 이 두개를 스프링 컨테이너에 올림
    // 컨트롤러는 스프링이 관리하는 것이라 컴포넌트 스캔으로 사용(Autowired 사용)
    /*
    컴포넌트 방식이 편한데 두개 장단점이 있음
    자바 코드로 직접 스프링 빈을 등록
    과거에는 자바코드로 설정하는 것이 아닌 XML 문서로 설정
    지금은 사용하지않고 자바코드로 사용
     */
    /*
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
*/

    // 스프링 DATA JPA
    // 멤버서비스에 의존관계 세팅을 해줘야함
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    // 컴포넌트 써도되고 빈등록해서 써도되고 (빈 등록이 좋은듯 이걸 보고 AOP쓰는구나 라고 생각)
    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
    //@Bean
    //public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
        // return new JdbcMemberRepository(dataSource);

        // jdbc 템플릿
        // return new JdbcTemplateMemberRepository(dataSource);

        // jpa
        // return new JpaMemberRepository(em);

    //}
    


}
