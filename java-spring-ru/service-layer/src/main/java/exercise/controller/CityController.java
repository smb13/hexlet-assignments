package exercise.controller;
import exercise.CityNotFoundException;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    // BEGIN
    @GetMapping("/cities/{id}")
    public Object getCity(@PathVariable Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException("City " + id + " was not found"));
        return weatherService.getCityWeather(city.getName());
    }

    @GetMapping("/search")
    public Iterable<Map<String, String>> searchCities(@RequestParam(required = false) String name) {
        List<City> cities;
        if (name != null) {
            cities = cityRepository.findByNameStartingWithIgnoreCase(name).orElseThrow(() -> new CityNotFoundException("City begins " + name + " was not found"));
        } else {
            cities = cityRepository.findByNameNotNullOrderByName().orElseThrow(() -> new CityNotFoundException("City begins " + name + " was not found"));
        }
        return cities.stream()
                .map(city -> city.getName())
                .map(cityName -> weatherService.getCityWeather(cityName))
                .map(pojo -> {
                    pojo.remove("cloudy");
                    pojo.remove("humidity");
                    pojo.remove("wind");
                    return pojo;
                })
                .toList();
    }
    // END
}

