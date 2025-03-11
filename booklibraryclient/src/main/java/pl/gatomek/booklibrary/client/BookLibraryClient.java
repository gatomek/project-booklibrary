package pl.gatomek.booklibrary.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gatomek.booklibrary.dto.LibraryItem;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class BookLibraryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookLibraryClient.class);

    private final Properties properties = new Properties();

    private BookLibraryClient() {
        loadProperties();
    }

    private void loadProperties() {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/application.properties");
            properties.load(resourceAsStream);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private static String argsToFileHash(String[] args) {
        String arg = args[0];
        return arg.substring(20, arg.length() - 1);
    }

    public static void main(String[] args) {
        try {
            BookLibraryClient client = new BookLibraryClient();
            client.resolve(argsToFileHash(args));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private String mapToXwwwFormUrlEncoded(Map<String, String> parameters) {
        return parameters
                .entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    private void ok(String fileHash, String body) throws IOException {
        LOGGER.info("{}: ok", fileHash);

        ObjectMapper objectMapper = new ObjectMapper();
        LibraryItem libraryItem = objectMapper.readValue(body, LibraryItem.class);

        String filePath = libraryItem.filePath();
        File file = new File(filePath);
        if (file.exists()) {
            LOGGER.info("{}: ok", filePath);
            Runtime.getRuntime().exec(new String[]{"explorer", file.getAbsolutePath()});
        } else {
            LOGGER.error("{}: not found", filePath);
        }
    }

    private void notFoundOnServer(String fileHash) {
        LOGGER.error("{}: not found on server", fileHash);
    }

    private void internalServerError(String fileHash) {
        LOGGER.error("{}: internal server error", fileHash);
    }

    private void unknownServerError(String fileHash, int statusCode) {
        LOGGER.error("{}: unknown server error: {}", fileHash, statusCode);
    }

    public void resolve(String fileHash) throws IOException, URISyntaxException, InterruptedException {
        LOGGER.info("request: {}", fileHash);

        Map<String, String> parameters = Collections.singletonMap("hash", fileHash);
        String form = mapToXwwwFormUrlEncoded(parameters);
        String endpoint = properties.getProperty("endpoint");

        HttpRequest req = HttpRequest
                .newBuilder(new URI(endpoint))
                .timeout(Duration.ofSeconds(15))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            switch (response.statusCode()) {
                case 200:
                    ok(fileHash, response.body());
                    break;
                case 404:
                    notFoundOnServer(fileHash);
                    break;
                case 500:
                    internalServerError(fileHash);
                    break;
                default:
                    unknownServerError(fileHash, response.statusCode());
            }
        }
    }
}
