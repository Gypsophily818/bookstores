package miles.JdbcTest.baseDAO;

import miles.JdbcTest.Employee;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/17
 */
public interface EmployeeDAO {
    /**
     * employee 对象添加到数据库中
     * @param connection
     * @param employee
     */
    void insert(Connection connection, Employee employee);

    /**
     * 删除一条记录
     * @param connection
     * @param id
     */
    void deleteById(Connection connection,int id);

    /**
     * 根据内存中的employee对象修改指定的记录
     * @param connection
     * @param employee
     */
    void update(Connection connection,Employee employee);

    /**
     * 针对指定的id 查询对应的Employee对象
     * @param connection
     * @param id
     */
    Employee getEmployeeById(Connection connection,int id);

    /**
     * 返回数据库中的所有记录构成的集合
     * @param connection
     * @return
     */
    List<Employee> getAll(Connection connection);

    /**
     * 返回数据库所有条目数
     * @param connection
     * @return
     */
    Long getCount(Connection connection);

    /**
     * 返回数据库中最大的生日值
     */

    Date getMaxBirth(Connection connection);
}
