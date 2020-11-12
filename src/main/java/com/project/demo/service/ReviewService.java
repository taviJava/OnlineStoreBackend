package com.project.demo.service;

import com.project.demo.persitance.dto.ProductDto;
import com.project.demo.persitance.dto.ReviewDto;
import com.project.demo.persitance.dto.UserDto;
import com.project.demo.persitance.model.ProductModel;
import com.project.demo.persitance.model.ReviewModel;
import com.project.demo.persitance.model.Role;
import com.project.demo.persitance.model.UserModel;
import com.project.demo.repository.ProductRepository;
import com.project.demo.repository.ReviewRepository;
import com.project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
    public class ReviewService {
        @Autowired
        private ReviewRepository reviewRepository;
        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private UserRepository userRepository;
        public List<ReviewDto> getAll(long id) {
            List<ReviewModel> reviewModels = reviewRepository.findByProduct_Id(id);
            List<ReviewDto> reviewDtos = new ArrayList<>();
            for (ReviewModel reviewModel : reviewModels) {
                ReviewDto reviewDto1 = new ReviewDto();
                reviewDto1.setId(reviewModel.getId());
                reviewDto1.setComment(reviewModel.getComment());
                reviewDto1.setRating(reviewModel.getRating());
                reviewDto1.setEmail(reviewModel.getEmail());
                reviewDto1.setDate(reviewModel.getDate());
                reviewDto1.setFullname(reviewModel.getFullname());
                reviewDto1.setUserId(getUser(reviewModel.getEmail()).getId());
                UserDto userDto = new UserDto();
                Optional<UserModel> userModelOptional = userRepository.findById(reviewModel.getUser().getId());
                if (userModelOptional.isPresent()) {
                    UserModel userModel = userModelOptional.get();
                    userDto.setId(userModel.getId());
                    userDto.setEmail(userModel.getEmail());
                    userDto.setPassword(userModel.getPassword());
                    userDto.setRole(userModel.getRole().name());
                    userDto.setPhone(userModel.getPhone());
                    userDto.setFullName(userModel.getFullName());
                    if (userModel.getPhotos()!=null) {
                        userDto.setIdPhoto(userModel.getPhotos().getId());
                    }
                    reviewDto1.setUserDto(userDto);
                }
                reviewDtos.add(reviewDto1);
            }
            return reviewDtos;
        }
        private UserModel getUser(String email){
            Optional<UserModel> userModelOptional = userRepository.findUserModelByEmail(email);
            UserModel userModel = new UserModel();
            if (userModelOptional.isPresent()){
                userModel = userModelOptional.get();
            }
            return userModel;
        }
        public ReviewDto getReview(long id) {
            ReviewModel reviewModel = reviewRepository.findById(id).orElse(null);
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(reviewModel.getId());
            reviewDto.setComment(reviewModel.getComment());
            reviewDto.setRating(reviewModel.getRating());
            reviewModel.setProduct(reviewModel.getProduct());
            return reviewDto;
        }
        public void delete(long id) {
            reviewRepository.deleteById(id);
        }

    }

