package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo config.properties");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar configuración: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Configuración faltante: '" + key + "'");
        }
        return value;
    }

    public static int getInt(String key) {
        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Configuración inválida para '" + key + "': debe ser un número entero.");
        }
    }
}