package com.example.aps;

public class Post {

    private String id;
    private String title;
    private String description;
    private String local;
    private String image;

    public Post(String title, String description, String local, String image) {
        this.title = title;
        this.description = description;
        this.local = local;
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getLocal() {
        return local;
    }
    public String getImage() {
        return image;
    }
    public String getId() {
        return id;
    }
}
