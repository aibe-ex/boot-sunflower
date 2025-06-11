package org.example.bootsunflower.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.bootsunflower.dto.GeminiRequestDTO;
import org.example.bootsunflower.dto.GeminiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {
    @Value("${gemini.key}")
    private String geminiKey;

    public String generate(String text) throws IOException, InterruptedException {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=%s".formatted(geminiKey);
        GeminiRequestDTO dto = new GeminiRequestDTO(List.of(new GeminiRequestDTO.Content(List.of(new GeminiRequestDTO.Part(text)))));
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(dto)))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Gemini API returned status code %d".formatted(response.statusCode()));
        }
        GeminiResponseDTO responseDTO = mapper.readValue(response.body(), GeminiResponseDTO.class);

        return responseDTO.candidates().get(0).content().parts().get(0).text();
    }
}
