package com.example.service;

import java.util.List;

import com.example.entity.User;

public interface UserService {
	public User registerUser(User user);

	public List<User> getAllUsers();

	public User getUserById(Long id);

	public User updateUser(Long id, User updatedUser);

	public void deleteUser(Long id);
	 public User loginUser(String email, String password);
}
