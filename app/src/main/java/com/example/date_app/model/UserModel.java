package com.example.date_app.model;

public class UserModel {
    private String number;
    private String name;
    private String city;
    private String gender;
    private String email;
    private String relationship;
    private String star;
    private String image;
    private String age;
    private String status;



    public UserModel(String number, String name, String city, String gender, String email, String relationship, String star, String image, String age, String status) {
        this.number = number;
        this.name = name;
        this.city = city;
        this.gender = gender;
        this.email = email;
        this.relationship = relationship;
        this.star = star;
        this.image = image;
        this.age = age;
        this.status = status;
    }
    public UserModel(){}
    public UserModel(String name,String string, String email, String city) {
        this.name = name;
        this.image = string;
        this.email = email;
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
