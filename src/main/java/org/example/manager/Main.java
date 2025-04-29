package org.example.manager;

import javafx.application.Application;
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
        Button saveButtom = new Button("Save Notes");
        Button loadButtom = new Button("Load Notes");
        Button undoButtom = new Button("Undo");
        Button redoButtom = new Button("Redo");
        Button deleteButtom = new Button("Delete Note");


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
            System.out.println("Searching for tag: " + searchTag);

            if(!searchTag.isEmpty()){
                Tag tag = new Tag(searchTag.trim());
                List<Note> filteredNotes = knowledgeBase.searchNotesByTag(tag);
                if(filteredNotes.isEmpty()){
                    System.out.println("No notes found for the tag: " + searchTag);
                }
                updateNotesList(notesListView, filteredNotes);
            }else {
                updateNotesList(notesListView, knowledgeBase.getNotes());
            }
        });


        saveButtom.setOnAction(e -> {
            try{
                knowledgeBase.saveNotesToFile("notes.dat");
                showAlert("Success", "Notes saved successfully");
            }catch (IOException ex){
                showAlert("Error", "Error saving notes to file");
            }
        });

        loadButtom.setOnAction(e -> {
            knowledgeBase.loadNotesFromFile("notes.dat");
            updateNotesList(notesListView);
            showAlert("Success", "Notes loaded successfully");
        });

        undoButtom.setOnAction(e -> {
            Note lastNote = knowledgeBase.undo();
            if (lastNote != null) {
                updateNotesList(notesListView);
            }
        });

        redoButtom.setOnAction(e -> {
            Note redoNote = knowledgeBase.redo();
            if (redoNote != null){
                updateNotesList(notesListView);
            }
        });

        deleteButtom.setOnAction(e -> {
            int selectedIndex = notesListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1){
                knowledgeBase.deleteNote(selectedIndex);
                updateNotesList(notesListView);
            }
        });


        vBox.getChildren().addAll(noteTextArea, tagTextfield, addNoteButtom, searchButtom, notesListView,saveButtom,loadButtom,undoButtom,redoButtom,deleteButtom);

        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateNotesList(ListView<String> notesListView){
        notesListView.getItems().clear();
        List<Note> allNotes = knowledgeBase.getNotes();

        if(allNotes.isEmpty()){
            notesListView.getItems().add("z");
        }else {
            for (Note note : allNotes) {
                String displayText = "Note: " + note.getText() + " | Tags: " +
                        note.getTags().stream().map(Tag::getName).collect(Collectors.joining(", "));
                notesListView.getItems().add(displayText);
            }
        }
    }

    private void updateNotesList(ListView<String> notesListView, List<Note> filteredNotes){
        notesListView.getItems().clear();
        if(filteredNotes.isEmpty()){
            notesListView.getItems().add("No notes found");
        }
        else {
            for (Note note : filteredNotes) {
                String displayText = "Note: " + note.getText() + " | Tags: " +
                        note.getTags().stream().map(Tag::getName).collect(Collectors.joining(", "));
                notesListView.getItems().add(displayText);
            }
        }
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}