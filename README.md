# MyWeather App Tech Test

## Description

MyWeatherApp is a Spring Boot application that retrieves weather data from the Visual Crossing API.

It provides the following features:

- **Forecast by City**: Get current weather conditions for a given city.
- **Compare Daylight Hours**: Compare the daylight duration between two cities.
- **Rain Check**: Determine whether it is raining in the specified cities.

## Submission Includes
- Functionality for comparing daylight and rain check features
- Local and Global Exception handling
- Unit and API tests
- Documentation

## Technologies Used
- Java 17
- Spring Boot 3.0.6
- Swagger for API documentation
- JUnit 5 & Mockito for testing
- Maven for dependency management

## Setup & Installation

### Prerequisites
- [Java sdk 17](https://openjdk.java.net/projects/jdk/17/)
- [Maven 3.6.3+](https://maven.apache.org/install.html)
- API key for [Visual Crossing Weather API](https://www.visualcrossing.com/weather-data-editions). 
  - This can be done by creating a free account on the above link. Then you will need to add your key to the `weather.visualcrossing.key` field in src/main/resources/application.properties

### Steps to run the Application
1. **Clone the repository**:
   
   ```sh
   git clone https://github.com/your-username/myweatherapp.git
   cd myweatherapp-tech-test
   ```
2. Configure API Key in application.properties:
   ```sh
   weather.visualcrossing.url=https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/
   weather.visualcrossing.key=your_api_key_here
   ```
3. Run the Application:

   ```sh
   mvn spring-boot:run
   ```
4. Run unit tests:
   ```sh
   mvn test
   ```
5. Access the Swagger API Docs: http://localhost:8080/swagger-ui.html  
   
   

