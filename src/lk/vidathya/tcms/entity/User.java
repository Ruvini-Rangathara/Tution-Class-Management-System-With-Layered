package lk.vidathya.tcms.entity;

import java.io.Serializable;

public class User  implements SuperEntity {
    private String staffId;
    private String username;
    private String password;
    private String passwordHint;

    public User() {
    }

    public User(String staffId, String username, String password, String passwordHint) {
        this.staffId = staffId;
        this.username = username;
        this.password = password;
        this.passwordHint = passwordHint;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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

    public String getPasswordHint() {
        return passwordHint;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    @Override
    public String toString() {
        return "User{" +
                "staffId='" + staffId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordHint='" + passwordHint + '\'' +
                '}';
    }
}
