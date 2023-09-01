package miles.JdbcTest.baseDAO;

import miles.utils.JdbcUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/16
 */
public abstract class BaseDAOTest<T> {
    private Class<T> tClass = null;

    {
//        获取当前basedao的子类继承的父类中的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        tClass = (Class<T>) actualTypeArguments[0];//泛型第一个参数
    }

    /**
     * 通用增删改 考虑事务
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
     * 返回一条结果 考虑事务
     */
    public T getInstance(Connection connection, String sql, Object... args) {
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
        } finally {
//            JdbcUtils.close();
        }
        return null;
    }

    /**
     * 用于返回多条数据 考虑事务
     */
    public List<T> getInstanceForList(Connection connection, String sql, Object... args) {
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
            ArrayList<T> tArrayList = new ArrayList<T>();
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

    public <E> E getValue(Connection connection, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        E result = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                result = (E) resultSet.getObject(1);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//            JdbcUtils.close();
        }

        return result;

    }
}
