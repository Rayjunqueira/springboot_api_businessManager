package com.api.business_manager_api.Services;

import com.api.business_manager_api.Models.UserModel;
import com.api.business_manager_api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <UserModel> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            UserModel userModel = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(userModel.getUsername(), userModel.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
