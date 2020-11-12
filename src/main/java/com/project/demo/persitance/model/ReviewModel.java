package com.project.demo.persitance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;


@Entity
    public class ReviewModel {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        private String comment;
        private int rating;
        private String email;
        private String fullname;
        private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("reviewModelList")
    private UserModel user;
        @ManyToOne(fetch = FetchType.EAGER)
        @JsonIgnoreProperties("reviewModelList")
        private ProductModel product;
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
        public ProductModel getProduct() {
            return product;
        }
        public void setProduct(ProductModel product) {
            this.product = product;
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}

