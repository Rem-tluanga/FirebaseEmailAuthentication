package com.example.firebaseemailauthentication;

public class User {

    String Name, Email,Phone_No;

    public User(){}

    public User(String name, String email, String phone_No) {
        Name = name;
        Email = email;
        Phone_No = phone_No;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }
}
