package ru.telegrambot.bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Weather {

    private static final String apiKey = System.getenv("OPENWEATHERMAP_API_KEY");

    public static String getWeatherReport(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + apiKey + "&units=metric&lang=ru";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseWeatherResponse(response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при получении прогноза погоды для города: " + city;
        }
    }

    private static String parseWeatherResponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            String cityName = root.path("name").asText();
            String weatherDescription = root.path("weather").get(0).path("description").asText();
            double temperature = root.path("main").path("temp").asDouble();
            double feelsLike = root.path("main").path("feels_like").asDouble();
            int humidity = root.path("main").path("humidity").asInt();

            return String.format("Погода в %s: %s\nТемпература: %.2f°C (ощущается как %.2f°C)\nВлажность: %d%%",
                    cityName, weatherDescription, temperature, feelsLike, humidity);
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при разборе ответа.";
        }
    }
}