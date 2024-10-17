package org.example.backend;

import java.time.YearMonth;
import java.util.List;

public record Month(YearMonth yearMonth, List<Vocab> vocabsOfMonth) {
}
