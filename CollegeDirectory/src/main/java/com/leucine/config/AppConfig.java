package com.leucine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig {
	
	@Bean
    public JwtTokenGeneratorFilter jwtTokenGeneratorFilter() {
        return new JwtTokenGeneratorFilter();
    }


    @Bean
    public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

        http
            .cors().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/users/hello").permitAll() 
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Allow user registration
            .requestMatchers("/api/admin/**").hasAuthority("ADMINISTRATOR")
            .requestMatchers("/api/departments/**").hasAuthority("ADMINISTRATOR")
            .requestMatchers("/api/faculty/**").hasAuthority("ADMINISTRATOR")
            .requestMatchers("/api/students/**").hasAuthority("STUDENT")
            
            .requestMatchers(HttpMethod.GET, "/api/courses").hasAnyAuthority("STUDENT","FACULTY_MEMBER", "ADMINISTRATOR") // All can view courses
            .requestMatchers(HttpMethod.GET, "/api/users/{email}").hasAnyAuthority("STUDENT","FACULTY_MEMBER", "ADMINISTRATOR") // All can view courses
            .requestMatchers(HttpMethod.POST, "/api/courses/**").hasAuthority("ADMINISTRATOR") // Only administrators can create courses
            .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasAuthority("ADMINISTRATOR") // Only administrators can update courses
            .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasAuthority("ADMINISTRATOR") // Only administrators can delete courses
            .requestMatchers(HttpMethod.GET, "/api/faculty/**").hasAuthority("FACULTY_MEMBER")
            .requestMatchers(HttpMethod.GET,"/api/student/profile/{userId}").hasAuthority("STUDENT")
            .requestMatchers(HttpMethod.PUT,"/api/student/profile").hasAuthority("STUDENT")
            .anyRequest().authenticated()
            .and()
            .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new JwtTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
            
            .formLogin()
            .and()
            .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}