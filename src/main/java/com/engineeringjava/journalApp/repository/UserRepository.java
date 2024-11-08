package com.engineeringjava.journalApp.repository;

import com.engineeringjava.journalApp.entity.JournalEntry;
import com.engineeringjava.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);

    void deleteByUsername(String name);
}
