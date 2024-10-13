package org.example.backend;

import java.util.Date;
import java.util.List;

public record Vocab(String id,
                    String word,
                    String translation,
                    String info,
                    String language,
                    List<Date> reviewDates) {
}
