package miles.JdbcTest;

import miles.utils.JdbcUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author by Miles
 * @date 2023/8/16
 * 插入Blob类型数据
 */
public class BlobTest {
    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "insert into employees(name,email,birth,photo)values(?,?,?,?)";
            ps = connection.prepareStatement(sql);

            ps.setObject(1, "唐发威");
            ps.setObject(2, "tang@qq.com");
            ps.setObject(3, "2020-08-15");
            InputStream is = new FileInputStream(new File("D:\\Neusoft_study\\BackEnd\\Bookstores\\src\\miles\\JdbcTest\\img\\catH.jpg"));
            ps.setBlob(4, is);

            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("添加成功！");
            } else {
                System.out.println("添加失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JdbcUtils.close();
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 查询Blob
     */
    @Test
    public void testQuery() {
        Connection connection = null;
        PreparedStatement ps = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            connection = JdbcUtils.getConnection();
            String sql = "select id,name,email,birth,photo from employees where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, 41);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //            方式一
//                int id = rs.getInt(1);
//                String name = rs.getString(2);
//                String email = rs.getString(3);
//                Date birth = rs.getDate(4);
//                Blob photo = rs.getBlob(5);

                //            方式二
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");

//                将blob类型的字段下载下来
                Blob photo = rs.getBlob("photo");
                inputStream = photo.getBinaryStream();
                fos = new FileOutputStream("tang.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();

                Employee employee = new Employee(id, name, email, birth);

                System.out.println(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JdbcUtils.close();
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if (fos != null) {
                    fos.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
