package basic.rewrite.singleton;

public class SingletonServiceRewrite {

    private static final SingletonServiceRewrite instance = new SingletonServiceRewrite();

    public static SingletonServiceRewrite getInstance() {
        return instance;
    }

    private SingletonServiceRewrite() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
