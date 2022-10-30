package ReadyPlan.controller;

import ReadyPlan.model.Notes.NotesSetUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ReadyPlan.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerViewNote extends Controller implements Initializable {
    @FXML
    private Button viewNoteBackBtn;
    @FXML
    private TextArea notesContentTA;
    @FXML
    private TextField notesTitleTF;
    @FXML
    private Button viewNoteSaveBtn;
    private NotesSetUp note;

    public void initializeNotesData(NotesSetUp note){
        this.note = note;
        notesTitleTF.setText(note.getNoteTitle());
        notesTitleTF.setEditable(false);
        notesContentTA.setText(note.getNoteContent());
    }

    public void clickViewNoteBack(ActionEvent event) {
        try {
            ((ControllerNotes) Main.changePage("/ReadyPlan/view/fxml/notesPage.fxml","/ReadyPlan/view/css/designNotes.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickNoteSave(ActionEvent event) {
        String updatedContent = notesContentTA.getText();
        note.updateNoteContent(updatedContent);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Note saved successfully");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notesContentTA.setWrapText(true);
    }

}
