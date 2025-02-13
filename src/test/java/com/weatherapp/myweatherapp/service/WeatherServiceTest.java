package com.weatherapp.myweatherapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

/**
 * Unit tests for the WeatherService class.
 * These tests ensure that the service methods correctly handle city data, daylight duration and rain conditions.
 */
@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
  //Mocked VisualcrossingRepository used by the service
  @Mock 
  private VisualcrossingRepository weatherRepository;

  //Service being tested
  @InjectMocks 
  private WeatherService weatherService;

  /**
   * Test to verify that the forecastByCity method throws IllegalArgumentException when an invalid city is provided.
   */
  @Test
  @DisplayName("Should throw IllegalArgumentException when city is null")
  public void forecastByCity_invalidCity_throwsIllegalArgumentException() {
    String invalidCity = "invalidCity";
    boolean langid = false;

    when(weatherRepository.getByCity(invalidCity, langid)).thenReturn(null);

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> weatherService.forecastByCity(invalidCity));

    assertEquals("City data could not be found for: " + invalidCity, e.getMessage());

    verify(weatherRepository, times(1)).getByCity(invalidCity, langid);
  }

  /**
   * Test to verify that the forecastByCity method returns the correct CityInfo object when a valid city is provided.
   */
  @Test
  @DisplayName("Should return the correct CityInfo object when the city is valid")
  public void forecastByCity_validCity_returnsCorrectCityInfoObj() {
    String validCity = "London";
    boolean langid = false;

    CityInfo ci = new CityInfo();
    ci.setAddress(validCity);

    when(weatherRepository.getByCity(validCity, langid)).thenReturn(ci);

    CityInfo ci_response = weatherService.forecastByCity(validCity);

    assertEquals(validCity, ci_response.getAddress());

    verify(weatherRepository, times(1)).getByCity(validCity, false);
  }

  /**
   * Test to verify that the compareDaylightHours method throws IllegalArgumentException when one or both of the cities are invalid.
   */
  @Test
  @DisplayName("Should throw IllegalArgumentException when the cities are invalid")
  public void compareDaylightHours_invalidCities_throwsIllegalArgumentException() {
    String invalidCity1 = "invalidCity1";
    String invalidCity2 = "invalidCity2";
    boolean langId = false;

    when(weatherRepository.getByCity(invalidCity1, langId)).thenThrow(new IllegalArgumentException("City data could not be found for: " + invalidCity1));

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> weatherService.compareDaylightHours(invalidCity1, invalidCity2));

    assertEquals("City data could not be found for: " + invalidCity1, e.getMessage());

    verify(weatherRepository, times(1)).getByCity(invalidCity1, langId);
  }

  /**
   * Test to verify that the compareDaylightHours method correctly identifies the city with the longer daylight duration when both cities are valid.
   */
  @Test
  @DisplayName("Should return the city with the longer daylight duration when the cities are valid")
  public void compareDaylightHours_validCities_returnsCorrectCity() {
    String validCity1 = "London";
    String validCity2 = "Tokyo";
    boolean langId = false;

    CityInfo ci1 = mock(CityInfo.class); 
    CityInfo ci2 = mock(CityInfo.class);

    ci1.setAddress(validCity1);
    ci2.setAddress(validCity2);

    when(weatherRepository.getByCity(validCity1, langId)).thenReturn(ci1);
    when(weatherRepository.getByCity(validCity2, langId)).thenReturn(ci2);

    when(ci1.getDaylightDuration()).thenReturn(9000L);  
    when(ci2.getDaylightDuration()).thenReturn(10000L); 

    String result = weatherService.compareDaylightHours(validCity1, validCity2);

    assertEquals(ci2.getAddress() + " has the longer daylight duration.", result);

    verify(ci1, times(1)).getDaylightDuration(); 
    verify(ci2, times(1)).getDaylightDuration();

    verify(weatherRepository, times(1)).getByCity(validCity1, langId);
    verify(weatherRepository, times(1)).getByCity(validCity2, langId);
  }

  /**
   * Test to verify that the compareDaylightHours method correctly identifies when both cities have the same daylight duration.
   */
  @Test
  @DisplayName("Should return both cities when their daylight duration is identical")
  public void compareDaylightHours_equalDaylight_returnsBothCities() {
    String validCity1 = "London";
    String validCity2 = "Manchester";
    boolean langId = false;

    CityInfo ci1 = mock(CityInfo.class); 
    CityInfo ci2 = mock(CityInfo.class);

    ci1.setAddress(validCity1);
    ci2.setAddress(validCity2);

    when(weatherRepository.getByCity(validCity1, langId)).thenReturn(ci1);
    when(weatherRepository.getByCity(validCity2, langId)).thenReturn(ci2);

    when(ci1.getDaylightDuration()).thenReturn(9000L);  
    when(ci2.getDaylightDuration()).thenReturn(9000L); 

    String result = weatherService.compareDaylightHours(validCity1, validCity2);

    assertEquals("Both cities have the same daylight duration.", result);

    verify(weatherRepository, times(1)).getByCity(validCity1, langId);
    verify(weatherRepository, times(1)).getByCity(validCity2, langId);

    verify(ci1, times(1)).getDaylightDuration(); 
    verify(ci2, times(1)).getDaylightDuration();
  }

  /**
   * Test to verify that the rainCheck method throws IllegalArgumentException when one or both cities are invalid.
   */
  @Test
  @DisplayName("Should throw IllegalArgumentException when the cities are invalid")
  public void rainCheck_invalidCities_throwsIllegalArgumentException() {
    String invalidCity1 = "invalidCity1";
    String invalidCity2 = "invalidCity2";
    boolean langId = true;

    when(weatherRepository.getByCity(invalidCity1, langId)).thenThrow(new IllegalArgumentException("City data could not be found for: " + invalidCity1));

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> weatherService.rainCheck(invalidCity1, invalidCity2));

    assertEquals("City data could not be found for: " + invalidCity1, e.getMessage());

    verify(weatherRepository, times(1)).getByCity(invalidCity1, langId);
  }

  /**
   * Test to verify that the rainCheck method returns the correct result when both cities are experiencing rain conditions.
   */
  @Test
  @DisplayName("Should return both cities when the they both have rain conditions")
  public void rainCheck_bothCitiesHaveRain_returnsBothCities() {
    String validCity1 = "London";
    String validCity2 = "Manchester";
    boolean langId = true;

    CityInfo ci1 = mock(CityInfo.class); 
    CityInfo ci2 = mock(CityInfo.class);

    ci1.setAddress(validCity1);
    ci2.setAddress(validCity2);

    when(weatherRepository.getByCity(validCity1, langId)).thenReturn(ci1);
    when(weatherRepository.getByCity(validCity2, langId)).thenReturn(ci2);

    when(ci1.isRaining()).thenReturn(true);  
    when(ci2.isRaining()).thenReturn(true); 

    String result = weatherService.rainCheck(validCity1, validCity2);

    assertEquals(ci1.getAddress() + " and " + ci2.getAddress() + " are experiencing rain.", result);

    verify(weatherRepository, times(1)).getByCity(validCity1, langId);
    verify(weatherRepository, times(1)).getByCity(validCity2, langId);

    verify(ci1, times(1)).isRaining(); 
    verify(ci2, times(1)).isRaining();
  }

  /**
   * Test to verify that the rainCheck method returns the correct result when one of the cities is experiencing rain conditions.
   */
  @Test
  @DisplayName("Should return the city which has rain conditions")
  public void rainCheck_oneCityHasRain_returnsBothCities() {
    String validCity1 = "London";
    String validCity2 = "Manchester";
    boolean langId = true;

    CityInfo ci1 = mock(CityInfo.class); 
    CityInfo ci2 = mock(CityInfo.class);

    ci1.setAddress(validCity1);
    ci2.setAddress(validCity2);

    when(weatherRepository.getByCity(validCity1, langId)).thenReturn(ci1);
    when(weatherRepository.getByCity(validCity2, langId)).thenReturn(ci2);

    when(ci1.isRaining()).thenReturn(false);  
    when(ci2.isRaining()).thenReturn(true); 

    String result = weatherService.rainCheck(validCity1, validCity2);

    assertEquals(ci2.getAddress() + " is experiencing rain.", result);

    verify(weatherRepository, times(1)).getByCity(validCity1, langId);
    verify(weatherRepository, times(1)).getByCity(validCity2, langId);

    verify(ci1, times(1)).isRaining(); 
    verify(ci2, times(1)).isRaining();
  }

  /**
   * Test to verify that the rainCheck method returns the correct result when neither of the cities are experiencing rain conditions.
   */
  @Test
  @DisplayName("Should return neither cities when the they both don't have conditions")
  public void rainCheck_bothCitiesNoRain_returnsBothCities() {
    String validCity1 = "London";
    String validCity2 = "Manchester";
    boolean langId = true;

    CityInfo ci1 = mock(CityInfo.class); 
    CityInfo ci2 = mock(CityInfo.class);

    ci1.setAddress(validCity1);
    ci2.setAddress(validCity2);

    when(weatherRepository.getByCity(validCity1, langId)).thenReturn(ci1);
    when(weatherRepository.getByCity(validCity2, langId)).thenReturn(ci2);

    when(ci1.isRaining()).thenReturn(false);  
    when(ci2.isRaining()).thenReturn(false); 

    String result = weatherService.rainCheck(validCity1, validCity2);

    assertEquals("It is not raining in either city.", result);

    verify(weatherRepository, times(1)).getByCity(validCity1, langId);
    verify(weatherRepository, times(1)).getByCity(validCity2, langId);

    verify(ci1, times(1)).isRaining(); 
    verify(ci2, times(1)).isRaining();
  }
}