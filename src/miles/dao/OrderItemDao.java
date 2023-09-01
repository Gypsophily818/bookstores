package miles.dao;

import miles.pojo.Order;
import miles.pojo.OrderItem;

import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public interface OrderItemDao extends BaseOrder{
    int saveOrderItem(OrderItem orderItem);

    List<OrderItem> queryOrderItemByOrderId(String orderId);

    List<OrderItem> queryForPageOrderItem(Integer begin, Integer pageSize);

    List<OrderItem> queryForPageMyOrderDetail(String orderId, Integer begin, Integer pageSize);


}
