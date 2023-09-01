package miles.jsptest;

import miles.pojo.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author by Miles
 * @date 2023/8/22
 */
public class JspDaoImplTest {
    JspDap jspDap = new JspDaoImpl();
    private static List<User> userList = new JspDaoImpl().getAll();
    @Test
    public void getAll() {
//        List<User> all = jspDap.getAll();
//        all.forEach(System.out::println);
        userList.forEach(System.out::println);
    }
}