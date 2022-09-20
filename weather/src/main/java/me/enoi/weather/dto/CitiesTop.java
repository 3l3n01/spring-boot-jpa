package me.enoi.weather.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 *  Clase para el resultado de las ciudades mas buscadas.
 */
public interface  CitiesTop {
    @ApiModelProperty(value = "Name of the city")
    String getCity();

    @ApiModelProperty(value = "Total searches")
    int getSearches();
}
