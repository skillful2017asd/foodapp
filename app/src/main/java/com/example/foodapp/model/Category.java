package com.example.foodapp.model;

public class Category {
    private int id;
    private String category;
    private String categoryThumb;

    private String categoryDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryThumb() {
        return categoryThumb;
    }

    public void setCategoryThumb(String categoryThumb) {
        this.categoryThumb = categoryThumb;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
