package com.engineeringjava.journalApp.service;

import com.engineeringjava.journalApp.entity.JournalEntry;
import com.engineeringjava.journalApp.entity.User;
import com.engineeringjava.journalApp.repository.JournalEntryRepository;
import com.engineeringjava.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
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
}
