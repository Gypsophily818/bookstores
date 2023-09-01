package miles.JdbcTest.baseDAO;

import miles.JdbcTest.Employee;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/17
 */
public class EmployeeDAOImpl extends BaseDAOTest<Employee> implements EmployeeDAO {
//public class EmployeeDAOImpl extends BaseDAO implements EmployeeDAO {
    @Override
    public void insert(Connection connection, Employee employee) {
        String sql = "insert into employees (name,email,birth) values (?,?,?)";
        update(connection, sql, employee.getName(), employee.getEmail(), employee.getBirth());
    }

    @Override
    public void deleteById(Connection connection, int id) {
        String sql = "delete from employees where id = ?";
        update(connection, sql, id);
    }

    @Override
    public void update(Connection connection, Employee employee) {
        String sql = "update employees set name = ?,email = ?,birth = ? where id = ?";
        update(connection, sql, employee.getName(), employee.getEmail(), employee.getBirth(), employee.getId());
    }

    @Override
    public Employee getEmployeeById(Connection connection, int id) {
        String sql = "select id,name,email,birth from employees where id = ?";
        Employee employee = getInstance(connection, sql, id);
        return employee;
    }

    @Override
    public List<Employee> getAll(Connection connection) {
        String sql = "select id,name,email,birth from employees";
        List<Employee> employeeList = getInstanceForList(connection, sql);
        return employeeList;
    }

    @Override
    public Long getCount(Connection connection) {
        String sql = "select count(*) from employees";
        return getValue(connection, sql);
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        String sql = "select min(birth) from employees";
        return getValue(connection, sql);
    }
}
