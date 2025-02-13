package com.weatherapp.myweatherapp.repository;

import com.weatherapp.myweatherapp.model.CityInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Repository layer responsible for accessing the weather data.
 * This class interacts with the Visual Crossing API to retrieve the weather data.
 */
@Repository
public class VisualcrossingRepository {
  private final RestTemplate restTemplate;  
  private final String url;  
  private final String key; 

  /**
   * Creates a VisualcrossingRepository instance.
   * 
   * @param restTemplate Makes HTTP request to the Visual Crossing API.
   * @param url Visual Crossing url.
   * @param key Visual Crossing API key.
   */
  public VisualcrossingRepository(RestTemplate restTemplate, @Value("${weather.visualcrossing.url}") String url, @Value("${weather.visualcrossing.key}") String key) {
    this.restTemplate = restTemplate;
    this.url = url;
    this.key = key;
  }

  /**
   * Retrieves the CityInfo object from Visual Crossing Weather.
   * 
   * @param city The name of the city for which to fetch weather data.
   * @param includeLangId specifies whether the weather data should include code names to describe weather conditions.
   * @return A CityInfo object containing weather details.
   * @throws HttpClientErrorException If API request failed (e.g., invalid city, invalid API key).
   * @throws HttpServerErrorException If the server encounters an internal error.
   * @throws Exception If an unexpected error occurs during the API call.
   */
  public CityInfo getByCity(String city, boolean includeLangId) {
    String uri = url + "timeline/" + city + "?key=" + key;
    uri = includeLangId ? uri + "&lang=id" : uri;

    try {
      return restTemplate.getForObject(uri, CityInfo.class);
    } catch(HttpClientErrorException | HttpServerErrorException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
  }
}
