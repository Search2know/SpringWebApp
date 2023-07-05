package com.kovalchishin.SpringWebApp.domain;

import com.kovalchishin.SpringWebApp.infrastructure.File;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This interface declares the methods that provides CRUD operations for
 * objects.
 */

@Component
public interface FileService {

    /**
     * This method creates a new File with Position and Description.
     * Position defines by getting size of File array from DB + 1.
     * If File array is empty -> Position = 1,
     * then saves in DB
     * @param description
     */
    void create(String description);

    /**
     * This method updates description of File
     * and saves in DB
     * @param position
     * @param newDescription
     */
    void update(int position, String newDescription);

    /**
     * This method deletes File by Position
     * and updates Position other Files
     * @param position
     */
    void delete(int position);

    /**
     * This method returns List of Files from DB
     * @return
     */
    List<File> findAll();

    /**
     * This method returns next Position
     * @return
     */
    int nextPosition();

}
