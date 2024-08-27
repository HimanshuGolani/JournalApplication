package com.Himanshu.JournalApplication.Repository;
import com.Himanshu.JournalApplication.Entity.JournalEntry;
import com.Himanshu.JournalApplication.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
}
