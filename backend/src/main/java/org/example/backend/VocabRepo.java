package org.example.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VocabRepo extends MongoRepository<Vocab, String> {
}
