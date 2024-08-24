package com.Himanshu.JournalApplication.Service;

import com.Himanshu.JournalApplication.Entity.JournalEntry;
import com.Himanshu.JournalApplication.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {
    @Autowired
    private JournalRepository journalRepository;


    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalRepository.findAll());
    }

    public JournalEntry saveEntry(JournalEntry newEntry){
        newEntry.setDate(LocalDateTime.now());
        journalRepository.save(newEntry);
        return newEntry;
    }

    public Optional<JournalEntry> findJournalById(ObjectId objId){
        return journalRepository.findById(objId);
    }

    public Boolean findByIdAndDelete(ObjectId toDeleteId){
        try {
            journalRepository.deleteById(toDeleteId);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }

    }



 }
