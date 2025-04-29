package org.example.manager.model;


import java.io.Serializable;

public class Tag implements Serializable {
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public static Tag createTag(String name) {
        return new Tag(name);
    }

    public String getName() {
        return name;
    }

    public void updateTag(String newName) {
        this.name = newName;
    }

    public void deleteTag() {
        this.name = null;
    }
}
