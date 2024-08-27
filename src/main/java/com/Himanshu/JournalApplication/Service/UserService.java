package com.Himanshu.JournalApplication.Service;

 import com.Himanshu.JournalApplication.Entity.User;
 import com.Himanshu.JournalApplication.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public List<User> getAll(){
        return new ArrayList<>(userRepository.findAll());
    }

    public User saveEntry(User newEntry){
        userRepository.save(newEntry);
        return newEntry;
    }

    public User createUser(User userToCreate){
        return userRepository.save(userToCreate);
    }

    public User findByUserName(String userName){
         return userRepository.findByUserName(userName);
    }


 }
