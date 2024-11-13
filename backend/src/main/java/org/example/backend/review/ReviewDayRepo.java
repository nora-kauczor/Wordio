package org.example.backend.review;


import org.example.backend.vocab.Language;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReviewDayRepo extends MongoRepository<ReviewDay, String> {

    Optional<ReviewDay> getByDayAndUserIdAndLanguage(LocalDate day, String userId, Language language);
}
