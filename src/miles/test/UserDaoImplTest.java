package miles.test;

import miles.dao.impl.UserDaoImpl;
import miles.pojo.User;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * @author by Miles
 * @date 2023/8/18
 */
public class UserDaoImplTest {
    private UserDaoImpl userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() {
        Connection connection = JdbcUtils.getConnection();
        User user = userDao.queryUserByUsername("admin");
        if (user.getUsername().equals("admin")) {
            System.out.println("用户名不可用!");
        } else {
            System.out.println("用户名可用!");
        }
    }

    @Test
    public void saveUser() {
    }

    @Test
    public void queryUserByUsernameAndPassword() {
    }
}