package model;

import bean.User;

public class UserModel {
    private User user;
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public UserModel(User user) {
        super();
        this.user = user;
    }
    
    public UserModel() {
        super();
    }
}
