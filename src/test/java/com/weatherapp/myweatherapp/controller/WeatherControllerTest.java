package com.weatherapp.myweatherapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

    @InjectMocks
    WeatherController weatherController;

    @Mock
    private WeatherService weatherService; 

    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    @DisplayName("Should throw HttpClientErrorException  when the city is invalid")
    public void forecastByCity_invalidCity_throwsHttpClientErrorException(){
        String invalidCity = "invalidCity";

        when(weatherService.forecastByCity(invalidCity)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad API Request: Invalid address format"));

        HttpClientErrorException e = assertThrows(HttpClientErrorException.class, () -> weatherController.forecastByCity(invalidCity));

        assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());

        verify(weatherService, times(1)).forecastByCity(invalidCity);
    }

    @Test
    @DisplayName("Should return the correct CityInfo object as when the city is valid")
    public void forecastByCity_validCity_returnsCityInfo() {
        String validCity = "London";
        CityInfo ci = new CityInfo();
        ci.setAddress(validCity);

        when(weatherService.forecastByCity(validCity)).thenReturn(ci);

        ResponseEntity<CityInfo> response = weatherController.forecastByCity(validCity);

        assertEquals(validCity, response.getBody().getAddress());
    }

    @Test
    @DisplayName("Should throw HttpClientErrorException when the cities are invalid")
    public void compareDaylightHours_invalidCities_throwsHttpClientErrorException() {
        String invalidCity1 = "invalidCity1";
        String invalidCity2 = "invalidCity2";

        when(weatherService.compareDaylightHours(invalidCity1, invalidCity2)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "One or both of the cities could not be found."));

        HttpClientErrorException e = assertThrows(HttpClientErrorException.class, () -> weatherController.compareDaylightHours(invalidCity1, invalidCity2));

        assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());

        verify(weatherService, times(1)).compareDaylightHours(invalidCity1, invalidCity2);
    }

    @Test
    @DisplayName("Should return the city with longer daylight when the cities are valid")
    public void compareDaylightHours_validCities_returnsCorrectCity() {
        String validCity1 = "Liverpool";
        String validCity2 = "Mumbai";
        String result = validCity2 + " has the longer daylight duration.";

        when(weatherService.compareDaylightHours(validCity1, validCity2)).thenReturn(result);

        ResponseEntity<String> response = weatherController.compareDaylightHours(validCity1, validCity2);

        assertEquals(result, response.getBody());

        verify(weatherService, times(1)).compareDaylightHours(validCity1, validCity2);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when the cities is invalid")
    public void rainCheck_invalidCities_throwsIllegalArgumentException() {
        String invalidCity1 = "invalidCity1";
        String invalidCity2 = "invalidCity2";

        when(weatherService.rainCheck(invalidCity1, invalidCity2)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "One or both of the cities could not be found."));

        HttpClientErrorException e = assertThrows(HttpClientErrorException.class, () -> weatherController.rainCheck(invalidCity1, invalidCity2));

        assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());

        verify(weatherService, times(1)).rainCheck(invalidCity1, invalidCity2);
    }

    @Test
    @DisplayName("Should return the cities with rain when the cities are valid")
    public void rainCheck_validCities_returnsCorrectCity() {
        String validCity1 = "Paris";
        String validCity2 = "Sydney";
        String result = validCity1 + " is experiencing rain.";

        when(weatherService.rainCheck(validCity1, validCity2)).thenReturn(result);

        ResponseEntity<String> response = weatherController.rainCheck(validCity1, validCity2);

        assertEquals(result, response.getBody());

        verify(weatherService, times(1)).rainCheck(validCity1, validCity2);
    }
}
