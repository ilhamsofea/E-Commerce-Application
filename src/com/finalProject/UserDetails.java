package com.finalProject;
public class UserDetails {
    private static UserDetails instance;
    private String loggedInUser;
    
    private UserDetails() {
        // private constructor to prevent instantiation
    }
    
    public static UserDetails getInstance() {
        if (instance == null) {
            instance = new UserDetails();
        }
        return instance;
    }
    
    public String getLoggedInUser() {
        return loggedInUser;
    }
    
    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
