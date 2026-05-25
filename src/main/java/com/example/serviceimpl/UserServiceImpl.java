package com.example.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    // Register User

    @Override
    public User registerUser(User user) {

        return userRepository.save(user);
    }


    // Get All Users

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }


    // Get User By Id

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));
    }


    // Update User

  @Override
    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setPassword(updatedUser.getPassword());
      //  existingUser.setConfirmPassword(updatedUser.getConfirmPassword());
        existingUser.setGender(updatedUser.getGender());
     

        return userRepository.save(existingUser);
    }


    // Delete User

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        userRepository.delete(user);
    }
    
    @Override
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        if (!user.getPassword().equals(password)) {

            throw new RuntimeException("Invalid Password");
        }

        return user;
    }

}