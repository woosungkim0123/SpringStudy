package book.shop.api;

import book.shop.domain.Order;
import book.shop.domain.OrderSearch;
import book.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * ToOne(ManyToONE, OneToOne)
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        return orderRepository.findAllByString(new OrderSearch());
    }

}
