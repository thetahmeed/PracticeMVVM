package com.tahmeedul.practicemvvm.model;

import java.io.Serializable;

public class SignInModel implements Serializable {

    public String uid;
    public String name;
    public String email;
    public String imageUrl;
    public boolean isAuth;

    public SignInModel() {
    }

    public SignInModel(String id, String name, String email, String imageUrl) {
        this.uid = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
