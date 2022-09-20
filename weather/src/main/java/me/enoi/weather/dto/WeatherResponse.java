package me.enoi.weather.dto;

public class WeatherResponse {
    public Coord coord;
    public Weather[] weather;
    public String base;
    public DataWeather main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}
