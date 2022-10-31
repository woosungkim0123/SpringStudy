package basic.core.singleton;

public class StatelessService {

    // private int price;

    // 지역변수로 사용
    public int order(String name, int price) {
        System.out.println("name = " +  name + " price = " + price);
        return price;
    }

}
