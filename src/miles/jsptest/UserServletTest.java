package miles.jsptest;

import java.lang.reflect.Method;

/**
 * @author by Miles
 * @date 2023/8/23
 */
public class UserServletTest {
    public void login(){
        System.out.println("login 方法被调用");
    }
    public void register(){
        System.out.println("register 方法被调用");
    }
    public void logout(){
        System.out.println("logout 方法被调用");
    }
    public void updateUser(){
        System.out.println("updateUser 方法被调用");
    }
    public void deleteUser(){
        System.out.println("deleteUser 方法被调用");
    }

    public static void main(String[] args) {
        String action = "deleteUser";
        try {
            Method declaredMethod = UserServletTest.class.getDeclaredMethod(action);

            UserServletTest userServletTest = UserServletTest.class.newInstance();
            declaredMethod.invoke(userServletTest);
            System.out.println(declaredMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
