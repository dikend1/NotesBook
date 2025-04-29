package org.example.manager.model;

import java.io.Serializable;
import java.util.List;

public class Note implements Serializable {
    private String text;
    private List<Tag> tags;

    public Note(String text, List<Tag> tags) {
        this.text = text;
        this.tags = tags;
    }

    //create - Создание заметки
    public static Note createNote(String text, List<Tag> tags) {
        return new Note(text, tags);
    }

    //read - Чтение текста
    public String getText() {
        return text;
    }

    //read - Чтение текста
    public List<Tag> getTags() {
        return tags;
    }
    // Delete - Удаление заметки
    public void deleteNote() {
        this.text = "";
        this.tags.clear();
    }

}
