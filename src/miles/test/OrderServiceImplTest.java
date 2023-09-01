package miles.test;

import miles.dao.OrderItemDao;
import miles.dao.impl.OrderItemDaoImpl;
import miles.pojo.Cart;
import miles.pojo.CartItem;
import miles.pojo.Order;
import miles.pojo.OrderItem;
import miles.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public class OrderServiceImplTest {
    OrderServiceImpl orderService = new OrderServiceImpl();

    @Test
    public void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "java从入门到精通", 1, new BigDecimal(100), new BigDecimal(100)));
        cart.addItem(new CartItem(2, "Netty入门", 1, new BigDecimal(100), new BigDecimal(100)));
        cart.addItem(new CartItem(3, "结构预算法", 1, new BigDecimal(100), new BigDecimal(100)));

        String orderId = orderService.createOrder(cart, 8);
        System.out.println(orderId);
    }

    @Test
    public void showMyOrder() {
        List<Order> orders = orderService.showMyOrders(1, 0, 4);
        orders.forEach(System.out::println);
    }
}