package miles.JdbcTest.transaction_;

import miles.JdbcTest.User;
import miles.JdbcTest.bean.PreparedStatementUpdateTest;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/16
 */
public class TransactionTest {
    /**
     * order表的 100+- 的转账操作
     * 不考虑事务的操作
     */
    @Test
    public void testUpdate() {//1.0
        String sql1 = "update `t_user` set balance = balance - 100 where username = ?";
        PreparedStatementUpdateTest.update(sql1, "zhangsan");

        String sql2 = "update `t_user` set balance = balance + 100 where username = ?";
        PreparedStatementUpdateTest.update(sql2, "lisi");

        System.out.println("转账成功");
    }

    @Test
    public void testUpdateWithTx() {//2.0
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            connection.setAutoCommit(false);

            String sql1 = "update `t_user` set balance = balance - 100 where username = ?";
            update(connection, sql1, "zhangsan");

            System.out.println(12 / 0);

            String sql2 = "update `t_user` set balance = balance + 100 where username = ?";
            update(connection, sql2, "lisi");


            System.out.println("转账成功");
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            //        关闭资源
//            JdbcUtils.close();
        }

    }

    /**
     * 考虑事务
     *
     * @param sql
     * @param args
     * @return
     */
    public static int update(Connection connection, String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            修改为自动提交数据
//            主要针对数据库连接池时使用
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//            JdbcUtils.close();
        }
        return 0;

    }

    /**
     * ox001
     * 数据库开启事务 更新
     */
    @Test
    public void testTransactionUpdate() {
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement ps = null;
        try {
            System.out.println(connection.getTransactionIsolation());
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);

            String sql2 = "update `t_user` set balance = 999 where username = ?";
            update(connection, sql2, "lisi");

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String sql = "select id,username name,password,email,balance from t_user where id < ?";
            ps = connection.prepareStatement(sql);

            List<User> instanceForList = getInstanceForList(connection, User.class, sql, 5);
            for (User user : instanceForList) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                ps.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
//            JdbcUtils.close();
        }
    }

    /**
     * ox001
     * 数据库开启事务 查询
     */
    @Test
    public void testTransactionSelect() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtils.getConnection();
            connection.setAutoCommit(false);
            String sql = "select id,username name,password,email,balance from t_user where id < ?";
            ps = connection.prepareStatement(sql);

            List<User> instanceForList = getInstanceForList(connection, User.class, sql, 5);
            for (User user : instanceForList) {
                System.out.println(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                ps.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
//            JdbcUtils.close();
        }
    }

    public <T> List<T> getInstanceForList(Connection connection, Class<T> tClass, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
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
        } finally {
//            JdbcUtils.close();
        }
        return null;
    }
}
