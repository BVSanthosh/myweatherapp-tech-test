package com.weatherapp.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.zip.DataFormatException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CityInfo {

  @JsonProperty("address")
  String address;

  @JsonProperty("description")
  String description;

  @JsonProperty("currentConditions")
  CurrentConditions currentConditions;

  @JsonProperty("days")
  List<Days> days;

  public String getAddress(){
    return address;
  }

  public String getSunrise() {
    return currentConditions.sunrise;
  }

  public String getSunset() {
    return currentConditions.sunset;
  }

  public String getConditions() {
    return currentConditions.conditions;
  }

  public long getDaylightDuration() {
    try {
      String sunrise = getSunrise();
      String sunset = getSunset();

      if (sunrise == null || sunset == null) {
        throw new IllegalStateException("Sunrise or sunset data is missing.");
      }

      return Duration.between(LocalTime.parse(sunrise), LocalTime.parse(sunset)).getSeconds(); 

    }  catch(DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid time format for sunrise or sunset");
    }
  }

  static class CurrentConditions {
    @JsonProperty("temp")
    String currentTemperature;

    @JsonProperty("sunrise")
    String sunrise;

    @JsonProperty("sunset")
    String sunset;

    @JsonProperty("feelslike")
    String feelslike;

    @JsonProperty("humidity")
    String humidity;

    @JsonProperty("conditions")
    String conditions;
  }

  static class Days {

    @JsonProperty("datetime")
    String date;

    @JsonProperty("temp")
    String currentTemperature;

    @JsonProperty("tempmax")
    String maxTemperature;

    @JsonProperty("tempmin")
    String minTemperature;

    @JsonProperty("conditions")
    String conditions;

    @JsonProperty("description")
    String description;

  }

}
