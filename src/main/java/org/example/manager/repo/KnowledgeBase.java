package org.example.manager.repo;

import org.example.manager.FileManager;
import org.example.manager.model.Note;
import org.example.manager.model.Tag;

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

    public void displayNotes(){
        for (Note note : notes) {
            System.out.println("Note: " + note.getText());
            for(Tag tag : note.getTags()){
                System.out.println("Tag: " + tag.getName());
            }
        }
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

    public void updateNoteText(int index, String newText){
        if (index >= 0 && index < notes.size()) {
            notes.get(index).updateText(newText);
            history.saveState(notes.get(index));
        }
    }

    public void deleteNote(int index){
        if (index >= 0 && index < notes.size()) {
            notes.get(index).deleteNote();
            notes.remove(index);
        }
    }


    public void addTag(String tagName) {
        Tag newTag = Tag.createTag(tagName);
        tags.add(newTag);
    }

    public void updateTag(int index, String newTagName){
        if (index >= 0 && index < tags.size()) {
            tags.get(index).updateTag(newTagName);
        }
    }

    public void deleteTag(int index){
        if (index >= 0 && index < tags.size()) {
            Tag  tag = tags.get(index);
            removeTagFromNotes(tag);
            tag.deleteTag();
            tags.remove(index);
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

    public void removeTagFromNotes(Tag tag){
        for (Note note : notes) {
            note.removeTag(tag);
        }
    }
}
