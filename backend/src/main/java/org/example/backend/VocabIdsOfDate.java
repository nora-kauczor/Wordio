package org.example.backend;



import java.util.List;


public record VocabIdsOfDate(
        String date,
        List<String> vocabIds
) {


}
