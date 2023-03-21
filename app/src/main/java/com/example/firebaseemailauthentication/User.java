package com.example.firebaseemailauthentication;

public class User {

    String Name, Email,Phone_No,Age,Blood_Group,Locality,District,State;

    public User(){}

    public User(String name, String email, String phone_No,String age,String blood_Group,String locality,String district,String state) {
        Name = name;
        Email = email;
        Phone_No = phone_No;
        Age = age;
        Blood_Group = blood_Group;
        Locality = locality;
        District = district;
        State = state;
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

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBlood_Group() {
        return Blood_Group;
    }

    public void setBlood_Group(String blood_Group) {
        Blood_Group = blood_Group;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
