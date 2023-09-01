package miles.JdbcTest.bean;

import miles.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * @author by Miles
 * @date 2023/8/15
 */

/**
 * 通用增删改
 */
public class PreparedStatementUpdateTest {
    @Test
    public void testInsert() {

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String name = scanner.next();
        System.out.println("请输入邮箱：");
        String email = scanner.next();
        System.out.println("请输入生日：");
        String birth = scanner.next();

        String sql = "insert into employees (name,email,birth) values (?,?,?)";
        int insertCount = update(sql, name, email, birth);
        System.out.println((insertCount > 0) ? "添加成功" : "添加失败");
    }

    /**
     * 未考虑事务
     * @param sql
     * @param args
     * @return
     */
    public static int update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {

            connection = JdbcUtils.getConnection();
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JdbcUtils.close();
        }
        return 0;

    }
}
