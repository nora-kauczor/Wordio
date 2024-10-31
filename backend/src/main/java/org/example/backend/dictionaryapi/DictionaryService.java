package org.example.backend.dictionaryapi;


import org.example.backend.dictionaryapi.models.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class DictionaryService {
    RestClient restClient;

    public DictionaryService(RestClient.Builder builder) {
        this.restClient = RestClient.create("https://od-api-sandbox.oxforddictionaries.com/");
    }

    public String getLanguageAbbreviation(String language) {
        Map<String, String> languages = new HashMap<>();
        languages.put("Spanish", "es");
        languages.put("Italian", "it");
        return languages.get(language);
    }


    public Response getWord(String searchTerm) {
//        String languageAbbrev = getLanguageAbbreviation(language);
//        System.out.println(languageAbbrev);
        Response response = this.restClient.get()
                .uri("api/v2/translations/en/es/" + searchTerm)
                // TODO Value annotation und in env var speichern
                .header("app_id", "4c36af21")
                .header("app_key", "4abde45e2bb06fc2c905bcdfab3152c6")
//                .header("app_id", "${api.id}") // frei vergeben
//                .header("app_key", "${api.key}")
                .retrieve().body(Response.class);
        assert response != null;
        return response;
    }

}

