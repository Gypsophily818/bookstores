package miles.test;

import org.junit.Test;

/**
 * @author by Miles
 * @date 2023/8/21
 */
public class TestFloat {
    @Test
    public void test() {
        float a = 4;
        float a1 = 4.0f;
        double a2 = 4.0;
        float b = 4.0f;

        System.out.println(a == a2);//true
        System.out.println(a == a1);//true

        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;

        System.out.println(f1 == f2);
        System.out.println(f3 == f4);

    }
}
