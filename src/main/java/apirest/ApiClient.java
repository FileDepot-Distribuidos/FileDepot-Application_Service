package apirest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:3000/api";

    public static boolean post(String endpoint, String json) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = con.getResponseCode();
            return status >= 200 && status < 300;

        } catch (Exception e) {
            System.err.println("Error POST a " + endpoint + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean delete(String endpoint) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            return responseCode >= 200 && responseCode < 300;

        } catch (Exception e) {
            System.err.println("Error HTTP DELETE: " + e.getMessage());
            return false;
        }
    }

    public static String get(String endpoint) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("[GET] " + url + " → " + responseCode);

            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                return response.toString();
            } else {
                System.err.println("Error HTTP GET → " + responseCode);
                return "{}";
            }

        } catch (Exception e) {
            System.err.println("Excepción GET: " + e.getMessage());
            return "{}";
        }
    }

    public static boolean put(String endpoint, String json) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("utf-8"));
            }

            int responseCode = conn.getResponseCode();
            return responseCode >= 200 && responseCode < 300;

        } catch (Exception e) {
            System.err.println("❌ Error HTTP PUT: " + e.getMessage());
            return false;
        }
    }

}
