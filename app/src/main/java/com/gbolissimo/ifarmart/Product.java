package com.gbolissimo.ifarmart;


import com.google.firebase.firestore.Exclude;

import java.util.List;


public class Product {
    private String id;
    private String productname;
    private String quantity;
    private String farmaddress;
    private String price;
    private String contactno1;
    private String contactno2;
    private String seller;
    private String selleremail;
    private String verification;
    private String businessname;
    private String others1;
    private String others2;
    private String others3;
    private String others4;
    private String productpix;
    private String ttimestamp;


    private String documentId;
    public Product() {
        //empty constructor needed
    }

    public Product(String id, String productname, String quantity, String farmaddress, String price, String contactno1, String contactno2, String seller, String selleremail, String verification, String businessname, String others1, String others2, String others3, String others4, String productpix, String ttimestamp) {
        this.id = id;
        this.productname = productname;
        this.quantity = quantity;
        this.farmaddress = farmaddress;
        this.price = price;
        this.contactno1 = contactno1;
        this.contactno2 = contactno2;
        this.seller = seller;
        this.selleremail = selleremail;
        this.verification = verification;
        this.businessname = businessname;
        this.others1 = others1;
        this.others2 = others2;
        this.others3 = others3;
        this.others4 = others4;
        this.productpix = productpix;
        this.ttimestamp = ttimestamp;


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

    public String getProductname() {
        return productname;
    }


    public String getQuantity() {
        return quantity;
    }

    public String getFarmaddress() {
        return farmaddress;
    }

    public String getPrice() {
        return price;
    }
    public String getContactno1() {
        return contactno1;
    }

    public String getContactno2() {
        return contactno2;
    }
    public String getSeller() {
        return seller;
    }
    public String getSelleremail() {
        return selleremail;
    }
    public String getVerification() {
        return verification;
    }
    public String getBusinessname() {
        return businessname;
    }
    public String getOthers1() {
        return others1;
    }
    public String getOthers2() {
        return others2;
    }
    public String getOthers3() {
        return others3;
    }
    public String getOthers4() {
        return others4;
    }
    public String getProductpix() {
        return productpix;
    }
    public String getTtimestamp() {
        return ttimestamp;
    }


}
