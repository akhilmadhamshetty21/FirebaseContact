package com.example.inclass12;

import java.util.HashMap;

public class Contact {
    public String name,email,phone,imageUrl,documentID,imagepath;
    HashMap<String,Object> hmap;

    public Contact(String name, String email, String phone, String imageUrl, String imagepath) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.imagepath = imagepath;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public Contact() {
    }

    public Contact(String name, String email, String phone, String imageUrl) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    HashMap<String,Object> toHashMap(){
        hmap=new HashMap<>();
        hmap.put("name",name);
        hmap.put("email",email);
        hmap.put("phone",phone);
        hmap.put("imageUrl",imageUrl);
        hmap.put("imagepath",imagepath);
        return hmap;
    }
}
