package exercise.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.HttpClient;
import exercise.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;

    // Клиент
    HttpClient client;

    // При создании класса сервиса клиент передаётся снаружи
    // В теории это позволит заменить клиент без изменения самого сервиса
    WeatherService(HttpClient client) {
        this.client = client;
    }

    // BEGIN
    public Map<String, String> getCityWeather(String cityName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = client.get("http://weather/api/v2/cities/" + cityName);
        Map<String, String> result = new HashMap<>();
        try {
            result = objectMapper.readValue(response, Map.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return result;
    }
    // END
}
