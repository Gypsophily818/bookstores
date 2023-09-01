package miles.JdbcTest.bean;

import miles.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author by Miles
 * @date 2023/8/16
 */
public class InsertTest {
    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtils.getConnection();
            long start = System.currentTimeMillis();
            String sql = "insert into goods (name)values(?)";

//            设置不允许自动提交数据
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(sql);

//            for (int i = 1; i <= 20000; i++) {
            for (int i = 1; i <= 1000000; i++) {
                ps.setObject(1, "name_" + i);
                ps.addBatch();//添加
                if (i % 500 == 0) {
                    ps.executeBatch();//执行
                    ps.clearBatch();//清空
                }
            }
            //20000 系统花费时间： 17350
            //20000:开启Batch   系统花费时间： 168
            //1000000:开启Batch 系统花费时间： 4458
//            同意提交数据
            connection.commit();

            //1000000:开启Batch+关闭自动提交 系统花费时间： 3022


            long end = System.currentTimeMillis();
            System.out.println("系统花费时间： " + (end - start));
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
}
