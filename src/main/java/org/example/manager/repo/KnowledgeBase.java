package org.example.manager.repo;

import org.example.manager.model.Note;
import org.example.manager.model.Tag;

import java.util.List;
import java.util.ArrayList;

public class KnowledgeBase {
    private List<Note> notes;
    private List<Tag> tags;
    private History history;

    public KnowledgeBase() {
        this.notes = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.history = new History();
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
            tags.get(index).deleteTag();
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
}
