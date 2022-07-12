package com.gbolissimo.ifarmart;


import com.google.firebase.firestore.Exclude;


public class Note {
    private String id;
    private String title;
    private String content;
    private String link;
    private String image;
    private String tym;

    private String documentId;
    public Note() {
        //empty constructor needed
    }

    public Note(String id, String title, String content, String link, String image, String tym ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
        this.image = image;
        this.tym = tym;


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

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }


    public String getImage() {
        return image;
    }

    public String getTym() {
        return tym;
    }


}
