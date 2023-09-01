package miles.dao;

import miles.pojo.User;

import java.sql.Connection;

/**
 * @author by Miles
 * @date 2023/8/18
 */
public interface UserDao {
    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return 返回null 没有查到
     */
    User queryUserByUsername(String username);

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    int saveUser( User user);

    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return 返回null 没有查到
     */
    User queryUserByUsernameAndPassword(String username, String password);

}
