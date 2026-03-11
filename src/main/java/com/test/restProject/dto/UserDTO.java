package com.test.restProject.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDTO {
   private String userName;
   private String userPassword;

   /*
      Notice :
         here are three things missing, (userId, JournalEntryList, roles)
         why?
            1. UserId - cause it automatically created by database
            2. JournalEntryList - this is also created/mapped by database
            3. roles - often assign internally
    */
}
