package com.kovalchishin.SpringWebApp.infrastructure;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class File {

    @Id
    private String id;
    private int position;
    private String description;

    public File(int position, String description) {
        this.position = position;
        this.description = description;
    }
}