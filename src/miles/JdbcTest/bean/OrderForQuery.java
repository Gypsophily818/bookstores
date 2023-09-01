package miles.JdbcTest.bean;

import miles.JdbcTest.Order;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author by Miles
 * @date 2023/8/15
 */
public class OrderForQuery {
    @Test
    public void testOrderQuery(){
//        String sql = "select order_id,order_name from `order` where order_id = ?";
        String sql = "select order_id as orderId ,order_name as orderName from `order` where order_id = ?";
        Order order = orderForQuery(sql, 1);
//        order_id 与 类Order中的字段orderId 不一致造成的
//        可以用数据库起别名的+getColumnLabel() 方式来解决 ！！
        System.out.println(order);//java.lang.NoSuchFieldException: order_id

    }
    /**
     * 针对order的通用查询操作
     */
    public Order orderForQuery(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
//        预编译sql语句
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
//        执行获取结果
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    //                获取列值
                    Object columnValue = resultSet.getObject(i + 1);
                    //                获取列名 不推荐使用！！
//                    String columnName = metaData.getColumnName(i + 1);

                    //         想要解决列名和类名字段不一致的情况 可以      获取列的别名
                    String columnName = metaData.getColumnLabel(i + 1);

                    //                反射
                    Field field = Order.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(order, columnValue);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JdbcUtils.close();
        }
        return null;
    }

    @Test
    public void testQuery1() {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
//            String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setObject(1, 1);
//            if (resultSet.next()) {
//                int id = (int) resultSet.getObject(1);
//                String name = (String) resultSet.getObject(2);
//                Date date = (Date) resultSet.getObject(3);
//
//                Order order = new Order(id, name, date);
//                System.out.println(order);
//            }

            String sql = "select order_id,order_name,order_date from `order`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = (int) resultSet.getObject(1);
                String name = (String) resultSet.getObject(2);
                Date date = (Date) resultSet.getObject(3);

                Order order = new Order(id, name, date);
                System.out.println(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        JdbcUtils.close();
    }
}
