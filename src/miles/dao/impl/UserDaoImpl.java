package miles.dao.impl;

import miles.dao.BaseDao;
import miles.dao.UserDao;
import miles.pojo.User;

import java.sql.Connection;

/**
 * @author by Miles
 * @date 2023/8/18
 * 针对 t_user表
 */

public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Override
    public User queryUserByUsername(String username) {
        User bean = null;
        String sql = "select id,username,password,email from t_user where username=?";
        bean = queryForOne(sql, username);
        return bean;
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into t_user(username,password,email) values(?,?,?)";
        int update = update(sql, user.getUsername(), user.getPassword(), user.getEmail());
        return update;
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        String sql = "select id,username,password,email from t_user where username=?and password=?";
        return queryForOne(sql, username, password);
    }
}
