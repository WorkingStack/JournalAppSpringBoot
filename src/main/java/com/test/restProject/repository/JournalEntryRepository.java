package com.test.restProject.repository;

import com.test.restProject.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

// we have to pass generic datatype as first one is class that from where data is exchanging and second is
// id datatype
// Why MongoRepository? cause, the reason is this repository provide predefined methods us and we can use
// that methods and exchange data from it

public interface JournalEntryRepository extends MongoRepository<JournalEntry, Long> {

}

// controller -------> service ---------> repository
