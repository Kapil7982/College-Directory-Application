package com.leucine.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.leucine.config.JwtTokenGeneratorFilter;
import com.leucine.models.Users;
import com.leucine.repositories.UserRepository;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    
    @Autowired
	private UserRepository userRepository;
   
    @Autowired
    private JwtTokenGeneratorFilter jwtTokenGeneratorFilter;
	
    @GetMapping("/signIn")
	public ResponseEntity<?> getLoggedInCustomerDetailsHandler(Authentication auth){
		
    	if (auth == null) {
            throw new BadCredentialsException("User is not authenticated");
        }
		
    	Users customer= userRepository.findByEmail(auth.getName()).orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));
		
    	String token = jwtTokenGeneratorFilter.generateToken(auth);

        Map<String, Object> response = new HashMap<>();
        response.put("user", customer);
        response.put("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
		
		
	}
	
    

    
}
