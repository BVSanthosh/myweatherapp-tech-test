package com.weatherapp.myweatherapp.respository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

/**
 * Unit tests for the VisualcrossingRepository class.
 * These tests ensure that the repository makes requests to and handles responses from the Visual Crossing API, including successful data retrival and error handling.
 */
@ExtendWith(MockitoExtension.class)
public class VisualcrossingRepositoryTest {
    // Mocked RestTemplate used by the repository for making API calls
    @Mock
    private RestTemplate restTemplate;

    // The repository being tested
    private VisualcrossingRepository repository;

    /**
     * Setup method to initialize the repository and mocks before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify that the getByCity method throws HttpClientErrorException (404 NOT_FOUND) when an invalid city name is provided.
     */
    @Test
    @DisplayName("Should throw HttpClientErrorException when city name is invalid")
    public void getByCity_invalidCity_throwsClientErrorException() {
        repository = new VisualcrossingRepository(restTemplate, "https://test-url.com/", "test_key"); 

        String invalidCity = "invalidCity";
        String validKey = "?key=test_key";
        String url = "https://test-url.com/timeline/" + invalidCity + validKey;
        boolean langId = false;

        when(restTemplate.getForObject(url, CityInfo.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        HttpClientErrorException e = assertThrows(HttpClientErrorException.class, () -> repository.getByCity(invalidCity, langId));

        assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        assertEquals("404 NOT_FOUND", e.getMessage());

        verify(restTemplate, times(1)).getForObject(url, CityInfo.class);
    }

    /**
     * Test to verify that the getByCity method throws HttpClientErrorException (401 UNAUTHORIZED) when an invalid API key is provided.
     */
    @Test
    @DisplayName("Should throw HttpClientErrorException when API key is invalid")
    public void getByCity_invalidAPIKey_throwsClientErrorException() {
        repository = new VisualcrossingRepository(restTemplate, "https://test-url.com/", "invalid_key"); 

        String validcity = "London";
        String invalidKey = "?key=invalid_key";
        String url = "https://test-url.com/timeline/" + validcity + invalidKey;

        when(restTemplate.getForObject(url, CityInfo.class)).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        HttpClientErrorException e = assertThrows(HttpClientErrorException.class, () -> repository.getByCity(validcity, false));

        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusCode());
        assertEquals("401 UNAUTHORIZED", e.getMessage());

        verify(restTemplate, times(1)).getForObject(url, CityInfo.class);
    }

    /**
     * Test to verify that the getByCity method correctly returns a CityInfo object when both the API key and city name are valid.
     */
    @Test
    @DisplayName("Should return correct CityInfo object when API key and city name are valid")
    public void getByCity_validCityAndAPIKey_returnsCityInfoObj() {
        repository = new VisualcrossingRepository(restTemplate, "https://test-url.com/", "test_key"); 

        String validCity = "London";
        String validKey = "?key=test_key";
        String url = "https://test-url.com/timeline/" + validCity + validKey;

        CityInfo mockResponse = new CityInfo();
        mockResponse.setAddress("London");

        when(restTemplate.getForObject(url, CityInfo.class)).thenReturn(mockResponse);

        CityInfo ci = repository.getByCity(validCity, false);

        assertNotNull(ci);
        assertEquals("London", ci.getAddress());

        verify(restTemplate, times(1)).getForObject(url, CityInfo.class);
    }

}
