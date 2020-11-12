package com.project.demo.service;

import com.project.demo.persitance.dto.AddressDto;
import com.project.demo.persitance.dto.UserDto;
import com.project.demo.persitance.model.*;
import com.project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SendEmailService sendEmailService;
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    public void save(UserDto userDto) {
        UserModel userModel = new UserModel();
        AddressDto addressDto = userDto.getAdress();
        AdressModel addressModel = new AdressModel();
        if(addressDto != null){
            addressModel.setCountry(addressDto.getCountry());
            addressModel.setZipCode(addressDto.getZipCode());
            addressModel.setStreet(addressDto.getStreet());
            addressModel.setCity(addressDto.getCity());
        }
        userModel.setAdress(addressModel);
        userModel.setId(userDto.getId());
        userModel.setEmail(userDto.getEmail());
        userModel.setPhone(userDto.getPhone());
        userModel.setFullName(userDto.getFullName());
        userModel.setPassword(userDto.getPassword());
        userModel.setToken(userDto.getToken());
        userModel.setTokenCreationDate(userDto.getTokenCreationDate());
        List<UserModel> users = userRepository.findAll();
        if (users.size()== 0){
            userModel.setRole(Role.valueOf("Administrator"));
        }else{
            userModel.setRole(Role.valueOf("Standard"));
        }
        if (!ifEmailExist(userModel.getEmail())){
            userRepository.save(userModel);
        }

    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    public List<UserDto> findALl(){
        List<UserModel> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();
        for(UserModel userModel : users){
            UserDto userDto = new UserDto();
            userDto.setId(userModel.getId());
            userDto.setEmail(userModel.getEmail());
            userDto.setPassword(userModel.getPassword());
            if (userModel.getPhotos()!=null) {
                userDto.setIdPhoto(userModel.getPhotos().getId());
            }
            userDto.setPhone(userModel.getPhone());
            userDto.setRole(userModel.getRole().name());
            userDto.setFullName(userModel.getFullName());
            userDto.setToken(userModel.getToken());
            userDto.setTokenCreationDate(userModel.getTokenCreationDate());
            AdressModel addressModel = userModel.getAdress();
            AddressDto addressDto = new AddressDto();
            addressDto.setZipCode(addressModel.getZipCode());
            addressDto.setCountry(addressModel.getCountry());
            addressDto.setCity(addressModel.getCity());
            addressDto.setStreet(addressModel.getStreet());
            addressDto.setId(addressModel.getId());
            userDto.setAdress(addressDto);
            usersDto.add(userDto);
        }
        return usersDto;
    }
    public void update(UserDto userDto){
        UserModel userModel = convertUser(userDto);
        if (!ifEmailExist(userModel.getEmail())){
            userRepository.save(userModel);
        }
    }

    public UserModel convertUser(UserDto userDto){
        Optional<UserModel> newUser = userRepository.findById(userDto.getId());
        UserModel userModel = new UserModel();
        if(newUser.isPresent()) {
            userModel = newUser.get();
            userModel.setId(userDto.getId());
            userModel.setRole(Role.valueOf(userDto.getRole()));
            userModel.setEmail(userDto.getEmail());
            userModel.setPhone(userDto.getPhone());
            userModel.setPassword(userDto.getPassword());
            userModel.setFullName(userDto.getFullName());
            userModel.setToken(userDto.getToken());
            userModel.setTokenCreationDate(userDto.getTokenCreationDate());
            AdressModel adressModel = new AdressModel();
            AddressDto addressDto = userDto.getAdress();
            adressModel.setCity(addressDto.getCity());
            adressModel.setCountry(addressDto.getCountry());
            adressModel.setId(addressDto.getId());
            adressModel.setStreet(addressDto.getStreet());
            adressModel.setZipCode(addressDto.getZipCode());
            userModel.setAdress(adressModel);
        }
        return userModel;
    }
    public UserDto findById(Long id ){
        Optional<UserModel>userModel = userRepository.findById(id);
        UserDto userDto = new UserDto();
        if(userModel.isPresent()){
            userDto.setId(userModel.get().getId());
            userDto.setEmail(userModel.get().getEmail());
            userDto.setPassword(userModel.get().getPassword());
            userDto.setRole(userModel.get().getRole().name());
            userDto.setToken(userModel.get().getToken());
            userDto.setTokenCreationDate(userModel.get().getTokenCreationDate());
            userDto.setPhone(userModel.get().getPhone());
            userDto.setFullName(userModel.get().getFullName());
            if (userModel.get().getPhotos()!=null) {
                userDto.setIdPhoto(userModel.get().getPhotos().getId());
            }
            AdressModel addressModel = userModel.get().getAdress();
            AddressDto addressDto = new AddressDto();
            addressDto.setZipCode(addressModel.getZipCode());
            addressDto.setCountry(addressModel.getCountry());
            addressDto.setCity(addressModel.getCity());
            addressDto.setStreet(addressModel.getStreet());
            addressDto.setId(addressModel.getId());
            userDto.setAdress(addressDto);
        }
        return userDto;
    }

    public UserDto findByUsername(String username) {
        Optional<UserModel> userModelOptional = userRepository.getUserModelByEmail(username);
        UserDto userDto = new UserDto();
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            userDto.setId(userModel.getId());
            userDto.setEmail(userModel.getEmail());
            if (userModel.getPhotos() != null) {
                userDto.setIdPhoto(userModel.getPhotos().getId());
            }
            userDto.setPhone(userModel.getPhone());
            userDto.setFullName(userModel.getFullName());
            userDto.setPassword(userModel.getPassword());
            userDto.setRole(userModel.getRole().name());
            AdressModel addressModel = userModel.getAdress();
            AddressDto addressDto = new AddressDto();
            addressDto.setZipCode(addressModel.getZipCode());
            addressDto.setCountry(addressModel.getCountry());
            addressDto.setCity(addressModel.getCity());
            addressDto.setStreet(addressModel.getStreet());
            addressDto.setId(addressModel.getId());
            userDto.setAdress(addressDto);


        }
        return userDto;
    }
private boolean ifEmailExist(String email){
        List<UserModel> users = userRepository.findAll();
        for (UserModel user: users){
            if (email.equals(user.getEmail())){
                return true;
            }
        }
        return false;
}

    public String forgotPassword(String email) {

        Optional<UserModel> userOptional =userRepository.findUserModelByEmail(email);

        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        }

        UserModel user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());
        user = userRepository.save(user);
        sendEmailService.sendEmail(user.getEmail(),user.getId(),user.getToken(), user.getFullName());
        return user.getToken();
    }

    public String resetPassword(String token, String password) {

        Optional<UserModel> userOptional = userRepository.findByToken(token);

        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";

        }

        UserModel user = userOptional.get();

        user.setPassword(password);
        user.setToken(null);
        user.setTokenCreationDate(null);

        userRepository.save(user);

        return "Your password successfully updated.";
    }

    /**
     * Generate unique token. You may add multiple parameters to create a strong
     * token.
     *
     * @return unique token
     */
    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    /**
     * Check whether the created token expired or not.
     *
     * @param tokenCreationDate
     * @return true or false
     */
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}


