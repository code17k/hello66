package com.example.fact_train.bean;

public class User {

    private int id;
    private String name;
    private String Password;

    public User(String username, String password) {
        this.name = username;
        this.Password = password;
    }

    public User() {
      
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    
}