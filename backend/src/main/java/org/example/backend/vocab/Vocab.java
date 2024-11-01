package org.example.backend.vocab;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Vocab {
    @Id private String _id;
    private String word;
    private String translation;
    private String info;
    private Language language;
    private Map<String, List<LocalDate>> datesPerUser;
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

/*
datesPerUser: {
  "user1": ["2024-11-01", "2024-11-02"],
  "user2": ["2024-12-25"]
}
 */

}
