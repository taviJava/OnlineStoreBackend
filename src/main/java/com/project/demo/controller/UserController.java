package com.project.demo.controller;


import com.project.demo.common.util.AuthenticationBean;
import com.project.demo.persitance.dto.UserDto;
import com.project.demo.persitance.dto.files.ResponseFile;
import com.project.demo.persitance.dto.files.ResponseMessage;
import com.project.demo.persitance.model.PhotoU;
import com.project.demo.persitance.model.UserModel;
import com.project.demo.repository.UserRepository;
import com.project.demo.service.PhotoUService;
import com.project.demo.service.SendEmailService;
import com.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoUService photoUService;

    @Autowired
    private SendEmailService sendEmailService;


    @PostMapping("/user")
    public void addUser(@RequestBody UserDto userDto) {
        userService.save(userDto);

    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/user")
    public List<UserDto> getUser() {
        return userService.findALl();
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable(name = "id") Long id) {
        return userService.findById(id);
    }

    @PutMapping("/user")
    public void update(@RequestBody UserDto userDto) {
        userService.update(userDto);
    }

    @PostMapping("/photos")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("photo") MultipartFile photo) {
        String message;
        try {
            photoUService.store(photo);
            message = "Uploaded the file successfully: " + photo.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + photo.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @PutMapping("/photos/{id}")
    public ResponseEntity<ResponseMessage> uploadFileUpdate(@PathVariable(name = "id") long id,@RequestParam("photo") MultipartFile photo) {
        String message;
        try {
            photoUService.update(photo, id);
            message = "Uploaded the file successfully: " + photo.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + photo.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    @GetMapping("/photo/{id}")
    public ResponseEntity<List<ResponseFile>> getUserPhoto(@PathVariable(name = "id") Long id){
        System.out.println("test al treilea");
        List<ResponseFile> files = new ArrayList<>();
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        if (userModelOptional.isPresent()){
            if (userModelOptional.get().getPhotos()!=null){
                files = photoUService.getUserPhoto(id).map(dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/photos/")
                            .path(dbFile.getId())
                            .toUriString();
                    return new ResponseFile(
                            dbFile.getName(),
                            fileDownloadUri,
                            dbFile.getType(),
                            dbFile.getData().length);
                }).collect(Collectors.toList());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        PhotoU photoU = photoUService.getPhoto(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photoU.getName() + "\"")
                .body(photoU.getData());
    }

    @GetMapping(path = "/basicauth")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }
    @GetMapping("/user/getbyusername/{username}")
    public UserDto getUserByUsername(@PathVariable(name = "username") String userName){
        return userService.findByUsername(userName);
    }



    @DeleteMapping("/user/{photoId}/photo")
    public void deletePhoto(@PathVariable(name = "photoId") String photoId){
        photoUService.delete(photoId);
    }

    @PostMapping("/forgot-password/{email}")
    public String forgotPassword(@PathVariable(name = "email") String email) {

        String response = userService.forgotPassword(email);

        return response;
    }

    @PutMapping("/reset-password/{password}/{token}")
    public void resetPassword(@PathVariable(name = "token") String token,
                                @PathVariable(name = "password") String password) {

        userService.resetPassword(token, password);
    }

}
