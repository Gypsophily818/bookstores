package miles.jsptest;

import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author by Miles
 * @date 2023/9/1
 */
public class i18nTest {
    @Test
    public void getLocal() {
//        获取默认的
//        Locale aDefault = Locale.getDefault();
//        System.out.println(aDefault);

        // 获职中文，中文的常量的Locale 对象
        System.out.println(Locale.CHINA);
        // 获职英文，美国的常量的Locale 对象
        System.out.println(Locale.US);

        Locale us = Locale.US;

        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n", us);
        String username = resourceBundle.getString("username");
        System.out.println(username);
    }
}
