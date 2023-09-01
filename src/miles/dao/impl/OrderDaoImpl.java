package miles.dao.impl;

import miles.dao.BaseDao;
import miles.dao.OrderDao;
import miles.pojo.Order;
import miles.pojo.User;
import miles.utils.JdbcUtils;

import java.sql.Connection;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    private Connection conn = JdbcUtils.getConnection();

    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order (order_id , create_time , price , status , user_id) values (?,?,?,?,?)";
        return update(  sql, order.getOrderId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getUserId());
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "select `order_id` orderId , `create_time` createTime , `price` , `status` , `user_id` userId from t_order";
        return queryForList(  sql);
    }

    @Override
    public int changOrderStatus(String orderId, Integer status) {
        String sql = "update t_order SET `status`=? where `order_id`=?";
        return update(  sql, status, orderId);
    }

    @Override
    public Order queryOrderByUserId(User user) {
        String sql = "select `order_id` orderId , `create_time` createTime , `price` , `status` , `user_id` userId from t_order where user_id = ?";
        return queryForOne(  sql, user.getId());
    }

    @Override
    public List<Order> queryForPageMyOrder(Integer userId, Integer begin, Integer pageSize) {
        String sql = "select `order_id` orderId , `create_time` createTime , `price` , `status` , `user_id` userId from t_order where `user_id` = ? limit ?,?";
        return queryForList(  sql, userId, begin, pageSize);
    }

    //    @Override
//    public Integer queryForPageTotalCount(Integer userId) {
//        String sql = "select count(*) from t_order where `user_id` = ?";
//        Number count = (Number) queryForGetSingleVal(  sql,userId);
//        return count.intValue();
//    }
    @Override
    public <T> Integer queryForPageTotalCount(T userId) {
        String sql = "select count(*) from t_order where `user_id` = ?";
        Number count = (Number) queryForGetSingleVal(  sql, userId);
        return count.intValue();
    }
}
