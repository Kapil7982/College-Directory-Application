package com.leucine.config;

import com.leucine.models.Users;
import com.leucine.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Optional<Users> opt= userRepository.findByEmail(username);

		if(opt.isPresent()) {
			
			Users user= opt.get();
			
			List<GrantedAuthority> authorities= new ArrayList<>();
			SimpleGrantedAuthority sga= new SimpleGrantedAuthority(user.getRole().name());
			authorities.add(sga);
			
			
			return new User(user.getEmail(), user.getPassword(), authorities);
			
			
			
			
		}else
			throw new BadCredentialsException("User Details not found with this username: "+username);
		
		
		
		
		
	}
}
