package miles.service.impl;

import miles.dao.UserDao;
import miles.dao.BaseDao;
import miles.dao.impl.UserDaoImpl;
import miles.service.UserService;
import miles.pojo.User;

import java.sql.Connection;

/**
 * @author by Miles
 * @date 2023/8/21
 */
public class UserServiceImpl extends BaseDao<User> implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        if (userDao.queryUserByUsername(username) == null) {
            return false;
        }
        return true;
    }
}
