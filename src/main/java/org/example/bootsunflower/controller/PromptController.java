package org.example.bootsunflower.controller;

import lombok.RequiredArgsConstructor;
import org.example.bootsunflower.dto.PromptForm;
import org.example.bootsunflower.service.GeminiService;
import org.example.bootsunflower.service.PromptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;
    private final GeminiService geminiService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("promptForm", new PromptForm(""));
        return "index";
    }

    @PostMapping
    public String submit(PromptForm promptForm, Model model) {
        String promptText = geminiService.generate(promptForm.text());
        model.addAttribute("promptText",promptText);
        return "index";
    }
}
