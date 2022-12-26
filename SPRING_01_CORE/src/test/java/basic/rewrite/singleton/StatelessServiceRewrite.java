package basic.rewrite.singleton;

public class StatelessServiceRewrite {

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

}
