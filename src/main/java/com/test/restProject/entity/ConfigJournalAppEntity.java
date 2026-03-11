package com.test.restProject.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ConfigJournalApp")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {
   private String key;
   private String value;
}
