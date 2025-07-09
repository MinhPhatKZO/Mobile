package com.example.projecttng.model;

public class User {
    // đại diện bảng ghi 1 user db
    private String name;
    private String email;
    private int avatarResId;

    public User(String name, String email, int avatarResId) {
        this.name = name;
        this.email = email;
        this.avatarResId = avatarResId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAvatarResId() {
        return avatarResId;
    }
}