package miles.test;

import miles.dao.OrderItemDao;
import miles.dao.impl.OrderItemDaoImpl;
import miles.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public class OrderItemDaoImplTest {
    OrderItemDao orderItemDao = new OrderItemDaoImpl();
    @Test
    public void saveOrderItem() {
        orderItemDao.saveOrderItem(new OrderItem(null,"java从入门到精通", 1,new BigDecimal(100),new BigDecimal(100),"1234567890"));
        orderItemDao.saveOrderItem(new OrderItem(null,"javaScript从入门到精通", 2,new BigDecimal(100),new BigDecimal(200),"1234567890"));
        orderItemDao.saveOrderItem(new OrderItem(null,"Netty入门", 1,new BigDecimal(100),new BigDecimal(100),"1234567890"));
    }

    @Test
    public void queryOrderItemByOrderId() {
        List<OrderItem> orderItems = orderItemDao.queryOrderItemByOrderId("1234567890");
        orderItems.forEach(System.out::println);
    }
    @Test
    public void queryForPageMyOrderDetail(){
        List<OrderItem> orderItems = orderItemDao.queryForPageMyOrderDetail("16934044707471", 0, 4);
        orderItems.forEach(System.out::println);
    }

}