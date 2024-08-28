package com.Himanshu.JournalApplication.Service;

import com.Himanshu.JournalApplication.Entity.JournalEntry;
import com.Himanshu.JournalApplication.Entity.User;
import com.Himanshu.JournalApplication.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {
    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;


    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalRepository.findAll());
    }


    public void saveEntry(JournalEntry newEntry){
        journalRepository.save(newEntry);
     }

     @Transactional
     public JournalEntry saveEntry(JournalEntry newEntry , String userName){
        User user = userService.findByUserName(userName);
        newEntry.setDate(LocalDateTime.now());
        JournalEntry savedJournalEntry = journalRepository.save(newEntry);
        user.getMyJournalEntries().add(savedJournalEntry);
        userService.saveEntry(user);
        return newEntry;
    }



    public Optional<JournalEntry> findJournalById(ObjectId objId){
        return journalRepository.findById(objId);
    }

    public void findByIdAndDelete(ObjectId toDeleteId , String userName){
        try {
            User user = userService.findByUserName(userName);
            user.getMyJournalEntries().removeIf(x -> x.getId().equals(toDeleteId));
            userService.saveEntry(user);
            journalRepository.deleteById(toDeleteId);
         }
        catch (Exception e){
            System.out.println(e);
         }

    }



 }
