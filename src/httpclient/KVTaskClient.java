package httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String apiToken;
    private final String baseUrl;
    private final HttpClient client;

    public KVTaskClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newHttpClient();
        this.apiToken = register();
    }

    private String register() {
        try {
            URI uri = URI.create(baseUrl + "/register");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("Не удалось зарегистрироваться на KVServer. Код ответа: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка при регистрации на KVServer: " + e.getMessage(), e);
        }
    }

    public void put(String key, String json) {
        try {
            URI uri = URI.create(baseUrl + "/save/" + key + "?API_TOKEN=" + apiToken);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Не удалось сохранить данные. Код ответа: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка при сохранении данных: " + e.getMessage(), e);
        }
    }

    public String load(String key) {
        try {
            URI uri = URI.create(baseUrl + "/load/" + key + "?API_TOKEN=" + apiToken);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else if (response.statusCode() == 404) {
                return null;
            } else {
                throw new RuntimeException("Не удалось загрузить данные. Код ответа: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка при загрузке данных: " + e.getMessage(), e);
        }
    }
}
