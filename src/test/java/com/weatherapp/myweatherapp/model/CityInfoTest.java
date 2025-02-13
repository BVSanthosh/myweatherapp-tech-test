package com.weatherapp.myweatherapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the CityInfo class.
 * These tests validate the methods of the CityInfo class to ensure that daylight duration and rain conditions are calculated correctly.
 */
public class CityInfoTest {
    /**
     * Test to verify that the getDaylightDuration method throws an IllegalStateException when both sunrise and sunset are null.
     */
    @Test
    @DisplayName("Should throw IllegalStateException when sunrise or sunset are null")
    public void getDaylightDuration_nullSunriseAndSunset_throwsIllegalStateException() {
        CityInfo ci = new CityInfo();

        ci.setSunrise(null);
        ci.setSunset(null);

        IllegalStateException e = assertThrows(IllegalStateException.class, ci::getDaylightDuration);
        assertEquals("Sunrise or sunset data is missing for: " + ci.getAddress(), e.getMessage());
    }

    /**
     * Test to verify that the getDaylightDuration method throws an IllegalArgumentException when sunrise or sunset have invalid formats.
     */
    @Test
    @DisplayName("Should throw IllegalArgumentException when sunrise or sunset are invalid")
    public void getDaylightDuration_nullSunriseAndSunset_throwsIllegalArgumentException() {
        CityInfo ci = new CityInfo();

        ci.setSunrise("32:00");
        ci.setSunset("22:00");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ci::getDaylightDuration);
        assertEquals("Invalid time format for sunrise or sunet for: " + ci.getAddress(), e.getMessage());
    }

    /**
     * Test to verify that the getDaylightDuration method correctly calculates the daylight duration when both sunrise and sunset have valid time formats.
     */
    @Test
    @DisplayName("Should return correct daylight duration when sunset and sunrise are valid")
    public void getDaylightDuration_validSunriseAndSunset_returnsCorrectDuration() {
        CityInfo ci = new CityInfo();

        ci.setSunrise("12:00");
        ci.setSunset("22:00");

        long duration = ci.getDaylightDuration();
        assertEquals(36000, duration);
    }

    /**
     * Test to verify that the isRaining method throws an IllegalStateException when conditions are null.
     */
    @Test
    @DisplayName("Should throw IllegalStateException when conditions is null")
    public void isRaining_invalidConditions_throwsIllegalStateException() {
        CityInfo ci = new CityInfo();

        ci.setConditions(null);

        IllegalStateException e = assertThrows(IllegalStateException.class, ci::isRaining);
        assertEquals("Conditions data is missing for: " + ci.getAddress(), e.getMessage());
    }

    /**
     * Test to verify that the isRaining method returns false when the conditions do not contain any rain types.
     */
    @Test
    @DisplayName("Should return false when conditions doesn't include rain")
    public void isRaining_noRain_returnsFalse() {
        CityInfo ci = new CityInfo();

        ci.setConditions("type_1, type_2");

        boolean hasRain = ci.isRaining();
        assertEquals(false, hasRain);
    }

    /**
     * Test to verify that the isRaining method returns true when the conditions include a rain-related type.
     */
    @Test
    @DisplayName("Should return true when conditions include rain")
    public void isRaining_hasRain_returnsTrue() {
        CityInfo ci = new CityInfo();

        ci.setConditions("type_1, type_10");

        boolean hasRain = ci.isRaining();
        assertEquals(true, hasRain);
    }
}
