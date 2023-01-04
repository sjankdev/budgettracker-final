package com.budgettracker.demo.security.token.services.impl;

import com.budgettracker.demo.security.models.User;
import com.budgettracker.demo.security.repository.UserRepository;
import com.budgettracker.demo.security.token.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
