package book.shop.service;

import book.shop.domain.Address;
import book.shop.domain.Member;
import book.shop.domain.Order;
import book.shop.domain.OrderStatus;
import book.shop.domain.item.Book;
import book.shop.exception.NotEnoughStockException;
import book.shop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {


    @Autowired
    EntityManager em;
    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void itemOrder() {
        // g
        Member member = createMember();

        Book book = createBook("우성 책", 10000, 10);

        int orderCount = 2;

        // w
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // t
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야한다");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문가격은 가격 * 수량");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다");
    }


    @Test
    public void orderItemStockOver() {
        // g
        Member member = createMember();
        Book book = createBook("우성 책", 10000, 10);
        int orderCount = 11;

        // w

        // t
        Assertions.assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount)
        );
    }

    @Test
    public void cancelOrder() {
        // g
        Member member = createMember();
        Book book = createBook("우성책", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // w
        orderService.cancelOrder(orderId);

        // t
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL 되어야한다");
        assertEquals(10, book.getStockQuantity(),"주문이 취소된 경우 재고 원상복구");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("대구", "수성구", "113-133"));
        em.persist(member);
        return member;
    }

}