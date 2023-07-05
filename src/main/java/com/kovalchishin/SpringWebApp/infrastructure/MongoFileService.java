package com.kovalchishin.SpringWebApp.infrastructure;

import com.kovalchishin.SpringWebApp.domain.FileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This service class saves,deletes,updates objects
 * to MongoDB database.
 */

@Service
public class MongoFileService implements FileService {

    private final MongoStore target;

    public MongoFileService(MongoStore target) {
        this.target = target;
    }

    @Override
    public void create(String description) {
        target.save(new File(nextPosition(), description));
    }

    @Override
    public void update(int position, String newDescription) {
        File updated = target.findFileByPosition(position);
        updated.setDescription(newDescription);
        target.save(updated);
    }

    @Override
    public void delete(int position) {
        target.delete(target.findFileByPosition(position));

        File update;
        Matcher matcherLine;
        int currentPos;
        int requiredPos;

        var pattern = Pattern.compile("\\b(position=)\\b(\\d+)");
        ArrayList<File> list = new ArrayList<File>(target.findAll());

        for (int i = 0; i < list.size(); i++) {
            matcherLine = pattern.matcher(String.valueOf(list.get(i)));
            requiredPos = i + 1;
            if (matcherLine.find()) {
                currentPos = Integer.parseInt(matcherLine.group(2));
                if (currentPos != requiredPos) {
                    update = target.findFileByPosition(currentPos);
                    update.setPosition(requiredPos);
                    target.save(update);
                }
            } else break;
        }
    }

    @Override
    public List<File> findAll() {
        return target.findAll();
    }

    @Override
    public int nextPosition() {
        ArrayList<File> list = new ArrayList<File>(target.findAll());

        if (!list.isEmpty()) {
            return list.size() + 1;
        } else return 1;
    }
}