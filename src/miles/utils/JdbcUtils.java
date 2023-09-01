package miles.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author by Miles
 * @date 2023/8/11
 */
public class JdbcUtils {
    private static DruidDataSource dataSource = null;//池子只需要一个就可以，因此是静态的
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    //静态代码块
    static {
        try {

            // 加载jdbc.properties配置文件
            Properties properties = new Properties();
            //推荐使用方式
//            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("D:\\Neusoft_study\\BackEnd\\Bookstores\\src\\resources\\jdbc.properties");
//            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("resources\\jdbc.properties");
//            properties.load(inputStream);
            //范式二
            properties.load(new FileInputStream("D:\\Neusoft_study\\BackEnd\\Bookstores\\src\\resources\\jdbc.properties"));

            // 创建Druid连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 有值就获取连接成功
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = conns.get();
        try {
            if (conn == null) {
                try {
                    conn = dataSource.getConnection();//从数据库连接池中获取连接
                    conns.set(conn); // 保存到ThreadLocal对象中，供后面的jdbc操作使用
                    conn.setAutoCommit(false); // 设置为手动管理事务
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 提交事务，并关闭释放连接——ThreadLocal方法
     */
    public static void commitAndClose() {
        Connection connection = conns.get();
        if (connection != null) { // 如果不等于null，说明 之前使用过连接，操作过数据库
            try {
                connection.commit(); // 提交 事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 关闭连接，资源资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要执行remove操作，否则就会出错。（因为Tomcat服务器底层使用了线程池技术）
        conns.remove();
    }

    /**
     * 回滚事务，并关闭释放连接——ThreadLocal方法
     */
    public static void rollbackAndClose() {
        Connection connection = conns.get();
        if (connection != null) { // 如果不等于null，说明 之前使用过连接，操作过数据库
            try {
                connection.rollback();//回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 关闭连接，资源资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要执行remove操作，否则就会出错。（因为Tomcat服务器底层使用了线程池技术）
        conns.remove();
    }


}
