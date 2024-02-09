package hello.proxy.pureproxy;

import hello.proxy.pureproxy.code.CacheProxy;
import hello.proxy.pureproxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.code.RealSubject;
import hello.proxy.pureproxy.code.Subject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    /**
     * operation 반환 값이 변하지 않는 데이터인데 어딘가 보관해두고 사용하면 성능상 좋습니다. (캐시)
     * client -> realSubject
     */
    @Test
    void noProxyTest() {
        Subject subject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(subject);

        client.execute();
        client.execute();
        client.execute();
    }

    /**
     * 코드를 전혀 수정하지 않고 프록시 객체를 통해서 캐시를 적용할 수 있습니다.
     * client -> proxy -> realSubject
     */
    @Test
    void proxyTest() {
        Subject subject = new RealSubject(); // 실제 객체
        Subject proxy = new CacheProxy(subject); // 프록시
        ProxyPatternClient client = new ProxyPatternClient(proxy);

        client.execute(); // 캐시가 없어서 realSubject.operation() 호출 -> 캐시 저장 (1초)
        client.execute(); // 캐시가 있어서 realSubject.operation() 호출하지 않고 캐시 반환 (0초)
        client.execute(); // 캐시가 있어서 realSubject.operation() 호출하지 않고 캐시 반환 (0초)
    }

}
