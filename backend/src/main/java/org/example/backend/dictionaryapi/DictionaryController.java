package org.example.backend.dictionaryapi;

import org.example.backend.dictionaryapi.models.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/translate")
public class DictionaryController {


    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService ponyService) {
        this.dictionaryService = ponyService;
    }

    @GetMapping
    public Response getResponse(@RequestParam String searchTerm, @RequestParam String targetLanguage) {
        return dictionaryService.getResponse(searchTerm, targetLanguage);
    }
}