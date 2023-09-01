package miles.service;

import miles.pojo.Cart;
import miles.pojo.Order;
import miles.pojo.OrderItem;
import miles.pojo.Page;

import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param cart
     * @param userId
     * @return
     */
    String createOrder(Cart cart, Integer userId);

    /**
     * 展示所有订单【管理员】——分页
     *
     * @param
     * @return
     */
    List<Order> showAllOrdersByUserId(Integer begin, Integer pageSize);

    /**
     * 显示所有订单【用户】——分页
     *
     * @param userId
     * @return
     */
    List<Order> showMyOrders(Integer userId, Integer begin, Integer pageSize);

    /**
     * 发货【管理员】
     *
     * @param orderId
     * @return
     */
    boolean sendOrder(Integer orderId);

    /**
     * 展示订单详情【管理员、用户】
     *
     * @param orderId
     * @return
     */

    List<OrderItem> showOrderDetail(String orderId);

    /**
     * 签收订单/确认收货【用户】
     *
     * @param orderId
     * @return
     */
    boolean receiveOrder(Integer orderId);

    Page<Order> page(Integer userId, Integer pageNo, Integer pageSize);

    Page<OrderItem> pageOrderDetail(String orderId, Integer pageNo, Integer pageSize);


}
