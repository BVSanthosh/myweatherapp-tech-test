package com.weatherapp.myweatherapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CityInfoTest {
    
    @Test
    @DisplayName("Should throw IllegalStateException when sunrise or sunset are null")
    public void getDaylightDuration_nullSunriseAndSunset_throwsIllegalStateException() {
        CityInfo ci = new CityInfo();

        ci.setSunrise(null);
        ci.setSunset(null);

        IllegalStateException e = assertThrows(IllegalStateException.class, ci::getDaylightDuration);
        assertEquals("Sunrise or sunset data is missing for: " + ci.getAddress(), e.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when sunrise or sunset are invalid")
    public void getDaylightDuration_nullSunriseAndSunset_throwsIllegalArgumentException() {
        CityInfo ci = new CityInfo();

        ci.setSunrise("32:00");
        ci.setSunset("22:00");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ci::getDaylightDuration);
        assertEquals("Invalid time format for sunrise or sunet for: " + ci.getAddress(), e.getMessage());
    }

    @Test
    @DisplayName("Should return correct daylight duration when sunset and sunrise are valid")
    public void getDaylightDuration_validSunriseAndSunset_returnsCorrectDuration() {
        CityInfo ci = new CityInfo();

        ci.setSunrise("12:00");
        ci.setSunset("22:00");

        long duration = ci.getDaylightDuration();
        assertEquals(36000, duration);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when conditions is null")
    public void isRaining_invalidConditions_throwsIllegalStateException() {
        CityInfo ci = new CityInfo();

        ci.setConditions(null);

        IllegalStateException e = assertThrows(IllegalStateException.class, ci::isRaining);
        assertEquals("Conditions data is missing for: " + ci.getAddress(), e.getMessage());
    }

    @Test
    @DisplayName("Should return false when conditions doesn't include rain")
    public void isRaining_noRain_returnsFalse() {
        CityInfo ci = new CityInfo();

        ci.setConditions("type_1, type_2");

        boolean hasRain = ci.isRaining();
        assertEquals(false, hasRain);
    }

    @Test
    @DisplayName("Should return true when conditions include rain")
    public void isRaining_hasRain_returnsTrue() {
        CityInfo ci = new CityInfo();

        ci.setConditions("type_1, type_10");

        boolean hasRain = ci.isRaining();
        assertEquals(true, hasRain);
    }
}
