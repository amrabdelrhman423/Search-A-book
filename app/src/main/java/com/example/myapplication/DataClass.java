package com.example.myapplication;

public class DataClass {
    String titel;
    String auther;
    String publicher;
    String description;
    String data;
    String imageUrl;
    String download;

    public DataClass(String titel, String auther, String publicher, String description, String data, String imageUrl,String download) {
        this.titel = titel;
        this.auther = auther;
        this.publicher = publicher;
        this.description = description;
        this.data = data;
        this.imageUrl = imageUrl;
        this.download=download;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getPublicher() {
        return publicher;
    }

    public void setPublicher(String publicher) {
        this.publicher = publicher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
}
