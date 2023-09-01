package miles.JdbcTest;

import java.sql.Blob;
import java.util.Date;

/**
 * @author by Miles
 * @date 2023/8/15
 */

/**
 * ORM 对象关系映射
 * 映射数据库的内容
 */
public class Employee {
    private int id;
    private String name;
    private String email;
    private Date birth;

    public Employee() {
    }

    public Employee(int id, String name, String email, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }
}
