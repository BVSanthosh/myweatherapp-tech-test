package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  public CityInfo forecastByCity(String city) {

    return weatherRepo.getByCity(city);
  }

  public String compareDaylightHours(String city1, String city2) {
    CityInfo ci1 = weatherRepo.getByCity(city1);
    CityInfo ci2 = weatherRepo.getByCity(city2);

    if (ci1 == null || ci2 == null) {
      throw new IllegalArgumentException("One or both of the cities could not be found.");
    }

    long city1Daylight = ci1.getDaylightDuration();
    long city2Daylight = ci2.getDaylightDuration();

    if (city1Daylight > city2Daylight) {
      return ci1.getAddress() + " has the longer daylight duration";
    } else if (city1Daylight < city2Daylight) {
      return ci2.getAddress() + " has the longer daylight duration";
    } else {
      return "Both cities have the same dayight duration";
    }
  }

  public String rainCheck(String city1, String city2) {
    CityInfo ci1 = weatherRepo.getByCity(city1);
    CityInfo ci2 = weatherRepo.getByCity(city2);

    if (ci1 == null || ci2 == null) {
      throw new IllegalArgumentException("One or both of the cities could not be found.");
    }

    String city1Conditions = ci1.getConditions();
    String city2Conditions = ci2.getConditions();

    if (city1Conditions == null || city2Conditions == null) {
      throw new IllegalArgumentException("One or both of the cities could not be found.");
    }

    boolean city1HasRain = city1Conditions.toLowerCase().contains("rain");
    boolean city2HasRain = city2Conditions.toLowerCase().contains("rain");

    if (city1HasRain && city2HasRain) {
        return ci1.getAddress() + " and " + ci2.getAddress() + " are experiencing rain.";
    } else if (city1HasRain) {
        return ci1.getAddress() + " is experiencing rain.";
    } else if (city2HasRain) {
        return ci2.getAddress() + " is experiencing rain.";
    } else {
        return "It is not raining in either city.";
    }
  }
}
