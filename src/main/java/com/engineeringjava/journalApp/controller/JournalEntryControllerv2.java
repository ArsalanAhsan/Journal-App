package com.engineeringjava.journalApp.controller;

import com.engineeringjava.journalApp.entity.JournalEntry;
import com.engineeringjava.journalApp.entity.User;
import com.engineeringjava.journalApp.service.JournalEntryService;
import com.engineeringjava.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if(allEntries!=null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){
       try {
           journalEntryService.saveEntry(myEntry,userName);
           return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
       }
       catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findbyid(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName){
        journalEntryService.deletebyid(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id,
                                                               @RequestBody JournalEntry myEntry,
                                                               @PathVariable String userName){
        JournalEntry old = journalEntryService.findbyid(id).orElse(null);
        if(old!=null){
            old.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("")? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("")? myEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
