package org.example.bootsunflower.service;

import lombok.RequiredArgsConstructor;
import org.example.bootsunflower.entity.Prompt;
import org.example.bootsunflower.repository.PromptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;

    public List<Prompt> getAllPrompts() {
        return promptRepository.findAll();
    }

    public Prompt getPromptById(String id) {
        return promptRepository.findById(id).orElseThrow();
    }

    public Prompt ssavePrompt(Prompt prompt) {
        return promptRepository.save(prompt);
    }

}
