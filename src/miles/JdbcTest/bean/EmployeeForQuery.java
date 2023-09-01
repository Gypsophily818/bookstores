package miles.JdbcTest.bean;

import miles.JdbcTest.Employee;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author by Miles
 * @date 2023/8/15
 */
public class EmployeeForQuery {
    /**
     * 针对Employees表的通用的查询操作
     */
    public Employee queryForEmployee(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
//        获取结果记得元数据 ResultSetMetaDate
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (rs.next()) {
                Employee employee = new Employee();
                for (int i = 0; i < columnCount; i++) {
                    Object empVal = rs.getObject(i + 1);
//                    String columnName = metaData.getColumnName(i + 1);
                    String columnName = metaData.getColumnLabel(i + 1);

                    //                反射
                    Field declaredField = Employee.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(employee, empVal);

                }
                return employee;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testQueryForEmployee() {
        String sql = "select id,name,birth,email from Employees where id = ? ";
        Employee employee = queryForEmployee(sql, 3);
        System.out.println(employee);//Employee{id=3, name='汤唯', email='tw@example.com', birth=1987-08-04}


        String sql1 = "select name,email from Employees where name = ? ";
        Employee cl = queryForEmployee(sql1, "成龙");
        /**
         * 查询到的会赋值到Employee 没查的会是 new 对象时构造器的 默认值！！
         */
        System.out.println("成龙： " + cl);//Employee{id=0, name='成龙', email='JackChan@gmail.com', birth=null}

    }
}
