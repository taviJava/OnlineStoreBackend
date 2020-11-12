package com.project.demo.service;


import com.project.demo.persitance.dto.CategoryDto;
import com.project.demo.persitance.dto.ManufacturerDto;
import com.project.demo.persitance.dto.ProductDto;
import com.project.demo.persitance.dto.ReviewDto;
import com.project.demo.persitance.model.*;
import com.project.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ProductDto> getProducts(){
        List<ProductModel> productModels = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductModel productModel: productModels){
            ProductDto productDto = new ProductDto();
            productDto.setId(productModel.getId());
            productDto.setPrice(productModel.getPrice());
            productDto.setName(productModel.getName());
            if (productModel.getPhotos()!= null){
                productDto.setIdPhoto(productModel.getPhotos().getId());
            }
            if (productModel.getReviewModelList().size()> 0){
                for (ReviewModel reviewModel: productModel.getReviewModelList()){
                    ReviewDto reviewDto = new ReviewDto();
                    reviewDto.setId(reviewModel.getId());
                    reviewDto.setComment(reviewModel.getComment());
                    reviewDto.setRating(reviewModel.getRating());
                    reviewDto.setEmail(reviewModel.getEmail());
                    reviewDto.setFullname(reviewModel.getFullname());
                    reviewDto.setDate(reviewModel.getDate());
                    productDto.getReviewList().add(reviewDto);
                    productDto.setReviewAverage(productModel.getRatingAverage());
                }
            }
            productDto.setDescription(productModel.getDescription());
            CategoryDto categoryDto =new CategoryDto();
            categoryDto.setId(productModel.getCategory().getId());
            categoryDto.setName(productModel.getCategory().getName());
            productDto.setCategory(categoryDto);
            ManufacturerDto manufacturerDto = new ManufacturerDto();
            manufacturerDto.setId(productModel.getManufacturer().getId());
            manufacturerDto.setName(productModel.getManufacturer().getName());
            productDto.setManufacturer(manufacturerDto);
            productDto.setProductType(productModel.getProductType().name());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
 public List<ProductDto> getProductsByCategory(long id){
     List<ProductModel> productModels = productRepository.findByCategory_Id(id);
     List<ProductDto> productDtoList = new ArrayList<>();
     for (ProductModel productModel: productModels){
         ProductDto productDto = new ProductDto();
         productDto.setId(productModel.getId());
         productDto.setPrice(productModel.getPrice());
         productDto.setName(productModel.getName());
         productDto.setDescription(productModel.getDescription());
         CategoryDto categoryDto =new CategoryDto();
         categoryDto.setId(productModel.getCategory().getId());
         categoryDto.setName(productModel.getCategory().getName());
         productDto.setCategory(categoryDto);
         ManufacturerDto manufacturerDto = new ManufacturerDto();
         manufacturerDto.setId(productModel.getManufacturer().getId());
         manufacturerDto.setName(productModel.getManufacturer().getName());
         productDto.setManufacturer(manufacturerDto);
         productDto.setProductType(productModel.getProductType().name());
         if (productModel.getReviewModelList().size()> 0){
             for (ReviewModel reviewModel: productModel.getReviewModelList()){
                 ReviewDto reviewDto = new ReviewDto();
                 reviewDto.setId(reviewModel.getId());
                 reviewDto.setComment(reviewModel.getComment());
                 reviewDto.setRating(reviewModel.getRating());
                 reviewDto.setEmail(reviewModel.getEmail());
                 reviewDto.setFullname(reviewModel.getFullname());
                 reviewDto.setDate(reviewModel.getDate());
                 productDto.getReviewList().add(reviewDto);
                 productDto.setReviewAverage(productModel.getRatingAverage());
             }
         }
         productDtoList.add(productDto);
     }
     return productDtoList;
 }

    public void addProduct(ProductDto productDto){
        ProductModel productModel = new ProductModel();
        productModel.setId(productDto.getId());
        productModel.setName(productDto.getName());
        productModel.setPrice(productDto.getPrice());
        productModel.setDescription(productDto.getDescription());
        productModel.setProductType(ProductType.valueOf(productDto.getProductType()));
        CategoryDto categoryDto = productDto.getCategory();
        Optional<CategoryModel> opCategoryModel = categoryRepository.findById(categoryDto.getId());
        if (opCategoryModel.isPresent()){
            CategoryModel categoryModel = opCategoryModel.get();
            productModel.setCategory(categoryModel);
        }
        Optional<ManufacturerModel> manufacturerModelOptional = manufacturerRepository.findById(productDto.getManufacturer().getId());
        if (manufacturerModelOptional.isPresent()){
            ManufacturerModel manufacturerModel = manufacturerModelOptional.get();
            productModel.setManufacturer(manufacturerModel);
        }
        productRepository.save(productModel);

    }

    public ProductDto getProduct (long id){
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);
        ProductDto productDto = new ProductDto();
        if (optionalProductModel.isPresent()) {
            ProductModel productModel = optionalProductModel.get();
            productDto.setId(productModel.getId());
            productDto.setPrice(productModel.getPrice());
            productDto.setName(productModel.getName());
            productDto.setProductType(productModel.getProductType().name());
            if (productModel.getPhotos() != null){
                productDto.setIdPhoto(productModel.getPhotos().getId());
            }
            if (productModel.getReviewModelList().size()> 0){
                for (ReviewModel reviewModel: productModel.getReviewModelList()){
                    ReviewDto reviewDto = new ReviewDto();
                    reviewDto.setId(reviewModel.getId());
                    reviewDto.setComment(reviewModel.getComment());
                    reviewDto.setRating(reviewModel.getRating());
                    reviewDto.setEmail(reviewModel.getEmail());
                    reviewDto.setFullname(reviewModel.getFullname());
                    productDto.getReviewList().add(reviewDto);
                    productDto.setReviewAverage(productModel.getRatingAverage());
                }
            }
            productDto.setDescription(productModel.getDescription());
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(productModel.getCategory().getId());
            categoryDto.setName(productModel.getCategory().getName());
            productDto.setCategory(categoryDto);
            ManufacturerDto manufacturerDto = new ManufacturerDto();
            manufacturerDto.setId(productModel.getManufacturer().getId());
            manufacturerDto.setName(productModel.getManufacturer().getName());
            productDto.setManufacturer(manufacturerDto);

        }
        return productDto;
    }
public void update (ProductDto productDto){
        Optional<ProductModel> optionalProductModel = productRepository.findById(productDto.getId());
        if (optionalProductModel.isPresent()) {
            ProductModel productModel = optionalProductModel.get();
            productModel.setId(productDto.getId());
            productModel.setName(productDto.getName());
            productModel.setPrice(productDto.getPrice());
            productModel.setDescription(productDto.getDescription());
            productModel.setProductType(ProductType.valueOf(productDto.getProductType()));
            CategoryDto categoryDto = productDto.getCategory();
            Optional<CategoryModel> opCategoryModel = categoryRepository.findById(categoryDto.getId());
            if (opCategoryModel.isPresent()){
                CategoryModel categoryModel = opCategoryModel.get();
                productModel.setCategory(categoryModel);
            }
            Optional<ManufacturerModel> manufacturerModelOptional = manufacturerRepository.findById(productDto.getManufacturer().getId());
            if (manufacturerModelOptional.isPresent()){
                ManufacturerModel manufacturerModel = manufacturerModelOptional.get();
                productModel.setManufacturer(manufacturerModel);
            }

            productRepository.save(productModel);
        }
}

public void delete(long id){
    ProductModel productModel = productRepository.findById(id).orElse(null);
        productRepository.delete(productModel);
}

public void addReview(ReviewDto reviewDto, long id){
        Optional<ProductModel> productModelOptional = productRepository.findById(id);
        if (productModelOptional.isPresent()){
            ProductModel productModel = productModelOptional.get();
            ReviewModel reviewModel = new ReviewModel();
            reviewModel.setId(reviewDto.getId()); reviewModel.setComment(reviewDto.getComment());
            reviewModel.setRating(reviewDto.getRating()); reviewModel.setEmail(reviewDto.getEmail());
            reviewModel.setFullname(reviewDto.getFullname());
            reviewModel.setDate(Date.valueOf(LocalDate.now()));
            reviewModel.setProduct(productModel);
            Optional<UserModel> userModelOptional = userRepository.findUserModelByEmail(reviewDto.getEmail());
            if (userModelOptional.isPresent()){
                UserModel userModel = userModelOptional.get();
                userModel.getReviewModelList().add(reviewModel);
                reviewModel.setUser(userModel);
            }
            reviewRepository.save(reviewModel);
            calculateAverageRating(productModel);
            productRepository.save(productModel); // l-am salvat pt a avea media actualizata
        }
}
private void calculateAverageRating(ProductModel productModel){
        if (productModel.getReviewModelList().size()>0){
            int sum = 0;
            int number = productModel.getReviewModelList().size();
            for (ReviewModel reviewModel: productModel.getReviewModelList()){
                sum = sum + reviewModel.getRating();
            }
            double average = sum/ number;
            productModel.setRatingAverage( Math.round(average*10.0)/10.0);

        }
}

}
