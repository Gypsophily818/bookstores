package miles.dao;

import miles.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 针对于数据库增删改查操作 org.apache.commons.dbutils包属于Apache组织提供的开源JDBC工具类库
 *
 * @author by Miles
 * @date 2023/8/11
 */
public abstract class BaseDao<T> {
    private QueryRunner queryRunner = new QueryRunner();
    // 定义一个变量来接收泛型的类型
    private Class<T> type;

    // 获取T的Class对象，获取泛型的类型，泛型是在被子类继承时才确定
//    public BaseDao() {
//        // 获取子类的类型
//        Class clazz = this.getClass();
//        // 获取父类的类型
//        // getGenericSuperclass()用来获取当前类的父类的类型
//        // ParameterizedType表示的是带泛型的类型
//        Type genericSuperclass = clazz.getGenericSuperclass();
//        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
//        // 获取具体的泛型类型 getActualTypeArguments获取具体的泛型的类型
//        // 这个方法会返回一个Type的数组
//        Type[] types = parameterizedType.getActualTypeArguments();
//        // 获取具体的泛型的类型·
//        this.type = (Class<T>) types[0];
//    }

    {
//        获取当前basedao的子类继承的父类中的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        type = (Class<T>) actualTypeArguments[0];//泛型第一个参数
    }

    /**
     * 通用的增删改操作
     *
     * @param sql
     * @param args
     * @return 返回0
     */
    public int update(String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        int count = 0;
        try {
            count = queryRunner.update(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return count;

    }

    /**
     * 获取一个对象 考虑事务
     *
     * @param sql
     * @param args
     * @return
     */
    public T queryForOne(String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();
        T t = null;
        try {
            t = queryRunner.query(conn, sql, new BeanHandler<>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * 返回所有对象
     *
     * @param sql
     * @param args
     * @return
     */
    public List<T> queryForList(String sql, Object... args) {
        Connection conn = JdbcUtils.getConnection();

        List<T> list = null;
        try {
            list = queryRunner.query(conn, sql, new BeanListHandler<>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 获取一个但一值得方法，专门用来执行像 select count(*)...这样的sql语句
     *
     * @param sql
     * @param args
     * @return
     */
    public Object queryForGetSingleVal(String sql, Object... args) {
//        Object t = null;
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new ScalarHandler<>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

