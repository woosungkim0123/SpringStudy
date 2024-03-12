package hello;

import hello.boot.MySpringApplication;
import hello.boot.MySpringBootApplication;

@MySpringBootApplication
public class MySpringBootMain {
    public static void main(String[] args) {
        System.out.println("start spring");

        MySpringApplication.run(MySpringBootMain.class, args);
    }
}
