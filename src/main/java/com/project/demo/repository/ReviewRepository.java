package com.project.demo.repository;

import com.project.demo.persitance.model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel,Long> {
    List<ReviewModel> findByProduct_Id(long id);
}
