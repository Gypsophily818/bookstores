package miles.jsptest;

import miles.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/22
 */
public class JspUserServlet extends HttpServlet {
    private JspDap jspDap = new JspDaoImpl();

    public List<User> getUsers() {
        List<User> all = jspDap.getAll();

        return jspDap.getAll();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> all = jspDap.getAll();
        req.setAttribute("userList", all);
        System.out.println("请求数据" + all);
        req.getRequestDispatcher("/scope.jsp").forward(req, resp);
    }
}
