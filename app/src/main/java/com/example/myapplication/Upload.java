package com.example.myapplication;

public class Upload {

     private String projectname;
     private String message;
     private String price;
     private String imageUri;
    private   String phoneno;

     public Upload()
     {

     }

     public Upload(String projectname, String message, String price, String imageUri, String phoneno)
     {
         this.projectname =projectname;
         this.message =message;
         this.price =price;
         this.imageUri=imageUri;
         this.phoneno=phoneno;
     }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

}

