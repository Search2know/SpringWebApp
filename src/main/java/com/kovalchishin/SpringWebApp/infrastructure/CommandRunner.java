package com.kovalchishin.SpringWebApp.infrastructure;

import com.kovalchishin.SpringWebApp.domain.FileService;
import org.springframework.boot.CommandLineRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class CommandRunner implements CommandLineRunner {

    private final FileService fileService;

    public CommandRunner(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello\n");
        System.out.print("\nType the desired command:\n create file [text to add]\n show\n update [position of file] [text description updating]\n delete [position of file] \n exit\n");

        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            var done = false;
            do {
                var cmd = reader.readLine();
                if (cmd.equals("exit")) {
                    done = true;
                } else if (cmd.equals("show")) {
                    fileService.findAll().forEach(System.out::println);
                } else {
                    var pattern = Pattern.compile("(\\w+)\\s(\\d+|\\w+)(\\s(.*))?");
                    var matcher = pattern.matcher(cmd);
                    if (matcher.find()) {
                        try {
                            if (matcher.group(1).equals("create")) {
                                fileService.create(matcher.group(4));
                            } else if (Integer.parseInt(matcher.group(2)) <= fileService.nextPosition() - 1) {
                                if (matcher.group(1).equals("update")) {
                                    fileService.update(Integer.parseInt(matcher.group(2)), matcher.group(4));
                                }
                                if (matcher.group(1).equals("delete")) {
                                    fileService.delete(Integer.parseInt(matcher.group(2)));
                                }
                            } else {
                                throw new IllegalArgumentException("Provided line number is out of bounds");
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Improper command");
                    }
                }
            } while (!done);
        } catch (IOException e) {
            System.out.println("Wrong input command.");
        }
    }
}
