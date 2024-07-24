package com.leucine.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leucine.models.Users;
import com.leucine.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String testHandler() {
        return "Welcome to College Directory";
    }

    @PostMapping
    public ResponseEntity<Users> saveCustomerHandler(@RequestBody Users user) {

        //user.setRole(Users.Role.valueOf("ROLE_" + user.getRole().name()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users registeredUser = userService.createUser(user);

        return new ResponseEntity<>(registeredUser, HttpStatus.ACCEPTED);
    }	

    @GetMapping("/{email}")
    public ResponseEntity<Users> getCustomerByEmailHandler(@PathVariable("email") String email) {

        Users user = userService.getCustomerDetailsByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllCustomerHandler() {

        List<Users> users = userService.getAllCustomerDetails();

        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }
}
