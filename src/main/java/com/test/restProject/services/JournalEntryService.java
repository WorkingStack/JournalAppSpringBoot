package com.test.restProject.services;

import com.test.restProject.entity.JournalEntry;
import com.test.restProject.entity.User;
import com.test.restProject.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
   // now here the JournalEntryRepository has no implementation but spring automatically add implementat-
   // ion of that interface into the object, we don't need to do separate implementation of interface
   @Autowired
   private JournalEntryRepository journalEntryRepository;
   @Autowired
   private UserService userService;

   @Transactional // this is only used in service cause actual business login present in service
   public void saveEntry(JournalEntry journalEntry, String username) {
      User byUsername = userService.findByUsername(username);
      JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry); // this pre-defined method by MongoRepository

//      why we have written we can directly write byUsername.getJournalEntryList().add(savedJournalEntry)
//      but the problem is exception is nullPointerException so to avoid we just created temporary arraylist
//      and set as list for current username then same getting list and add element in that list
      if (byUsername.getJournalEntryList() == null) {
         byUsername.setJournalEntryList(new ArrayList<>());
      }
      byUsername.getJournalEntryList().add(savedJournalEntry);
//      byUsername.setUserName(null); // this is null will give me exception and half part only perform
      // but because I have used transactional annotation so it will rollback entire transaction
      userService.saveEntry(byUsername);
   }


//   this is overridden method and this is only save journal entry
   public void saveEntry(JournalEntry journalEntry) {
      journalEntryRepository.save(journalEntry); // this pre-defined method by MongoRepository
   }

   public List<JournalEntry> getAllData() {
      return journalEntryRepository.findAll();
   }

   public Optional<JournalEntry> getDataById(Long id) {
      return journalEntryRepository.findById(id);
   }

   @Transactional // cause we are performing delete on two table and it made cascading either full or none
   public boolean deleteData(long id, String username) {
      boolean hasRemoved = false;
      try {
         User user = userService.findByUsername(username);
         hasRemoved = user.getJournalEntryList().removeIf(x -> x.getJournalId() == id);
         if(hasRemoved) {
            userService.saveEntry(user);
            journalEntryRepository.deleteById(id);
         }
      }
      catch (Exception e) {
         System.out.println("journal service while deleting : " + e.getStackTrace());
         throw new RuntimeException("an error occur in journal service while deleting ->" + e);
      }
      finally {
         return hasRemoved;
      }
   }
}

// controller -------> service ---------> repository
