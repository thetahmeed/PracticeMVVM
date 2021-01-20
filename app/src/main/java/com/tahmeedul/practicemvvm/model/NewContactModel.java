package com.tahmeedul.practicemvvm.model;

public class NewContactModel {

    public String newId, newName, newPhone, newEmail, newImage;

    public NewContactModel() {

    }

    public NewContactModel(String newId, String newName, String newPhone, String newEmail, String newImage) {
        this.newId = newId;
        this.newName = newName;
        this.newPhone = newPhone;
        this.newEmail = newEmail;
        this.newImage = newImage;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewImage() {
        return newImage;
    }

    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }
}
