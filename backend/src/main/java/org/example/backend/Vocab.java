package org.example.backend;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

public record Vocab(@Id String _id,
                    String word,
                    String translation,
                    String info,
                    String language,
                    List<LocalDate> reviewDates) {
}
