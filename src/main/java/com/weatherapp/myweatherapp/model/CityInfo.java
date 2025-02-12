package com.weatherapp.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CityInfo {
  private static final Set<String> RAIN_CODES = Set.of(
    "type_21",
    "type_10",
    "type_11",
    "type_13",
    "type_14",
    "type_22",
    "type_23",
    "type_24",
    "type_25",
    "type_26",
    "type_3",
    "type_32",
    "type_37",
    "type_4",
    "type_5",
    "type_6",
    "type_9"
  );
  
  @JsonProperty("address")
  String address;

  @JsonProperty("description")
  String description;

  @JsonProperty("currentConditions")
  CurrentConditions currentConditions;

  @JsonProperty("days")
  List<Days> days;

  public CityInfo () {
    this.currentConditions = new CurrentConditions();
    this.days = new ArrayList<>(); 
  }

  public String getAddress(){
    return address;
  }

  public void setAddress(String address){
    this.address = address;
  }

  public String getSunrise() {
    return currentConditions.sunrise;
  }

  public void setSunrise(String sunrise) {
    this.currentConditions.sunrise = sunrise;
  }

  public String getSunset() {
    return currentConditions.sunset;
  }

  public void setSunset(String sunset) {
    this.currentConditions.sunset = sunset;
  }

  public String getConditions() {
    if (currentConditions == null || currentConditions.conditions == null) {
      throw new IllegalStateException("Conditions data is missing for: " + address);
    }

    return currentConditions.conditions;
  }

  public void setConditions(String conditions) {
    currentConditions.conditions = conditions;
  }

  public long getDaylightDuration() {
    String sunrise = getSunrise();
    String sunset = getSunset();

    if (sunrise == null || sunset == null) {
      throw new IllegalStateException("Sunrise or sunset data is missing for: " + address);
    }

    try {
      return Duration.between(LocalTime.parse(sunrise), LocalTime.parse(sunset)).getSeconds(); 
    } catch(DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid time format for sunrise or sunet for: " + address);
    }
  }

  public boolean isRaining() {
    return Arrays.stream(getConditions().split(","))
      .map(String::trim)
      .anyMatch(RAIN_CODES::contains);
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
