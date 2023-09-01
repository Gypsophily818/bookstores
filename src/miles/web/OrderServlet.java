package miles.web;

import miles.pojo.*;
import miles.service.OrderService;
import miles.service.impl.OrderServiceImpl;
import miles.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/30
 */
public class OrderServlet extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 先获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        // 获取Userid
        User loginUser = (User) req.getSession().getAttribute("user");

        if (loginUser == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return;
        }

        Integer userId = loginUser.getId();
        // 调用orderService.createOrder(Cart,Userid);生成订单
        String orderId = orderService.createOrder(cart, userId);

        req.getSession().setAttribute("orderId", orderId);
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }

    protected void showOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        // 获取user
        String orderId = req.getParameter("orderId");
        // 根据当前userId分页查询我的订单
        Page<OrderItem> page = orderService.pageOrderDetail(orderId, pageNo, pageSize);

        page.setUrl("orderServlet?action=showOrderDetail");

        req.setAttribute("page", page);
        req.getRequestDispatcher("/pages/order/orderDetail.jsp").forward(req, resp);
    }

    protected void myOrderPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取user
        User user = (User) req.getSession().getAttribute("user");

        //1 请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2 调用BookService.page(pageNo，pageSize)：Page对象

        // 根据当前userId分页查询我的订单
        Page<Order> page = orderService.page(user.getId(), pageNo, pageSize);

        page.setUrl("orderServlet?action=myOrderPage");

        //3 保存Page对象到Request域中
        req.setAttribute("page", page);
        req.getRequestDispatcher("/pages/order/order.jsp").forward(req, resp);

    }
}
