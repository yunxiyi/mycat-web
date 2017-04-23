package cn.edu.nwsuaf.model.enumation;

/**
 * Created by huangrongchao on 2017/4/17.
 */
public enum RoleType {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
