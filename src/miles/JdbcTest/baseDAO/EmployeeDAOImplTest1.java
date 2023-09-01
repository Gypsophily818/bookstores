package miles.JdbcTest.baseDAO;

import miles.JdbcTest.Employee;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;

/**
 * @author by Miles
 * @date 2023/8/17
 */
class EmployeeDAOImplTest1 {
    private EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();


//    @org.junit.jupiter.api.Test
    @Test
    void insert() {
        Connection connection = JdbcUtils.getConnection();
        Employee employee = new Employee(1, "于非", "yufei@139.com", new Date(4719235123235L));
        employeeDAO.insert(connection, employee);
        System.out.println("添加成功~");
//        JdbcUtils.close();
    }

//    @org.junit.jupiter.api.Test
    void deleteById() {
        Connection connection = JdbcUtils.getConnection();

        employeeDAO.deleteById(connection, 19);
        System.out.println("删除成功~");
//        JdbcUtils.close();
    }

//    @org.junit.jupiter.api.Test
    void update() throws ParseException {
        Connection connection = JdbcUtils.getConnection();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parse = simpleDateFormat.parse("1843-08-09");

        Date date = new Date(4345346L);
        long time1 = date.getTime();
//        String format = simpleDateFormat.format(4345346L);
//        System.out.println("4345346L--->" + format);
//
        long time = parse.getTime();
        Employee employee = new Employee(20, "于非非", "zhagnfei1122@QQ.com", new Date(time));
        employeeDAO.update(connection, employee);
        System.out.println("更新成功~");
//        JdbcUtils.close();
    }

//    @org.junit.jupiter.api.Test
    void getEmployeeById() {
        Connection connection = JdbcUtils.getConnection();

        Employee employeeById = employeeDAO.getEmployeeById(connection, 20);
        System.out.println("查询成功~" + employeeById);
//        JdbcUtils.close();
    }

//    @org.junit.jupiter.api.Test
    void getAll() {
        Connection connection = JdbcUtils.getConnection();
        List<Employee> all = employeeDAO.getAll(connection);
        all.forEach(System.out::println);
//        JdbcUtils.close();
    }

//    @org.junit.jupiter.api.Test
    void getCount() {
        Connection connection = JdbcUtils.getConnection();
        Long count = employeeDAO.getCount(connection);


        System.out.println("表中的记录数~ " + count);
//        JdbcUtils.close();
    }

//    @org.junit.jupiter.api.Test
    void getMaxBirth() {
        Connection connection = JdbcUtils.getConnection();
        Date maxBirth = employeeDAO.getMaxBirth(connection);

        System.out.println("年龄最大~；" + maxBirth);
//        JdbcUtils.close();
    }
}