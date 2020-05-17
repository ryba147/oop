package com.example.ticketservice.service;

import com.example.ticketservice.model.entity.User;
import com.example.ticketservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByNameAndPassword(String name, String password) {
        return userRepository.findByNameAndPassword(name, password).orElse(null);
    }
    public User findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public void createAndSaveUser(String name, String password, User user) {
        user.setName(name);
        user.setPassword(password);
        user.setTicketList(new ArrayList<>());
        userRepository.save(user);
    }
}
