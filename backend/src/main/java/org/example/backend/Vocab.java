package org.example.backend;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class Vocab {
    @Id String _id;
    String word;
    String translation;
    String info;
    String language;
    List<LocalDate> reviewDates;

    public List<LocalDate> generateDates(LocalDate date) {
        List<LocalDate> reviewDates = new ArrayList();
        int daysToAdd = 0;
        for (int i = 1; i < 100; i++) {
            LocalDate newDate = date.plusDays(daysToAdd);
            reviewDates.add(newDate);
            daysToAdd += i;
        }
        return reviewDates;
    }



}
