package me.enoi.weather.dto;

/**
 *  Clase para envolver la respuesta, y mantener un estandar
 */
public class Response {
    public WeatherResponse data;
    public ErrorResponse error;

    /**
     *  Contructor para respuesta correcta
     * @param data Datos de respuesta
     */
    public Response(WeatherResponse data) {
        this.data = data;
    }

    /**
     * Constructor para envio de error
     * @param err Objecto de error
     */
    public Response(ErrorResponse err) {
        this.error = err;
    }
}
