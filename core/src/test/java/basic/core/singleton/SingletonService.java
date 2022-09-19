package basic.core.singleton;

// 테스트 케이스가 아니라 그냥 앱에 영향을 주지 않게 하기 위해
// 자바가 뜰 때 static영역에 new로 되어있네 하면 내부적으로 실행해서 객체를 생성해서 인스턴스에 참조로 넣어놓음
public class SingletonService {

    // static 사용시 클래스 레벨에 올라가서 딱 하나만 존재
   private static final SingletonService instance = new SingletonService();

   public static SingletonService getInstance() {
       return instance;
   }
   private SingletonService() {
   }
   public void logic() {
       System.out.println("싱글톤 객체 로직 호출");
   }
}
