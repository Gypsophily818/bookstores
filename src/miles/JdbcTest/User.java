package miles.JdbcTest;

import java.math.BigDecimal;

/**
 * @author by Miles
 * @date 2023/8/16
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private BigDecimal balance;

    public User() {
    }

    public User(int id, String name, String password, String email, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
