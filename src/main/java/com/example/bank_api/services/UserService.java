package com.example.bank_api.services;

import com.example.bank_api.entities.User;
import com.example.bank_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id){
        return userRepository.findUserById(id).get();
    }

    public User create(double balance) {
        return userRepository.save(new User(balance));

    }

    public int delete(Long id) {
        try {
            userRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            e.getMessage();
            return 0;
        }
    }
}
