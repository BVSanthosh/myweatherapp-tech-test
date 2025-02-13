package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for executing the business logic.
 * This class interacts with the Repository and Model layers to produce an output to be sent to the Controller.
 */
@Service
public class WeatherService {
  //Injected repository to fetch the weater data from Visual Crossing API
  @Autowired
  VisualcrossingRepository weatherRepo;

  /**
   * Retrieves the weather forecast for a given city.
   * 
   * @param city The name of the city for which to fetch weather data.
   * @return A CityInfo object containing weather details.
   * @throws IllegalArgumentException If the city is not found.
   */
  public CityInfo forecastByCity(String city) {
    CityInfo ci = weatherRepo.getByCity(city, false);

    if (ci == null) {
      throw new IllegalArgumentException("City data could not be found for: " + city);
    }

    return ci;
  }

  /**
   * Compares the daylight duration between the given cities. 
   * 
   * @param city1 The first city.
   * @param city1 The second city.
   * @return A message specifiying which city has the longer daylight duration.
   */
  public String compareDaylightHours(String city1, String city2) {
    CityInfo ci1 = weatherRepo.getByCity(city1, false);
    CityInfo ci2 = weatherRepo.getByCity(city2, false);

    long city1Daylight = ci1.getDaylightDuration();
    long city2Daylight = ci2.getDaylightDuration();

    if (city1Daylight > city2Daylight) {
      return ci1.getAddress() + " has the longer daylight duration.";
    } else if (city1Daylight < city2Daylight) {
      return ci2.getAddress() + " has the longer daylight duration.";
    } else {
      return "Both cities have the same daylight duration.";
    }
  }

  /**
   * Checks the weather conditions in the given cities. 
   * 
   * @param city1 The first city.
   * @param city1 The second city.
   * @return A message specifiying where it is currently raining.
   */
  public String rainCheck(String city1, String city2) {
    CityInfo ci1 = weatherRepo.getByCity(city1, true);
    CityInfo ci2 = weatherRepo.getByCity(city2, true);

    boolean city1HasRain = ci1.isRaining();
    boolean city2HasRain = ci2.isRaining();

    if (city1HasRain && city2HasRain) {
      return ci1.getAddress() + " and " + ci2.getAddress() + " are experiencing rain.";
    } else if (city1HasRain) {
      return ci1.getAddress() + " is experiencing rain";
    } else if (city2HasRain) {
      return ci2.getAddress() + " is experiencing rain.";
    } else {
      return "It is not raining in either city.";
    }
  }
}
