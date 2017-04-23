package cn.edu.nwsuaf.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by huangrongchao on 2017/3/9.
 */
public class User {
    private Long id;
    private String username;
    private String password;
    private int age;
    private String email;
    private Date registerDate;
    private String validateCode;

    public User() {
    }

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.age = user.getAge();
        this.email = user.getEmail();
        this.registerDate = user.getRegisterDate();
        this.validateCode = user.getValidateCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", registerDate=" + registerDate +
                ", validateCode='" + validateCode + '\'' +
                '}';
    }
}
