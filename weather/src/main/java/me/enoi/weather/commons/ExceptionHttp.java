package me.enoi.weather.commons;

/**
 *  Excepcion personalizada
 */
public class ExceptionHttp extends RuntimeException {
    public int statusCode;

    public ExceptionHttp(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }
}
