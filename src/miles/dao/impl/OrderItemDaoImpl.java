package miles.dao.impl;

import miles.dao.BaseDao;
import miles.dao.OrderItemDao;
import miles.pojo.Order;
import miles.pojo.OrderItem;
import miles.utils.JdbcUtils;

import java.sql.Connection;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public class OrderItemDaoImpl extends BaseDao<OrderItem> implements OrderItemDao {
    private Connection conn = JdbcUtils.getConnection();

    @Override
    public int saveOrderItem(OrderItem orderItem) {
        System.out.println(" OrderItemDaoImpl 程序在[" +Thread.currentThread().getName() + "]中");

        String sql = "insert into t_order_item(`id`,`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?,?)";
        return update(  sql, orderItem.getId(), orderItem.getName(), orderItem.getCount(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> queryOrderItemByOrderId(String orderId) {
        String sql = "select `id` `name`,`count`,`price`,`total_price` totalPrice,`order_id` orderId from t_order_item where order_id = ?";
        return queryForList(  sql, orderId);
    }

    @Override
    public List<OrderItem> queryForPageOrderItem(Integer begin, Integer pageSize) {
        return null;
    }

    @Override
    public List<OrderItem> queryForPageMyOrderDetail(String orderId, Integer begin, Integer pageSize) {
        String sql = "select `id` ,`name`, `count`, `price`, `total_price` totalPrice,`order_id` orderId from t_order_item where `order_id` = ? limit ?,?";
        return queryForList(  sql, orderId, begin, pageSize);
    }

//    @Override
//    public Integer queryForPageTotalCount(String orderId) {
//        String sql = "select count(*) from t_order_item WHERE order_id = ?";
//        Number count = (Number) queryForGetSingleVal(  sql,orderId);
//        return count.intValue();
//    }

    @Override
    public <T> Integer queryForPageTotalCount(T id) {
        String sql = "select count(*) from t_order_item WHERE order_id = ?";
        Number count = (Number) queryForGetSingleVal(  sql,id);
        return count.intValue();
    }
}
