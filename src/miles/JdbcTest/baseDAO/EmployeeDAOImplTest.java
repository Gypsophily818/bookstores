package miles.JdbcTest.baseDAO;

import miles.JdbcTest.Employee;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;

/**
 * @author by Miles
 * @date 2023/8/22
 */
public class EmployeeDAOImplTest {
    private EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();

    @Test
    public void insert() {
        Connection connection = JdbcUtils.getConnection();
        Employee employee = new Employee(1, "于非", "yufei@139.com", new Date(4719235123235L));
        employeeDAO.insert(connection, employee);
        System.out.println("添加成功~");
//        JdbcUtils.close();
    }

    @Test
    public void deleteById() {
    }
}