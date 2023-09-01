package miles.service;

import miles.pojo.User;

import java.sql.Connection;

/**
 * @author by Miles
 * @date 2023/8/21
 */
public interface UserService {
    /**
     * 注册用户
     *
     * @param user
     */
    void registerUser(  User user);

    /**
     * 登录
     *
     * @param user
     */
    User login(  User user);

    /**
     * 检查用户名是否可用
     *
     * @param username
     * @return
     */
    boolean existsUsername(  String username);

}
