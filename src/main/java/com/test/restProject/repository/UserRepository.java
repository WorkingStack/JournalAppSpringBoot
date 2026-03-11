package com.test.restProject.repository;

import com.test.restProject.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
   // here spring automatically generate method by this model find + By + fieldName and return data from DB
   User findByUserName(String userName); // this is also used in spring security
   void deleteByUserName(String userName);
}
