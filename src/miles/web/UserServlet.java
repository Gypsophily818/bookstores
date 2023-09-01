package miles.web;

import com.google.gson.Gson;
import miles.pojo.User;
import miles.service.UserService;
import miles.service.impl.UserServiceImpl;
import miles.utils.JdbcUtils;
import miles.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author by Miles
 * @date 2023/8/24
 */
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private Connection conn = JdbcUtils.getConnection();


    /**
     * 注销
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1、销毁Session中用户登录的信息（或者销毁Session）
        req.getSession().invalidate();
//        2、重定向到首页（或登录页面）。
        resp.sendRedirect(req.getContextPath());
    }

    /**
     * 处理登录
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User loginUser = userService.login(new User(null, username, password, null));

        if (loginUser == null) {
            System.out.println("登录失败");
            req.setAttribute("msg", "账号或密码错误！");
            req.setAttribute("username", username);
            req.getRequestDispatcher("pages/user/login.jsp").forward(req, resp);

        } else {
            // 保存用户登录的信息到Session域中
            req.getSession().setAttribute("user", loginUser);
            req.getRequestDispatcher("pages/user/login_success.jsp").forward(req, resp);
        }
    }

    /**
     * 处理注册
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取Session中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
//        获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        User bean = new User();
        User user = WebUtils.copyParamToBean(req.getParameterMap(), bean);

//        检查验证码是否正确
        if (token != null && token.equalsIgnoreCase(code)) {
            if (userService.existsUsername(username)) {
                System.out.println("用户名" + username + "已存在！");
                // 把回显信息，保存到Request域中
                req.setAttribute("msg", "用户名已存在！！");
                req.setAttribute("username", username);
                req.setAttribute("email", email);
                // 跳回注册页面
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {//   可用
                userService.registerUser(new User(null, username, password, email));
                System.out.println("用户注册成功！");
                req.getRequestDispatcher("pages/user/regist_success.jsp").forward(req, resp);
            }
        } else {
            // 把回显信息，保存到Request域中
            req.setAttribute("msg", "验证码错误！！");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            System.out.println("验证码【" + code + "】错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }

    protected void ajaxExistUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        boolean existUsername = userService.existsUsername(username);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("existUsername", existUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);
    }

}
