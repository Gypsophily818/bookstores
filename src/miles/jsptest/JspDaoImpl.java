package miles.jsptest;

import miles.dao.BaseDao;
import miles.pojo.User;
import miles.utils.JdbcUtils;

import java.sql.Connection;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/22
 */
public class JspDaoImpl extends BaseDao<User> implements JspDap {
    private Connection conn = JdbcUtils.getConnection();
    private static List<User> getALlUser = new JspDaoImpl().getAll();
   @Override
    public List<User> getAll() {
        List<User> bean = null;
        String sql = "select id,username,password,email from t_user";
        bean = queryForList( sql);
        return bean;
    }
}
