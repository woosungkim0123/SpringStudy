package basic.rewrite.order;


public interface OrderServiceRewrite {

    OrderRewrite createOrder(Long memberId, String itemName, int itemPrice);
}
