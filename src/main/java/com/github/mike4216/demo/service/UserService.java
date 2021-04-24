package com.github.mike4216.demo.service;

import com.github.mike4216.demo.entity.User;
import com.github.mike4216.demo.entity.enums.ERole;
import com.github.mike4216.demo.exceptions.UserExistException;
import com.github.mike4216.demo.payload.request.SignUpRequest;
import com.github.mike4216.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(SignUpRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(this.passwordEncoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER);

        try{
            LOG.info("saving user {}", user.getEmail());
            return userRepository.save(user);
        } catch (Exception ex){
            LOG.error("Error during registration. {}", ex.getMessage());
            throw new UserExistException("the user " + user.getUsername() + "already exists. Please check credentials");
        }
    }


}
