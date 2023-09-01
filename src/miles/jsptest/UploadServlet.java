package miles.jsptest;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/23
 */
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
//        byte[] buff = new byte[1024000];
//        int read = inputStream.read(buff);
//        System.out.println(new String(buff,0,read));

//        只有多字段的数据才是文件上传
        if (ServletFileUpload.isMultipartContent(req)) {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload((fileItemFactory));
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);

                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        String fieldName = fileItem.getFieldName();
                        String string = fileItem.getString("UTF-8");
                        System.out.println("表单的name " + fieldName);
                        System.out.println("表单的val " + string);
                    } else {
                        String fieldName = fileItem.getFieldName();
                        String fileName = fileItem.getName();

                        System.out.println("表单的name " + fieldName); 
                        System.out.println("文件名 " + fileName);

                        fileItem.write(new File("D:\\Neusoft_study\\BackEnd\\Bookstores\\web\\" + fileName));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
