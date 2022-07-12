package com.gbolissimo.ifarmart;


import com.google.firebase.firestore.Exclude;

import java.util.List;


public class User {
    private String id;
    private String pix;
    private String name;
    private String email;
    private String password;
    private String number;
    private String number2;
    private String branch;
    private String activationcode;
    private String activationstatus;
    private String expirydate;
    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;

    private String documentId;
    public User() {
        //empty constructor needed
    }

    public User(String id, String pix, String name, String email, String password, String number, String number2, String branch, String activationcode, String activationstatus, String expirydate, String extra1, String extra2, String extra3, String extra4 ) {
        this.id = id;
        this.pix = pix;
        this.name = name;
        this.email = email;
        this.password = password;
        this.number = number;
        this.number2 = number2;
        this.branch = branch;
        this.activationcode = activationcode;
        this.activationstatus = activationstatus;
        this.expirydate = expirydate;
        this.extra1 = extra1;
        this.extra2 = extra2;
        this.extra3 = extra3;
        this.extra4 = extra4;


    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


    public String getId() {
        return id;
    }

    public String getPix() {
        return pix;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getNumber() {
        return number;
    }

    public String getNumber2() {
        return number2;
    }
    public String getBranch() {
        return branch;
    }
    public String getActivationcode() {
        return activationcode;
    }
    public String getActivationstatus() {
        return activationstatus;
    }
    public String getExpirydate() {
        return expirydate;
    }
    public String getExtra1() {
        return extra1;
    }
    public String getExtra2() {
        return extra2;
    }
    public String getExtra3() {
        return extra3;
    }
    public String getExtra4() {
        return extra4;
    }


}
