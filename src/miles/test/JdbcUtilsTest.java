package miles.test;

import miles.JdbcTest.Employee;
import miles.JdbcTest.User;
import miles.utils.JdbcUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author by Miles
 * @date 2023/8/11
 */
public class JdbcUtilsTest {
    @Test
    public void testJdbcUtils() {
        for (int i = 0; i < 100; i++) {
            Connection connection = JdbcUtils.getConnection();

            System.out.println(connection);
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //返回10个 因为数据库设置了最大连接数为10
        }
//        System.out.println(123);
    }

    @Test
    public void testUpdate() {
        Connection connection = JdbcUtils.getConnection();

//        JdbcUtils.close(connection);
    }

    /**
     * ScalarHandler 处理特殊值 count
     *
     * @throws Exception
     */
    @Test
    public void testQuery() throws Exception {
        Connection connection = JdbcUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        System.out.println(connection);
        String sql = "select count(*) FROM employees";
        ScalarHandler handler = new ScalarHandler<>();
        Long count = (Long) queryRunner.query(connection, sql, handler);
        System.out.println("总数： " + count);//总数： 17
    }

    /**
     * MapHandler 返回键值对
     *
     * @throws Exception
     */
    @Test
    public void testQuery1() throws Exception {
        Connection connection = JdbcUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "SELECT id,name,email,birth FROM `employees` where id = ?";
        MapHandler handler = new MapHandler();
        Map<String, Object> query = queryRunner.query(connection, sql, handler, 2);
        System.out.println("结果： " + query);//
    }

    /**
     * MapListHandler 返回键值对列表
     *
     * @throws Exception
     */
    @Test
    public void testQuery2() throws Exception {
        Connection connection = JdbcUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "SELECT id,name,email,birth FROM `employees`";
        MapListHandler handler = new MapListHandler();
        List<Map<String, Object>> query = queryRunner.query(connection, sql, handler);
        query.forEach(System.out::println);
    }


    @Test
    public void testQueryMaxBirth() throws Exception {
        Connection connection = JdbcUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "SELECT max(birth) FROM `employees`";
        ScalarHandler handler = new ScalarHandler<>();
        Object query = queryRunner.query(connection, sql, handler);
        System.out.println("maxBirth: " + query);
    }

    /**
     * 没法满足要求可以自己造一个
     *
     * @throws Exception
     */
    @Test
    public void testQueryResultSetHandler() throws Exception {
        Connection connection = JdbcUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "SELECT id,name,email,birth FROM `employees` where id = ?";
        ResultSetHandler<Employee> handler = new ResultSetHandler<Employee>() {

            @Override
            public Employee handle(ResultSet resultSet) throws SQLException {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    Date birth = resultSet.getDate("birth");
                    Employee employee = new Employee(id, name, email, birth);
                    return employee;
                }
                return null;
            }
        };
        Object query = queryRunner.query(connection, sql, handler, 3);
        System.out.println("maxBirth: " + query);
    }

//    public static void closeResource(Connection connection, Statement statement, ResultSet resultSet) {
//        try {
//            DbUtils.close(connection);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            DbUtils.close(statement);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            DbUtils.close(resultSet);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        DbUtils.closeQuietly(connection);
//        DbUtils.closeQuietly(statement);
//        DbUtils.closeQuietly(resultSet);
//    }
}
