package me.enoi.weather.dto;

/**
 *  Clase generica para resolver los errores
 */
public class ErrorResponse {
    public String message;
    public int code;

    /**
     * Constructor
     * @param code http status code
     * @param message Mensaje del error
     */
    public ErrorResponse(int code, String message) {
        this.message = message;
        this.code = code;
    }
}
