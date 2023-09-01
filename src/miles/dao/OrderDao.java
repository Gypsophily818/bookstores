package miles.dao;

import miles.pojo.Order;
import miles.pojo.User;

import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/29
 */
public interface OrderDao extends BaseOrder{
    int saveOrder(Order order);

    List<Order> queryOrders();

    int changOrderStatus(String orderId, Integer status);

    Order queryOrderByUserId(User user);

    /**
     * 查询我的订单【分页】
     *
     * @param begin
     * @param pageSize
     * @return
     */
    List<Order> queryForPageMyOrder(Integer userId, Integer begin, Integer pageSize);


}
