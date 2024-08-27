package com.Himanshu.JournalApplication.Controllers;


import com.Himanshu.JournalApplication.Entity.JournalEntry;
import com.Himanshu.JournalApplication.Entity.User;
import com.Himanshu.JournalApplication.Service.JournalService;
import com.Himanshu.JournalApplication.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryController {


    @Autowired
    private UserService service;


    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = service.getAll();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User userToCreate){
          return new ResponseEntity<>(service.createUser(userToCreate),HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User userToUpdate , @PathVariable String userName){
        User userInDb = service.findByUserName(userName);
        if (userInDb != null){
            userInDb.setUserName(userToUpdate.getUserName());
            userInDb.setPassword(userToUpdate.getPassword());
            service.saveEntry(userInDb);
         }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
