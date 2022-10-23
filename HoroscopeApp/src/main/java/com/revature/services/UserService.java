package com.revature.services;

import com.revature.models.User;
import com.revature.repos.UserRepo;

import java.util.List;

public class UserService {
    private UserRepo userRepo;

    public UserService() {
        userRepo = new UserRepo();
    }

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public int createUser(User user) {
        return userRepo.create(user);
    }

    public List<User> getAllUsers() {
        return userRepo.getAll();
    }

    public User getUserById(int id) {
        return userRepo.getById(id);
    }

    public User updateUser(User user) {
        return userRepo.update(user);
    }

    public boolean deleteUser(User user) {
        return userRepo.delete(user);
    }

    public boolean deleteUserById(int id) {
        return userRepo.deleteById(id);
    }

    public User loginUser(User user) {
        return userRepo.loginUser(user);
    }
}
