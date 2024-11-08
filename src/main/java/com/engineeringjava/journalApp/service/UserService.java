package com.engineeringjava.journalApp.service;

import com.engineeringjava.journalApp.entity.JournalEntry;
import com.engineeringjava.journalApp.entity.User;
import com.engineeringjava.journalApp.repository.JournalEntryRepository;
import com.engineeringjava.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    public void saveEntry(User user){
        userRepository.save(user);
    }


    public void savenewuser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        userRepository.save(user);
    }


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findbyid(ObjectId id){
        return userRepository.findById(id);
    }

    public void deletebyid(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
