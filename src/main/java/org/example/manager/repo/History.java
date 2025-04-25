package org.example.manager.repo;

import org.example.manager.model.Note;

import java.util.Stack;

public class History {
    private Stack<History> history = new Stack<>();

    public void saveState(Note note) {
        history.push(note);
    }

    public Note undo(){
        if(!history.isEmpty()){
            return history.pop().undo();
        }
        return null;
    }


}
