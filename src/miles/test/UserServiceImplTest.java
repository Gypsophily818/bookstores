package miles.test;

import miles.service.UserService;
import miles.service.impl.UserServiceImpl;
import miles.pojo.User;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author by Miles
 * @date 2023/8/21
 */
public class UserServiceImplTest {
    private UserService userService = new UserServiceImpl();
    private Connection conn = JdbcUtils.getConnection();

    @Test
    public void registerUser() {
        User user = new User(1, "ligou", "gou111", "ligou@999.com");
        userService.registerUser(  user);
    }

    @Test
    public void login() {
        User user = new User(1, "ligou22", "gou111", "ligou@999.com");
        User loginUser = userService.login(  user);
        System.out.println(loginUser);
    }

    @Test
    public void existsUsername() {
        User user = new User(1, "ligou", "gou111", "ligou@999.com");
        boolean ligou = userService.existsUsername(  "ligou2");
        System.out.println(ligou);
    }
}