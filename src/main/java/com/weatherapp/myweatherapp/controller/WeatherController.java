package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Controller layer responsible for accepting HTTP requests.
 * This class interacts with the Service layer to trigger the appropriate business logic based on the endpoint.  
 */
@Controller
@Tag(name = "Weather Controller", description = "APIs for accessing weather data from Visual Crossing Weather.")
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  /**
   * Endpoint for retrieving the weather forecast for a given city.
   * 
   * @param city The city name
   * @return A HTTP response with the body containing a CityInfo object.
   */
  @GetMapping("/forecast/{city}")
  @Operation(summary = "Get weather forecast", description = "Fetches the weather data for the specified city.")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {
    CityInfo ci = weatherService.forecastByCity(city);

    return ResponseEntity.ok(ci);
  }

  /**
   * Endpoint for comparing the daylight hours of two cities and specifies which city has the longer daylight duration.
   * 
   * @param city1 The first city.
   * @param city2 The second city.
   * @return A HTTP response with the body containing a string stating which city has the longer daylight duration.
   */
  @GetMapping("/compare-daylight/{city1}/{city2}")
  @Operation(summary = "Compare daylight hours", description = "Compares the daylight duration between two cities and states which city has the longer daylight duration.")
  public ResponseEntity<String> compareDaylightHours(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
    String longestDay = weatherService.compareDaylightHours(city1, city2);

    return ResponseEntity.ok(longestDay);
  }

  /**
   * Endpoint for checking the weather conditions of two cities and specifies whether they're experiencing raining or not.
   * 
   * @param city1 The first city.
   * @param city2 The second city.
   * @return A HTTP response with the body containing a string stating where it is currently raining.
   */
  @GetMapping("/rain-check/{city1}/{city2}")
  @Operation(summary = "Check rain conditions", description = "Checks the weather conditions of two cities and states where it is raining.")
  public ResponseEntity<String> rainCheck(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
    String rainConditions = weatherService.rainCheck(city1, city2);

    return ResponseEntity.ok(rainConditions);
  }
}