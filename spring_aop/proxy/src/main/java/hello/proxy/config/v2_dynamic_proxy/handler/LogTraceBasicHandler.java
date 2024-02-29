package hello.proxy.config.v2_dynamic_proxy.handler;

import hello.proxy.trace.LogTrace;
import hello.proxy.trace.TraceStatus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class LogTraceBasicHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;

    public LogTraceBasicHandler(Object target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
