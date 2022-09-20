package me.enoi.weather.commons;

import java.text.MessageFormat;

public class Util {

    /**
     * Metodo para generar la URL requerida para el consumo del servicio.
     * @param template Template de la URL
     * @param name Nombre de la ciudad a buscar
     * @return String
     */
    public String makeUri(String template, String name) {
        Object[] params = new Object[]{name, this.env("API_KEY", "10bb633e92711b79dcd8ed6d556ac8b9")};
        return MessageFormat.format(template, params);
    }

    /**
     * Metodo para recuperar una variable de entorno o asignar una por default
     * @param name nombre de la variable
     * @param dfl Valor por defecto en caso de encontrar la variable
     * @return String
     */
    public String env(String name, String dfl) {
        String e = System.getenv(name);

        if (e == null) {
            return dfl;
        } else {
            return e;
        }
    }


}
