package org.example.backend.dictionaryapi.models;

import java.util.List;

public record Response(
       String id,
List<Result> result,
       String word
) {
}
