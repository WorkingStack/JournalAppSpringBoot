package com.test.restProject.repository;

import com.test.restProject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {
   @Autowired
   private MongoTemplate mongoTemplate;

   public List<User> getAllUserInCustomizeWay() {
      Query query = new Query();
      Criteria criteria = new Criteria();
//      query.addCriteria(Criteria.where("userName").is("admin"));
//      query.addCriteria(Criteria.where("age").gte(18)); // meaning is age is greater than equal(gte)
//      to 18
//      query.addCriteria(Criteria.where("age").lte(17)); // meaning is age is less than equal(gte)
//      to 17 -----> this is customized way to write query through code

//      our main purpose to find those user who have email id and marked true for sentimentsAnalysis

      query.addCriteria(criteria.andOperator(
              Criteria.where("email").exists(true),
              Criteria.where("email").ne(null).ne(""),
              Criteria.where("email").regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"),
              Criteria.where("sentimentsAnalysis").is(true)
      ));


//      this result in list even though username is unique, the reason behind that is find method send data
//      into list format, it doesn't care that you have only 1 or 0 username inside database, it just sends
//      you the result in list format like this [User(admin)] or []
      List<User> users = mongoTemplate.find(query, User.class);
      return users;
   }
}
