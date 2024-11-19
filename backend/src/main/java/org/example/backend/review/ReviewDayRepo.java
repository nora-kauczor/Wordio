package org.example.backend.review;


import org.example.backend.vocab.Language;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReviewDayRepo extends MongoRepository<ReviewDay, String> {

    ReviewDay getByDayAndUserIdAndLanguage(LocalDate day, String userId, Language language);


}
