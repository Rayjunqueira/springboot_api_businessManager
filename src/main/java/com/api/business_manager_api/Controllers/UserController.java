package com.api.business_manager_api.Controllers;

import com.api.business_manager_api.Dtos.ProductDto;
import com.api.business_manager_api.Dtos.UserDto;
import com.api.business_manager_api.Mappers.UserMapper;
import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Models.UserModel;
import com.api.business_manager_api.Services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.mapstruct.control.MappingControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    final UserService userService;
    final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<Object> saveUser (@RequestBody @Valid UserDto userDto) {
        try {
            if (userService.existsByEmail(userDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists in database!");
            }
            UserModel userModel = userMapper.toUserModel(userDto);
            userModel.setPassword(passwordEncoder().encode(userModel.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create user. Check if the fields sent in your request are correct.");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers () {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        try {
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
            UserModel userModel = userModelOptional.get();
            userModel.setUsername(userDto.getUsername());
            userModel.setEmail(userDto.getEmail());
            userModel.setPassword(passwordEncoder().encode(userDto.getPassword()));

            return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot update user. Check if the fields sent in your request are correct.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        try {
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
            userService.delete(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted succesfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete user. Check if the fields sent in your request are correct.");
        }
    }

}
