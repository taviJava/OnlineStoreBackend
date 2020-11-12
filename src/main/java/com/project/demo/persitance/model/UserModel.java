package com.project.demo.persitance.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.demo.common.util.Hasher;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    @JsonProperty
    private String newPassword;

    private String email;

    private String fullName;

    private String phone;

    private String token;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime tokenCreationDate;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private AdressModel adress;

    @Enumerated(EnumType.STRING)
    @JsonIgnoreProperties("user")
    private Role role;


    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnoreProperties("user")
    private PhotoU photos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<ReviewModel>reviewModelList =new ArrayList<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Hasher.encode(password);
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdressModel getAdress() {
        return adress;
    }

    public void setAdress(AdressModel adress) {
        this.adress = adress;
    }


    public PhotoU getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoU photos) {
        this.photos = photos;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTokenCreationDate() {
        return tokenCreationDate;
    }

    public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
        this.tokenCreationDate = tokenCreationDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<ReviewModel> getReviewModelList() {
        return reviewModelList;
    }

    public void setReviewModelList(List<ReviewModel> reviewModelList) {
        this.reviewModelList = reviewModelList;
    }
}
