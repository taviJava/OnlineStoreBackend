package com.project.demo.persitance.dto;


import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    private long id;
    private String name;
    private String description;
    private double price;
    private String idPhoto;
    private OrderLineDto orderline;
    private String productType;
    private CategoryDto subcategory;
    private ManufacturerDto manufacturer;
    private List<ReviewDto> reviewList = new ArrayList<>();
    private double reviewAverage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderLineDto getOrderline() {
        return orderline;
    }

    public void setOrderline(OrderLineDto orderline) {
        this.orderline = orderline;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public CategoryDto getCategory() {
        return subcategory;
    }

    public void setCategory(CategoryDto subcategory) {
        this.subcategory = subcategory;
    }

    public ManufacturerDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public CategoryDto getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(CategoryDto subcategory) {
        this.subcategory = subcategory;
    }

    public List<ReviewDto> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewDto> reviewList) {
        this.reviewList = reviewList;
    }

    public double getReviewAverage() {
        return reviewAverage;
    }

    public void setReviewAverage(double reviewAverage) {
        this.reviewAverage = reviewAverage;
    }
}
