package org.example.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Vocab {
    @Id String _id;
    String word;
    String translation;
    String info;
    String language;
    List<String> reviewDates;

    public List<String> generateDates(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E-dd-MM-yyyy");
        List<String> reviewDates = new ArrayList();
        int daysToAdd = 0;
        for (int i = 1; i < 20; i++) {
            LocalDate newDate = date.plusDays(daysToAdd);
            reviewDates.add(newDate.format(formatter));
            daysToAdd += i;
        }
        return reviewDates;
    }



}
