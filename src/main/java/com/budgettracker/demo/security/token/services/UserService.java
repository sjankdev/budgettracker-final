package com.budgettracker.demo.security.token.services;

import com.budgettracker.demo.security.models.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

}
