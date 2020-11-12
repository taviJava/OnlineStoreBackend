package com.project.demo.persitance.dto;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReviewDto {
    private long id;
    private String comment;
    private int rating;
    private String email;
    private String fullname;
    private Date date;
    private List<Integer> ratingList = new ArrayList<>();
    private long userId;
    private UserDto userDto;
    private ProductDto product;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public ProductDto getProduct() {
        return product;
    }
    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public List<Integer> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Integer> ratingList) {
        this.ratingList = ratingList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
