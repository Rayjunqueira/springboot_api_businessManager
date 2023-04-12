package com.api.business_manager_api.Services;

import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Models.UserModel;
import com.api.business_manager_api.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }
}
