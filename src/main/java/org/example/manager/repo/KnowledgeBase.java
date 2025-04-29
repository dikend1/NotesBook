package org.example.manager.repo;

import org.example.manager.FileManager;
import org.example.manager.model.Note;
import org.example.manager.model.Tag;
import org.example.manager.Main;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Stack;


public class KnowledgeBase {
    private List<Note> notes;
    private List<Tag> tags;
    private History history;
    private Stack<Note> redoStack;

    public KnowledgeBase() {
        this.notes = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.history = new History();
        this.redoStack = new Stack<>();
    }

    public Note undo(){
        Note lastNote = history.undo();
        if(lastNote != null){
            redoStack.push(lastNote);
        }
        return lastNote;
    }

    public void addNote(String text, List<Tag> tags) {
        Note newNote = Note.createNote(text, tags);
        notes.add(newNote);
        history.saveState(newNote);
    }



    public void saveNotesToFile(String fileName) throws IOException {
        try {
            FileManager.saveNotesToFile(notes, fileName);
            System.out.println("Notes saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving notes to file: " + e.getMessage());
        }
    }

    public void loadNotesFromFile(String fileName) {
        try {
            notes = FileManager.loadNotesFromFile(fileName);  // Загружаем заметки
            System.out.println("Notes loaded successfully.");
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Error loading notes from file: " + ex.getMessage());
        }
    }



    public void deleteNote(int index){
        if (index >= 0 && index < notes.size()) {
            notes.get(index).deleteNote();
            notes.remove(index);
        }
        try {
            FileManager.saveNotesToFile(notes, "notes.dat");
            System.out.println("Note deleted and changes saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving notes to file after deletion: " + e.getMessage());
        }

    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Note> searchNotesByTag(Tag tag){
        List<Note> result = new ArrayList<>();
        for (Note note : notes) {
            if (note.getTags().contains(tag)){
                result.add(note);
            }
        }
        return result;
    }

    public Note redo(){
        if(!redoStack.isEmpty()){
            Note redoNote = redoStack.pop();
            notes.add(redoNote);
            history.saveState(redoNote);
            return redoNote;
        }
        return null;
    }

}
