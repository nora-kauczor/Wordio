package org.example.backend.dictionaryapi;

import org.example.backend.dictionaryapi.models.Response;
import org.springframework.http.HttpHeaders;
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
        return languages.get(language);
    }

    public Response getResponse(String searchTerm, String targetLanguage) {
        String languageAbbreviation = getLanguageAbbreviation(targetLanguage);
        HttpHeaders headers = new HttpHeaders();
        // TODO Value annotation und in env var speichern
//        headers.set("app_id", "4c36af21");
//        headers.set("app_key", "4abde45e2bb06fc2c905bcdfab3152c6");
//        HttpEntity<String> entity = new HttpEntity<>(headers);
        String uri = "api/v2/translations/en/" + languageAbbreviation + "/" + searchTerm;
//        ResponseEntity<String> response = restClient.exchange(uri, HttpMethod.GET, entity, String.class);

//        return response.getBody();

        org.example.backend.dictionaryapi.models.Response response = this.restClient.get()
                .uri(uri)
                .header("app_id", "4c36af21")
                .header("app_key", "4abde45e2bb06fc2c905bcdfab3152c6")
                .retrieve().body(Response.class);
        assert response != null;
        return response;
    }
}

