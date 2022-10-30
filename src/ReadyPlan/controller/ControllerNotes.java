package ReadyPlan.controller;

import ReadyPlan.model.Notes.NotesSetUp;
import ReadyPlan.model.Reminders.RemindersPopUp;
import ReadyPlan.model.Security.Security;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import ReadyPlan.Main;
import ReadyPlan.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerNotes extends Controller implements Initializable {

    @FXML
    private TextField newNoteTitle;
    @FXML
    private Button addNoteBtn;
    @FXML
    private GridPane notesPane;
    @FXML
    private Button nextNotesBtn;
    @FXML
    private Button notesDeleteBtn1;
    @FXML
    private Button notesDeleteBtn2;
    @FXML
    private Button notesDeleteBtn3;
    @FXML
    private Button notesDeleteBtn4;
    @FXML
    private Button notesDeleteBtn5;
    @FXML
    private Button notesDeleteBtn6;
    @FXML
    private Button notesDeleteBtn7;
    @FXML
    private Button notesViewBtn1;
    @FXML
    private Button notesViewBtn2;
    @FXML
    private Button notesViewBtn3;
    @FXML
    private Button notesViewBtn4;
    @FXML
    private Button notesViewBtn5;
    @FXML
    private Button notesViewBtn6;
    @FXML
    private Button notesViewBtn7;
    @FXML
    private Button prevNotesBtn;
    @FXML
    private TextField notesSearchTF;
    private int notesListPage = 0;
    private int maxNotesListPage;
    private Button[] arrDeleteButton;
    private Button[] arrViewButton;
    private ObservableList<NotesSetUp> notesSetUpArrayList = FXCollections.observableArrayList(User.getNotesUser().getNotesDB());

    public void initializeUserData(){
        displayNotes();
        if (notesSetUpArrayList.size()<=7){
            nextNotesBtn.setDisable(true);
        }
        prevNotesBtn.setDisable(true);
        if (notesSetUpArrayList.size()%7==0){
            maxNotesListPage = notesSetUpArrayList.size()/7;
        }else{
            maxNotesListPage = notesSetUpArrayList.size()/7+1;
        }
    }

    public void clickNextNotes(ActionEvent event) {
        notesListPage++;
        displayNotes();
        prevNotesBtn.setDisable(false);
        if (notesListPage==maxNotesListPage-1){
            nextNotesBtn.setDisable(true);
        }
        if (notesListPage==0){
            prevNotesBtn.setDisable(true);
        }
    }

    public void clickPrevNotes(ActionEvent event) {
        notesListPage--;
        displayNotes();
        nextNotesBtn.setDisable(false);
        if (notesListPage==0){
            prevNotesBtn.setDisable(true);
        }
        if (notesListPage==maxNotesListPage-1){
            nextNotesBtn.setDisable(true);
        }
    }

    public void displayNotes(){
        notesPane.getChildren().clear();
        for (int i = 0;i<arrDeleteButton.length;i++){
            arrDeleteButton[i].setDisable(false);
            arrViewButton[i].setDisable(false);
        }
        notesPane.setStyle("-fx-font-family: 'Lucida Sans';-fx-font-size: 15px;");
        int rowToFillUp;
        System.out.println(notesSetUpArrayList.size()+"notes");
        if (notesSetUpArrayList.size()-notesListPage*7>=7){
            rowToFillUp = 7;
        }else{
            rowToFillUp = notesSetUpArrayList.size()-notesListPage*7;
        }
        for (int row = 0;row<rowToFillUp;row++) {
            //initializing data into labels
            Label tempTitle = new Label();

            tempTitle.setText(notesSetUpArrayList.get(notesListPage * 7 + row).getNoteTitle());
            tempTitle.setPrefWidth(820);
            tempTitle.setAlignment(Pos.CENTER);

            notesPane.add(tempTitle,0,row);
        }

        if (rowToFillUp<7){
            for (int i = rowToFillUp;i<7;i++){
                arrDeleteButton[i].setDisable(true);
                arrViewButton[i].setDisable(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        arrDeleteButton = new Button[]{notesDeleteBtn1, notesDeleteBtn2, notesDeleteBtn3, notesDeleteBtn4, notesDeleteBtn5, notesDeleteBtn6, notesDeleteBtn7};
        arrViewButton = new Button[]{notesViewBtn1,notesViewBtn2,notesViewBtn3,notesViewBtn4,notesViewBtn5,notesViewBtn6,notesViewBtn7};

        notesSearchTF.textProperty().addListener(e->{
            String searchText = notesSearchTF.getText();
            Pattern pattern = Pattern.compile(searchText);
            ObservableList<NotesSetUp> tempNotesArrayList = FXCollections.observableArrayList();
            for (int i = 0;i<User.getNotesUser().getNotesDB().size();i++){
                Matcher matcher = pattern.matcher(User.getNotesUser().getNotesDB().get(i).getNoteTitle());
                if (matcher.find()){
                    tempNotesArrayList.add(User.getNotesUser().getNotesDB().get(i));
                }
            }
            notesSetUpArrayList = tempNotesArrayList;
            notesListPage = 0;
            nextNotesBtn.setDisable(false);
            if (notesSetUpArrayList.size()<=7){
                nextNotesBtn.setDisable(true);
            }
            prevNotesBtn.setDisable(true);
            if (notesSetUpArrayList.size()%7==0){
                maxNotesListPage = notesSetUpArrayList.size()/7;
            }else{
                maxNotesListPage = notesSetUpArrayList.size()/7+1;
            }
            displayNotes();
        });
    }

    public void viewNoteAtIndex(int indexNo){
        String filePath = "src/ReadyPlan/notes/"+ Security.getCurrentUserNameFormat()+"_"+notesSetUpArrayList.get(indexNo).getNoteTitle()+".txt";
        try {
            ((ControllerViewNote) Main.changePage("/ReadyPlan/view/fxml/viewNotePage.fxml",
                    "/ReadyPlan/view/css/designViewNote.css")).initializeNotesData(notesSetUpArrayList.get(indexNo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayNotes();
    }

    public void deleteNoteAtIndex(int indexNo){
        User.getNotesUser().deleteNote(notesSetUpArrayList.get(indexNo));
        notesSetUpArrayList.remove(notesSetUpArrayList.get(indexNo));
        if (notesSetUpArrayList.size()%7==0){
            maxNotesListPage = notesSetUpArrayList.size()/7;
        }else{
            maxNotesListPage = notesSetUpArrayList.size()/7+1;
        }
        displayNotes();
    }

    public void addNewNote(ActionEvent event) {
        //something wrong with this method, displays the one just added twice but cannot view or delete
        String noteTitle = newNoteTitle.getText().trim();
        if (checkValidNoteTitle(noteTitle)){
            if (!isDuplicatedNote(noteTitle)){
                NotesSetUp newNote = new NotesSetUp(noteTitle);
                User.getNotesUser().addNote(newNote);
                notesSetUpArrayList.add(newNote);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Note created successfully!");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate Note");
                alert.setHeaderText("Note with note title already exists.");
                alert.setContentText("Please choose another note title to create a new note.");
                alert.showAndWait();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Note title naming error");
            alert.setHeaderText("Invalid note title");
            alert.setContentText("Please key in a valid task title.");
            alert.showAndWait();
        }
        if (notesSetUpArrayList.size()%7==0){
            maxNotesListPage = notesSetUpArrayList.size()/7;
        }else{
            maxNotesListPage = notesSetUpArrayList.size()/7+1;
        }
        if (notesListPage==maxNotesListPage-1){
            nextNotesBtn.setDisable(true);
        }else{
            nextNotesBtn.setDisable(false);
        }
        displayNotes();
    }

    private boolean isDuplicatedNote(String noteTitle){
        System.out.println(User.getNotesUser().getNotesDB());
        for (int i = 0;i<User.getNotesUser().getNotesDB().size();i++){
            if (User.getNotesUser().getNotesDB().get(i).getNoteTitle().equals(noteTitle)){
                return true;
            }
        }
        return false;
    }

    private boolean checkValidNoteTitle(String noteTitle){
        String regexDoNotMatch = "(CON|AUX|PRN|LST|COM[\\d]|LTP[\\d]|NUL| +|)";
        String regexToMatch = "(\\w|_| )+";
        return !noteTitle.matches(regexDoNotMatch)&&noteTitle.matches(regexToMatch);
    }

    public void clickNotesDelete1(ActionEvent event) { deleteNoteAtIndex(notesListPage*7); }

    public void clickNotesDelete2(ActionEvent event) { deleteNoteAtIndex(notesListPage*7+1); }

    public void clickNotesDelete3(ActionEvent event) { deleteNoteAtIndex(notesListPage*7+2); }

    public void clickNotesDelete4(ActionEvent event) { deleteNoteAtIndex(notesListPage*7+3); }

    public void clickNotesDelete5(ActionEvent event) { deleteNoteAtIndex(notesListPage*7+4); }

    public void clickNotesDelete6(ActionEvent event) { deleteNoteAtIndex(notesListPage*7+5); }

    public void clickNotesDelete7(ActionEvent event) { deleteNoteAtIndex(notesListPage*7+6); }

    public void clickNotesView1(ActionEvent event) { viewNoteAtIndex(notesListPage*7); }

    public void clickNotesView2(ActionEvent event) { viewNoteAtIndex(notesListPage*7+1); }

    public void clickNotesView3(ActionEvent event) { viewNoteAtIndex(notesListPage*7+2); }

    public void clickNotesView4(ActionEvent event) { viewNoteAtIndex(notesListPage*7+3); }

    public void clickNotesView5(ActionEvent event) { viewNoteAtIndex(notesListPage*7+4); }

    public void clickNotesView6(ActionEvent event) { viewNoteAtIndex(notesListPage*7+5); }

    public void clickNotesView7(ActionEvent event) { viewNoteAtIndex(notesListPage*7+6); }

}
