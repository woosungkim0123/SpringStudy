package hello.proxy.config.v2_dynamic_proxy.handler;

import hello.proxy.trace.LogTrace;
import hello.proxy.trace.TraceStatus;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 메서드 이름 필터 기능 추가
 * - 특정 메서드에 로그를 찍지 않도록 필터링 기능 추가
 */
public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns; // 추가

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 메서드 이름 필터링 추가
        String methodName = method.getName();
        // save, request, reque*, *est - 매칭 안되면 로그 안찍음
        if(!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;
        try {
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()"; // 메타정보로 부터 메세지 만듬
            status = logTrace.begin(message);
            //로직 호출
            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
