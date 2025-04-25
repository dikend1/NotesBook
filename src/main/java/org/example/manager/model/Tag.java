package org.example.manager.model;


public class Tag {
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
