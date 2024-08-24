package com.Himanshu.JournalApplication.Controllers;


import com.Himanshu.JournalApplication.Entity.JournalEntry;
import com.Himanshu.JournalApplication.Service.JournalService;
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

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> allJournals = service.getAll();
        if (allJournals != null && !allJournals.isEmpty()){
            return new ResponseEntity<>(allJournals,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry newEntry){

        try {
            service.saveEntry(newEntry);
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

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id , @RequestBody JournalEntry updatedEntry){
        JournalEntry oldEntry =  service.findJournalById(id).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldEntry.getContent());
            service.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{toDeleteId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId toDeleteId){
       try {
           service.findByIdAndDelete(toDeleteId);
           return new ResponseEntity<>(HttpStatus.OK);
       }
       catch (Exception e){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
    }




}
