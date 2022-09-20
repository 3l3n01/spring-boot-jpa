package me.enoi.weather.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import me.enoi.weather.commons.ExceptionHttp;
import me.enoi.weather.dto.CitiesTop;
import me.enoi.weather.dto.ErrorResponse;
import me.enoi.weather.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Api("Weather")
public class WeatherController {
    @Autowired
    WeatherService wthService;

    @GetMapping("/weather/city")
    @ApiOperation("Check the weather by city")
    public ResponseEntity<Object> city(
            @ApiParam(value = "Name of the city being consulted", required = true)
            @RequestParam(value = "name", defaultValue = "") String name
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.wthService.getData(name));
        } catch (ExceptionHttp | JsonProcessingException e) {
            int st = 501;
            if (e instanceof ExceptionHttp) {
                st = ((ExceptionHttp) e).statusCode;
            }
            return ResponseEntity.status(st > 0 ? st : 501).body(new ErrorResponse(st > 0 ? st : 501,  e.getMessage()));
        }
    }

    @GetMapping("/weather/city/top")
    @ApiOperation("Returns the top of consulted cities")
    public List<CitiesTop> top() {
        return wthService.getTopSearch();
    }
}
