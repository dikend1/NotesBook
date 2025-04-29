package org.example.manager;

import org.example.manager.model.Note;

import java.io.*;
import java.util.List;

public class FileManager {
    public static void saveNotesToFile(List<Note> notes, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            System.out.println("Saving notes to " + fileName);
            out.writeObject(notes);
        }catch (IOException e){
            System.err.println("Error saving notes to file: " + e.getMessage());
            throw e;
        }
    }

    public static List<Note> loadNotesFromFile(String fileName) throws IOException,ClassNotFoundException {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            System.out.println("Loading notes from " + fileName);
            return (List<Note>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Error loading notes from file: " + e.getMessage());
            throw e;
        }
    }

}
