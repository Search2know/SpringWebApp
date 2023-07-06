package com.kovalchishin.SpringWebApp;
import com.kovalchishin.SpringWebApp.domain.FileService;
import com.kovalchishin.SpringWebApp.infrastructure.*;
import com.kovalchishin.SpringWebApp.infrastructure.File;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class MongoFileServiceTests {

    @Test
    public void testCreateFile() {
        MongoStore mockStore = Mockito.mock(MongoStore.class);
        MongoFileService fileService = new MongoFileService(mockStore);

        String description = "Test File";

        fileService.create(description);

        verify(mockStore, times(1)).save(any(File.class));
    }

    @Test
    public void testUpdateFile() {
        int position = 1;
        String newDescription = "Updated File";

        File existingFile = new File(position, "Old Description");

        MongoStore mockStore = Mockito.mock(MongoStore.class);
        when(mockStore.findFileByPosition(position)).thenReturn(existingFile);

        MongoFileService fileService = new MongoFileService(mockStore);

        fileService.update(position, newDescription);

        assertEquals(newDescription, existingFile.getDescription());
        verify(mockStore, times(1)).save(existingFile);
    }



    @Test
    public void testFindAllFiles() {
        List<File> files = new ArrayList<>();
        files.add(new File(1, "File 1"));
        files.add(new File(2, "File 2"));

        MongoStore mockStore = Mockito.mock(MongoStore.class);
        when(mockStore.findAll()).thenReturn(files);

        MongoFileService fileService = new MongoFileService(mockStore);

        List<File> result = fileService.findAll();

        assertEquals(files, result);
        verify(mockStore, times(1)).findAll();
    }


    @Test
    public void testNextPosition() {
        // Arrange
        List<File> files = new ArrayList<>();
        files.add(new File(1, "File 1"));
        files.add(new File(2, "File 2"));

        MongoStore mockStore = Mockito.mock(MongoStore.class);
        when(mockStore.findAll()).thenReturn(files);

        MongoFileService fileService = new MongoFileService(mockStore);

        int nextPosition = fileService.nextPosition();

        assertEquals(3, nextPosition);
    }

    @Test
    public void testCommandExecution_Show() throws Exception {
        String input = "show\nexit";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        BufferedReader mockReader = Mockito.mock(BufferedReader.class);
        when(mockReader.readLine()).thenReturn("show", "exit");

        MongoStore mockStore = Mockito.mock(MongoStore.class);
        List<File> files = new ArrayList<>();
        files.add(new File(1, "File 1"));
        files.add(new File(2, "File 2"));
        when(mockStore.findAll()).thenReturn(files);

        FileService fileService = new MongoFileService(mockStore);
        CommandRunner commandRunner = new CommandRunner(fileService);

        System.setIn(inputStream);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        commandRunner.run(String.valueOf(bufferedReader));

        verify(mockStore, times(1)).findAll();

    }

}