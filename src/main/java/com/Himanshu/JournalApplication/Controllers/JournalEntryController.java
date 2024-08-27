package com.Himanshu.JournalApplication.Controllers;


import com.Himanshu.JournalApplication.Entity.JournalEntry;
import com.Himanshu.JournalApplication.Entity.User;
import com.Himanshu.JournalApplication.Service.JournalService;
import com.Himanshu.JournalApplication.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.Joinable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalService service;

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfAUser(@PathVariable String userName){
        User userFromDb = userService.findByUserName(userName);
        List<JournalEntry> allJournals = userFromDb.getMyJournalEntries();
        if (allJournals != null && !allJournals.isEmpty()){
            return new ResponseEntity<>(allJournals,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create/{userName}")
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry newEntry , @PathVariable String userName){

        try {
            service.saveEntry(newEntry,userName);
            return new ResponseEntity<>(newEntry,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> findJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntry>  toFindJournal = service.findJournalById(myId);
        if(toFindJournal.isPresent()){
            return new ResponseEntity<>(toFindJournal.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id , @RequestBody JournalEntry updatedEntry , @PathVariable String userName){
        JournalEntry oldEntry =  service.findJournalById(id).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldEntry.getContent());
            service.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{toDeleteId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId toDeleteId , @PathVariable String userName){
       try {
           service.findByIdAndDelete(toDeleteId,userName);
           return new ResponseEntity<>(HttpStatus.OK);
       }
       catch (Exception e){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
    }




}
