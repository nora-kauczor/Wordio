package org.example.backend.vocab;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Vocab {
    @Id private String _id;
    private String word;
    private String translation;
    private String info;
    private Language language;
    private List<LocalDate> reviewDates;
    private String createdBy;



    public static List<LocalDate> generateDates(LocalDate date) {
        List<LocalDate> reviewDates = new ArrayList<>();
        int daysToAdd = 0;
        for (int i = 1; i < 20; i++) {
            LocalDate newDate = date.plusDays(daysToAdd);
            reviewDates.add(newDate);
            daysToAdd += i;
        }
        return reviewDates;
    }



}
