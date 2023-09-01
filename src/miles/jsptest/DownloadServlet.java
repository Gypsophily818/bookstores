package miles.jsptest;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author by Miles
 * @date 2023/8/23
 */
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1，获取要下载的文件名
        String downloadFileName = "cat.jpg";
        //        2，读取要下载的文件内容
        ServletContext servletContext = getServletContext();
//        3在回传前，通过响应头告诉客户端返回的数据类型
        String mimeType = servletContext.getMimeType("file/" + downloadFileName);
        resp.setContentType(mimeType);

//        4，还要告诉客户端收到的数据是用于下载使用(还是使用响应头)
        resp.setHeader("Content-Disposition", "attachment;filename=" + new String(downloadFileName.getBytes(), "utf-8"));

//        5，把下载的文件内容回传给客户端
        InputStream resourceAsStream = servletContext.getResourceAsStream("file/" + downloadFileName);
        ServletOutputStream outputStream = resp.getOutputStream();
        IOUtils.copy(resourceAsStream, outputStream);
    }
}

