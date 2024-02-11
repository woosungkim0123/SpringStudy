package hello.proxy.trace;

public interface TraceCallback<T>{
    T call();
}
