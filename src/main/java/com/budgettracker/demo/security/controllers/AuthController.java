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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

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
    public String login(@Valid @ModelAttribute("login") LoginRequest loginRequest, BindingResult result, HttpServletResponse response, Model model) {

        Optional<User> admin = userRepository.findByUsername(loginRequest.getUsername());


        boolean thereAreErrors = result.hasErrors();
        if (thereAreErrors) {
            model.addAttribute("login", loginRequest);
            return "login_form";
        }

        if (admin.isEmpty()) {
            admin = Optional.of(new User());
            result.rejectValue("username", "error.adminUserModel", "Username doesn't exist.");
        }
        if (!encoder.matches(loginRequest.getPassword(), admin.get().getPassword())) {
            result.rejectValue("password", "error.adminUserModel", "Wrong password, try again.");
        }

        if (result.hasErrors()) {
            return "login_form";
        } else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        }

        model.addAttribute("login", loginRequest);


        return "redirect:/api/test/homePage";

    }

    @PostMapping("/signup")
    @Transactional
    public String signup(@Valid @ModelAttribute("signup") SignupRequest signupRequest, BindingResult result, Model model) throws Exception {

        boolean thereAreErrors = result.hasErrors();
        if (thereAreErrors) {
            model.addAttribute("signup", signupRequest);
            return "register_form";
        }

        Optional<User> userUsername = userRepository.findByUsername(signupRequest.getUsername());
        Boolean userEmail = userRepository.existsByEmail(signupRequest.getEmail());

        if (userUsername.isPresent()) {
            userUsername = Optional.of(new User());
            result.rejectValue("username", "error.adminUserModel", "Username already exist.");
        }
        if (userEmail) {
            result.rejectValue("email", "error.adminUserModel", "Email already exist.");
        }
        if (result.hasErrors()) {
            return "register_form";
        }
        User user = new User(signupRequest.getUsername(), signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));

        model.addAttribute("signup", signupRequest);
        userRepository.save(user);

        return "redirect:/api/auth/loginForm";

    }

    @PostMapping("/signout")
    public String logoutUser(HttpServletResponse response) {
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return "redirect:/api/auth/loginForm";
    }

    @GetMapping("/registerForm")
    public String registerForm(Model model) {
        SignupRequest signupRequest = new SignupRequest();
        model.addAttribute("signup", signupRequest);
        return "register_form";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("login", loginRequest);
        return "login_form";
    }

}
