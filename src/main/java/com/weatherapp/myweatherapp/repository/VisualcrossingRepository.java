package com.weatherapp.myweatherapp.repository;

import com.weatherapp.myweatherapp.model.CityInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class VisualcrossingRepository {

  @Value("${weather.visualcrossing.url}")
  String url;
  @Value("${weather.visualcrossing.key}")
  String key;


  public CityInfo getByCity(String city, boolean includeId) {
    String uri = url + "timeline/" +city + "?key=" + key;
    uri = includeId ? uri + "&lang=id" : uri;

    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(uri, CityInfo.class);
  }
}
