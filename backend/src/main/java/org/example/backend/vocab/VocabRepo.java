package org.example.backend.vocab;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabRepo extends MongoRepository<Vocab, String> {
}
