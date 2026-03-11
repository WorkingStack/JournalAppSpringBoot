package com.test.restProject.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "User_Entries")
@Data
@NoArgsConstructor // this for when I mapped with user then it was calling RequiredArgsConstructor cause data annotation contains it
public class User {
   @Id
   private ObjectId userId; // you can do anything, I have taken Object cause MongoDB test, ID
   @Indexed(unique = true) // it will not create automatic so you have to add property in properties file
   @NonNull
   private String userName;
   @NonNull
   private String userPassword;
   @DBRef // this annotation make sure that just Ids of Journal Entry map with user
   // if you want entire document map with it just remove @DBRef
   private List<JournalEntry> journalEntryList;

//   for spring security - we need authorization so roles matters
   private List<String> roles;

}
