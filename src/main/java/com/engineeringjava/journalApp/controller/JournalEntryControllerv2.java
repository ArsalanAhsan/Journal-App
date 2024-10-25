package com.engineeringjava.journalApp.controller;

import com.engineeringjava.journalApp.entity.JournalEntry;
import com.engineeringjava.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();

    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;

    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.findbyid(myId).orElse(null);
    }
    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId){
        journalEntryService.deletebyid(myId);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry){
        JournalEntry old = journalEntryService.findbyid(id).orElse(null);
        if(old!=null){
            old.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("")? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("")? myEntry.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }



}
