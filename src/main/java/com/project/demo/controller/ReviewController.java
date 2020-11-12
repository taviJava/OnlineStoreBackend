package com.project.demo.controller;

import com.project.demo.persitance.dto.ReviewDto;
import com.project.demo.service.ProductService;
import com.project.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;
    @PostMapping("/review/{id}")
    public void add(@RequestBody ReviewDto reviewDto, @PathVariable(name = "id") Long id) {
        productService.addReview(reviewDto,id);
    }
    @DeleteMapping("/review/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        reviewService.delete(id);
    }
    @GetMapping("/review/{id}")
    public List<ReviewDto> getAll(@PathVariable(name = "id") Long id) {
        return reviewService.getAll(id);
    }
//    @GetMapping("/review/{id}")
//    public ReviewDto get(@PathVariable(name = "id") Long id) {
//        return reviewService.getReview(id);
//    }
}
