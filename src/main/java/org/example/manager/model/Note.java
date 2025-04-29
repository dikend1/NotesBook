package org.example.manager.model;
import org.example.manager.repo.History;

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


    //Update - Обновление текста
    public void updateText(String text) {
        this.text = text;
    }

    // Delete - Удаление заметки
    public void deleteNote() {
        this.text = "";
        this.tags.clear();
    }
    // Метод для добавления тега к заметке
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

}
