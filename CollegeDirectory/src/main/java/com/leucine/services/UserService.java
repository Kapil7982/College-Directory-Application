package com.leucine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leucine.exceptions.UserException;
import com.leucine.models.Users;
import com.leucine.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    
	public Users getCustomerDetailsByEmail(String email)throws UserException {
		
		return userRepository.findByEmail(email).orElseThrow(() -> new UserException("Customer Not found with Email: "+email));
	}

	
	public List<Users> getAllCustomerDetails()throws UserException {
		
		List<Users> customers= userRepository.findAll();
		
		if(customers.isEmpty())
			throw new UserException("No Customer find");
		
		return customers;
		
	}

}