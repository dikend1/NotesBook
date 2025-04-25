package org.example.manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.manager.model.Note;
import org.example.manager.model.Tag;
import org.example.manager.repo.KnowledgeBase;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
    private KnowledgeBase knowledgeBase =  new KnowledgeBase();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Knowledge Base Manager");

        VBox vBox = new VBox(10);
        vBox.setPadding(new javafx.geometry.Insets(15));

        TextArea noteTextArea = new TextArea();
        noteTextArea.setPromptText("Enter note text ...");

        TextField tagTextfield = new TextField();
        tagTextfield.setPromptText("Enter tags (comma separated)");

        Button addNoteButtom = new Button("Add Note");
        Button searchButtom = new Button("Search Notes by Tag");


        ListView<String> notesListView  = new ListView<>();

        addNoteButtom.setOnAction(e -> {
            String noteText = noteTextArea.getText();
            String tagsInput = tagTextfield.getText();

            if(!noteText.isEmpty() && !tagsInput.isEmpty()){

                String[] tagsArray = tagsInput.split(",");
                List<Tag> tags = new ArrayList<>();
                for (String tagName : tagsArray) {
                    tags.add(new Tag(tagName.trim()));
                }
                knowledgeBase.addNote(noteText, tags);
                noteTextArea.clear();
                tagTextfield.clear();
                updateNotesList(notesListView);
            }
        });

        searchButtom.setOnAction(e -> {
            String searchTag = tagTextfield.getText();
            if(!searchTag.isEmpty()){
                Tag tag = new Tag(searchTag.trim());
                List<Note> filteredNotes = knowledgeBase.searchNotesByTag(tag);
                updateNotesList(notesListView, filteredNotes);
            }
        });

        vBox.getChildren().addAll(noteTextArea, tagTextfield, addNoteButtom, searchButtom, notesListView);

        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateNotesList(ListView<String> notesListView){
        notesListView.getItems().clear();
        for(Note note : knowledgeBase.getNotes()){
            String displayText = "Note: " + note.getText() + " | Tags: " + note.getTags().stream().map(Tag::getName).collect(Collectors.joining(", "));
            notesListView.getItems().add(displayText);
        }
    }

    private void updateNotesList(ListView<String> notesListView, List<Note> filteredNotes){
        notesListView.getItems().clear();
        for (Note note : filteredNotes){
            String displayText = "Note: " + note.getText() + " | Tags: " + note.getTags().stream().map(Tag::getName).collect(Collectors.joining(", "));
            notesListView.getItems().add(displayText);
        }
    }

}