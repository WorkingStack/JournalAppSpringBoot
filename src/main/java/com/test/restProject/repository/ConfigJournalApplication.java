package com.test.restProject.repository;

import com.test.restProject.entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalApplication extends MongoRepository<ConfigJournalAppEntity,ObjectId> {
}
