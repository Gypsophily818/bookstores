package miles.service.impl;

import miles.dao.BaseOrder;
import miles.dao.BookDao;
import miles.dao.OrderDao;
import miles.dao.OrderItemDao;
import miles.dao.impl.BookDaoImpl;
import miles.dao.impl.OrderDaoImpl;
import miles.dao.impl.OrderItemDaoImpl;
import miles.pojo.*;
import miles.service.OrderService;
import miles.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private Connection conn = JdbcUtils.getConnection();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        System.out.println(" OrderServiceImpl 程序在[" +Thread.currentThread().getName() + "]中");

        // 订单号===唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        // 创建一个订单对象
        Order order = new Order(orderId, new Timestamp(new Date().getTime()), cart.getTotalPrice(), 0, userId);
        // 保存订单
        orderDao.saveOrder(order);

        // 遍历购物车中每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            // 获取每一个购物车中的商品项
            CartItem cartItem = entry.getValue();
            // 转换为每一个订单项
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            // 保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            // 更新库存和销量
            Book book = bookDao.queryBookById(  cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateBook(  book);
        }
        // 清空购物车
        cart.clear();

        return orderId;
    }

    @Override
    public List<Order> showAllOrdersByUserId(Integer begin, Integer pageSize) {
        orderDao.queryOrders();
        return null;
    }

    @Override
    public List<Order> showMyOrders(Integer userId, Integer begin, Integer pageSize) {
        return orderDao.queryForPageMyOrder(userId, begin, pageSize);
    }

    @Override
    public boolean sendOrder(Integer orderId) {
        return false;
    }

    @Override
    public List<OrderItem> showOrderDetail(String orderId) {

        return null;
    }

    @Override
    public boolean receiveOrder(Integer orderId) {
        return false;
    }


    @Override
    public Page<Order> page(Integer userId, Integer pageNo, Integer pageSize) {
        Page<Order> page = new Page<>();
        setPageProperties(page, userId, orderDao, pageNo, pageSize);
        // 求当前页数据
        List<Order> items = orderDao.queryForPageMyOrder(userId, page.getBegin(), page.getPageSize());
        // 设置当前页数据
        page.setItems(items);
        return page;
    }

    @Override
    public Page<OrderItem> pageOrderDetail(String orderId, Integer pageNo, Integer pageSize) {
        Page<OrderItem> page = new Page<>();
        setPageProperties(page, orderId, orderItemDao, pageNo, pageSize);
        List<OrderItem> items = orderItemDao.queryForPageMyOrderDetail(orderId, page.getBegin(), page.getPageSize());
        // 设置当前页数据
        page.setItems(items);
        return page;
    }

    private <T> void setPageProperties(Page page, T id, BaseOrder orderDao, Integer pageNo, Integer pageSize) {
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        // 求总记录数【管理员】
        Integer pageTotalCount = orderDao.queryForPageTotalCount(id);
        // 设置总记录数
        page.setPageTotalCount(pageTotalCount);
        // 求总页码
        Integer pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal += 1;
        }
        // 设置总页码
        page.setPageTotal(pageTotal);
        // 设置当前页码
        page.setPageNo(pageNo);
        // 求当前页数据的开始索引
        int begin = (page.getPageNo() - 1) * pageSize;
        page.setBegin(begin);
    }


}
