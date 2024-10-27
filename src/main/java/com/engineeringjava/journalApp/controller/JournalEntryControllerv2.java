package com.engineeringjava.journalApp.controller;

import com.engineeringjava.journalApp.entity.JournalEntry;
import com.engineeringjava.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> allEntries = journalEntryService.getAll();
        if(allEntries!=null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
       try {
           myEntry.setDate(LocalDateTime.now());
           journalEntryService.saveEntry(myEntry);
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
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        journalEntryService.deletebyid(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry){
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
