package com.weatherapp.myweatherapp.repository;

import com.weatherapp.myweatherapp.model.CityInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Repository
public class VisualcrossingRepository {
  private final RestTemplate restTemplate;
  private final String url;
  private final String key;

  public VisualcrossingRepository(RestTemplate restTemplate, @Value("${weather.visualcrossing.url}") String url, @Value("${weather.visualcrossing.key}") String key) {
    this.restTemplate = restTemplate;
    this.url = url;
    this.key = key;
  }

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
