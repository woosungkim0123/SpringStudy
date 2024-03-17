package hello.jdbc.connection;

// 상수를 모아둔 것이라 객체를 생성할 필요가 없으므로 추상 클래스로 선언 (객체 생성을 막기 위함)
public abstract class ConnectionConst {

    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
