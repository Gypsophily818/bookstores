package miles.test;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import miles.pojo.PersonTestJson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {

    //    1.2.1、javaBean和json的互转
    @Test
    public void test1() {
        PersonTestJson person = new PersonTestJson(1, "国哥好帅!");
        // 创建Gson对象实例
        Gson gson = new Gson();
        // toJson方法可以把java对象转换成为json字符串
        String personJsonString = gson.toJson(person);
        System.out.println(personJsonString);
        // fromJson把json字符串转换回Java对象
        // 第一个参数是json字符串
        // 第二个参数是转换回去的Java对象类型
        PersonTestJson person1 = gson.fromJson(personJsonString, PersonTestJson.class);
        System.out.println(person1);
    }

    //    1.2.2、List 和json的互转
    @Test
    public void test2() {
        List<PersonTestJson> personList = new ArrayList<>();

        personList.add(new PersonTestJson(1, "国哥"));
        personList.add(new PersonTestJson(2, "康师傅"));

        Gson gson = new Gson();

        // 把List转换为json字符串
        String personListJsonString = gson.toJson(personList);
        System.out.println(personListJsonString);

        List<PersonTestJson> list = gson.fromJson(personListJsonString, new PersonListType().getType());
        System.out.println(list);
        PersonTestJson person = list.get(0);
        System.out.println(person);
    }

    //    1.2.3、map 和json的互转
    @Test
    public void test3() {
        Map<Integer, PersonTestJson> personMap = new HashMap<>();

        personMap.put(1, new PersonTestJson(1, "国哥好帅"));
        personMap.put(2, new PersonTestJson(2, "康师傅也好帅"));

        Gson gson = new Gson();
        // 把 map 集合转换成为 json字符串
        String personMapJsonString = gson.toJson(personMap);
        System.out.println(personMapJsonString);

//        Map<Integer,Person> personMap2 = gson.fromJson(personMapJsonString, new PersonMapType().getType());
        Map<Integer, PersonTestJson> personMap2 = gson.fromJson(personMapJsonString, new TypeToken<HashMap<Integer, PersonTestJson>>() {
        }.getType());

        System.out.println(personMap2);
        PersonTestJson p = personMap2.get(1);
        System.out.println(p);

    }


}
