package com.example.ticketservice.repository;

import com.example.ticketservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameAndPassword(String name, String password);
    Optional<User> findByName(String name);
}
