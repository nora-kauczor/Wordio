package org.example.backend.calendar;

import java.util.Arrays;
import java.util.Objects;

public record Month
        (String yearMonthName,
         VocabIdsOfDate[][] vocabIdsOfMonth) {

    @Override
    public String toString() {
        return "Month{" +
               "vocabIdsOfMonth=" + Arrays.toString(vocabIdsOfMonth) +
               ", yearMonthName='" + yearMonthName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Month month = (Month) o;
        return Objects.equals(yearMonthName, month.yearMonthName) && Objects.deepEquals(vocabIdsOfMonth, month.vocabIdsOfMonth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(yearMonthName, Arrays.deepHashCode(vocabIdsOfMonth));
    }
}
