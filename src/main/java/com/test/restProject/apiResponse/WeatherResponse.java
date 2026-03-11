package com.test.restProject.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {
   public Current current;
   @Getter
   @Setter
   public class Current{
      @JsonProperty("observation_time")
      public String observationTime;
      public int temperature;
      public int cloudcover;
      public int feelslike;
      @JsonProperty("weather_descriptions")
      public List<String> weatherDescriptions;
      @JsonProperty("is_day")
      public String isDay;
   }
}

// here I have used JsonProperty cause I have changed the names of fields and api fields and POJO class
// fields are different so to store data inside that variable or field we attached JsonProperty
// now we are free to use this class

