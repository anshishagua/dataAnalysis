package com.anshishagua.object;

/**
 * User: lixiao
 * Date: 2018/5/29
 * Time: 下午4:52
 */

public class UserRole {
    private long id;
    private long userId;
    private User user;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
