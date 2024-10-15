package org.example.backend;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public record Vocab(@Id String _id,
                    String word,
                    String translation,
                    String info,
                    String language,
                    List<Date> reviewDates) {
}
