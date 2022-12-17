package com.budgettracker.demo.security.controllers;


import com.budgettracker.demo.security.models.User;
import com.budgettracker.demo.security.payload.request.LoginRequest;
import com.budgettracker.demo.security.payload.request.SignupRequest;
import com.budgettracker.demo.security.payload.response.MessageResponse;
import com.budgettracker.demo.security.repository.RoleRepository;
import com.budgettracker.demo.security.repository.UserRepository;
import com.budgettracker.demo.security.token.jwt.JwtUtils;
import com.budgettracker.demo.security.token.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@Valid @ModelAttribute("login") LoginRequest loginRequest, Model model) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

        long userId = user.getId();
        String url = "/api/wallet/userWallet/balance/" + userId;

        model.addAttribute("login", loginRequest);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();

    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> signup(@Valid @ModelAttribute("signup") SignupRequest signupRequest, Model model) throws Exception {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        model.addAttribute("signup", signupRequest);
        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/api/auth/loginAndRegisterForm"))
                .build();
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/api/auth/loginAndRegisterForm"))
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @GetMapping("/loginAndRegisterForm")
    public String showLoginForm(Model model) {
        // create model object to store form data
        LoginRequest loginRequest = new LoginRequest();
        SignupRequest signupRequest = new SignupRequest();
        model.addAttribute("login", loginRequest);
        model.addAttribute("user", signupRequest);
        return "loginAndRegistration";
    }

}
