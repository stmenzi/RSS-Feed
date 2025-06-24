package com.example.weatherapp;

public class DataModel {
    private final String title;
    private final String description;
    private final String pubDate;

    public DataModel(String title, String description, String pubDate) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getImageUrl() {
        String imageUrl = "";
        String pattern = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        String[] descriptionParts = description.split(pattern);
        if (descriptionParts.length > 1) {
            imageUrl = descriptionParts[1];
        }
        return imageUrl;
    }
}



