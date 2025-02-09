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
      throw new IllegalArgumentException("One or both of the cities could noto be found.");
    }

    long city1Daylight = ci1.getDaylightDuration();
    long city2Daylight = ci2.getDaylightDuration();

    if (city1Daylight > city2Daylight) {
      return ci1.getAddress();
    } else if (city1Daylight < city2Daylight) {
      return ci2.getAddress();
    } else {
      return "Both cities have the same dayight duration";
    }
  }
}
