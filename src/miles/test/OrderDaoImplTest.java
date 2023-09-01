package miles.test;

import miles.dao.OrderDao;
import miles.dao.impl.OrderDaoImpl;
import miles.pojo.Order;
import miles.pojo.User;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public class OrderDaoImplTest {
    private OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void saveOrder() {
        Order order = new Order("DD11111", new Timestamp(new Date().getTime()), new BigDecimal(33.99), 0, 5);
        orderDao.saveOrder(order);
    }

    @Test
    public void queryOrders() {
        List<Order> orders = orderDao.queryOrders();
        orders.forEach(System.out::println);
    }

    @Test
    public void changOrderStatus() {
        int i = orderDao.changOrderStatus("DD11111", 1);
        System.out.println("已更新: " + i+"条数据");
    }

    @Test
    public void queryOrderByUserId() {
        User user = new User(1, null, null, null);
        Order order = orderDao.queryOrderByUserId(user);
        System.out.println(order);
    }
}