package com.kovalchishin.SpringWebApp.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * This repository provides CRUD operations for
 * objects.
 */

@Repository
public interface MongoStore extends MongoRepository<File, String> {

    @Query(value = "{position : ?0}")
    File findFileByPosition(int position);


}