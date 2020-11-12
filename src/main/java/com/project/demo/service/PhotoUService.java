package com.project.demo.service;

import com.project.demo.persitance.model.PhotoU;
import com.project.demo.persitance.model.ProductModel;
import com.project.demo.persitance.model.UserModel;
import com.project.demo.repository.PhotoURepository;
import com.project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class PhotoUService {
    @Autowired
    private PhotoURepository photoURepository;
    @Autowired
    private UserRepository userRepository;

    public PhotoU store(MultipartFile file) throws IOException, InterruptedException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        PhotoU photoU = new PhotoU(fileName, file.getContentType(), file.getBytes());
        TimeUnit.SECONDS.sleep(3);
        List<UserModel> users = userRepository.findAll();
        UserModel userModel = users.get(users.size()-1);
        photoU.setUser(userModel);
        return photoURepository.save(photoU);
    }
    public PhotoU update(MultipartFile file, long userId) throws IOException, InterruptedException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        PhotoU photoU = new PhotoU(fileName, file.getContentType(), file.getBytes());
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        if (userModelOptional.isPresent()){
            UserModel userModel = userModelOptional.get();
            photoU.setUser(userModel);
        }

        return photoURepository.save(photoU);
    }
    public PhotoU getPhoto(String id) {
        Optional<PhotoU> photoUOptional = photoURepository.findById(id);
        if (photoUOptional.isPresent()){
            return photoUOptional.get();
        }
        return new PhotoU();
    }

    public Stream<PhotoU> getAllphotos() {
        return photoURepository.findAll().stream();
    }

    public Stream<PhotoU> getUserPhoto(long id) {
        UserModel userModel = userRepository.findById(id).orElse(null);
        List<PhotoU> list = new ArrayList<>();
        list.add(userModel.getPhotos());
        return list.stream();
    }
    public void delete(String photo){
        Optional<PhotoU> photoUOptional = photoURepository.findById(photo);
        if (photoUOptional.isPresent()){
            photoURepository.deleteById(photo);
        }

    }

}
