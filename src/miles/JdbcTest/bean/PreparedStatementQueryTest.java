package miles.JdbcTest.bean;

import miles.JdbcTest.Employee;
import miles.JdbcTest.Order;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/15
 */
public class PreparedStatementQueryTest {
    @Test
    public void testGetInstance() {
//        返回单行数据
        String sql = "select id,name,birth,email from Employees where id = ? ";
        Employee EmployeeInstance = getInstance(Employee.class, sql, 9);
        System.out.println(EmployeeInstance);

        String sql1 = "select order_id orderId,order_name orderName from `order` where order_id = ? ";
        Order OrderInstance = getInstance(Order.class, sql1, 3);
        System.out.println(OrderInstance);

//        返回多行数据
        String sql2 = "select id,name,birth,email from Employees where id <= ?";
        List<Employee> instance = getInstanceForList(Employee.class, sql2, 5);
        instance.forEach(System.out::println);

        String sql3 = "select order_id orderId ,order_name orderName from `order`";
        List<Order> instance1 = getInstanceForList(Order.class, sql3);
        instance1.forEach(System.out::println);


    }

    public <T> List<T> getInstanceForList(Class<T> tClass, String sql, Object... args) {
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
            ArrayList<T> tArrayList = new ArrayList<>();
            while (resultSet.next()) {
                T t = tClass.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //                获取列值
                    Object columnValue = resultSet.getObject(i + 1);
                    //                获取列名 不推荐使用！！
//                    String columnName = metaData.getColumnName(i + 1);

                    //         想要解决列名和类名字段不一致的情况 可以      获取列的别名
                    String columnName = metaData.getColumnLabel(i + 1);

                    //                反射
                    Field field = t.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                tArrayList.add(t);
            }
            return tArrayList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getInstance(Class<T> tClass, String sql, Object... args) {
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
            while (resultSet.next()) {
                T t = tClass.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //                获取列值
                    Object columnValue = resultSet.getObject(i + 1);
                    //                获取列名 不推荐使用！！
//                    String columnName = metaData.getColumnName(i + 1);

                    //         想要解决列名和类名字段不一致的情况 可以      获取列的别名
                    String columnName = metaData.getColumnLabel(i + 1);

                    //                反射
                    Field field = t.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
