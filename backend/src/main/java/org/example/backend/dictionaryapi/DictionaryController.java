package org.example.backend.dictionaryapi;


import org.example.backend.dictionaryapi.models.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translate")
public class DictionaryController {


    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public Response getWord(
//            @RequestParam String language,
            @RequestParam String q){
        return dictionaryService.getWord( q);
    }

}