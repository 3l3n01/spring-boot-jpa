package me.enoi.weather.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.enoi.weather.commons.ExceptionHttp;
import me.enoi.weather.commons.RedisClient;
import me.enoi.weather.commons.Util;
import me.enoi.weather.dto.CitiesTop;
import me.enoi.weather.dto.WeatherResponse;
import me.enoi.weather.entities.CityEntity;
import me.enoi.weather.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

/**
 *
 */
@Service
public class WeatherService {

    @Autowired
    CityRepository<CityEntity> CityRepository;

    private final HttpClient client = HttpClient.newHttpClient();
    private final Util util = new Util();

    private final Jedis redis;

    public WeatherService() {
        RedisClient redisClient = new RedisClient();
        this.redis = redisClient.getConnection();
    }

    /**
     * Metodo para consultar los datos segun el nombre de la ciudad
     * @param name Nombre de la ciudad
     * @return String response
     */
    @Transactional
    public WeatherResponse getData(String name) throws JsonProcessingException, ExceptionHttp {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = null;
        HttpResponse<String> response;
        String msg = "";
        int timeOut = Integer.parseInt(util.env("TIMEOUT", "5000"));

        try {
            String templateUri = "https://api.openweathermap.org/data/2.5/weather?q={0}&appid={1}&units=metric";
            request = HttpRequest.newBuilder()
                    .uri(new URI(this.util.makeUri(templateUri, name)))
                    .timeout(Duration.ofMillis(timeOut)) // Solo para poder probar que responde la cache
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            // From cache
            if (this.redis.get(name) != null) {
                System.out.println("Response from cache");
                CityRepository.save(new CityEntity(name));
                return mapper.readValue(this.redis.get(name), WeatherResponse.class);
            }
            //->
            throw new ExceptionHttp(
                    String.format("Por el momento no se puede consultar los datos de la ciudad %s, intentarlo mas tarde.", name),
                    501
            );
        }

        // Is ok response
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            this.redis.set(name, response.body()); // Cache response
            CityRepository.save(new CityEntity(name)); // Save search
            // Return response
            return mapper.readValue(response.body(), WeatherResponse.class);
        } else if (response.statusCode() == 404) {
            msg = String.format("La ciudad %s no se encuentra registrada para consultar sus datos.", name);
        } else {
            msg = String.format("Ocurrio un problema al recuperar los datos de la ciudad %s", name);
        }

        // Default
        throw new ExceptionHttp(msg, response.statusCode() > 0 ? response.statusCode() : 501);
    }


    /**
     * Recupera el top 10 de ciudades mas consultadas
     * @return List search city top
     */
    public List<CitiesTop> getTopSearch() {
        return CityRepository.findTopSearch();
    }
}
