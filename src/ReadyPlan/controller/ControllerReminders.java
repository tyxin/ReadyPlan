package ReadyPlan.controller;

import ReadyPlan.model.Reminders.ReminderSortByCreateDate;
import ReadyPlan.model.Reminders.ReminderSortByReminderDate;
import ReadyPlan.model.Reminders.ReminderSortByType;
import ReadyPlan.model.Reminders.RemindersPopUp;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import ReadyPlan.Main;
import ReadyPlan.model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerReminders extends Controller implements Initializable {
    @FXML
    private GridPane remindersPane;
    @FXML
    private TextField remindersSearchTF;
    @FXML
    private Button prevRemindersBtn;
    @FXML
    private Button nextRemindersBtn;
    @FXML
    private ComboBox<String> sortByComboBox;
    @FXML
    private Button rpDeleteBtn1;
    @FXML
    private Button rpDeleteBtn2;
    @FXML
    private Button rpDeleteBtn3;
    @FXML
    private Button rpDeleteBtn4;
    @FXML
    private Button rpDeleteBtn5;
    @FXML
    private Button rpDeleteBtn6;
    @FXML
    private Button rpDeleteBtn7;
    @FXML
    private Button reminderAddNewBtn;
    private Button[] arrDeleteButton;
    //also indexed 0
    private int reminderPageNo = 0;
    private int maxReminderPage;

    private ObservableList<RemindersPopUp> remindersPopUpArrayList = FXCollections.observableArrayList(User.getRemindersUser().getRemindersPopUpDB());

    public void clickPrevReminders(ActionEvent event) {
        reminderPageNo--;
        displayReminders();
        nextRemindersBtn.setDisable(false);
        if (reminderPageNo==0){
            prevRemindersBtn.setDisable(true);
        }
        if (reminderPageNo==maxReminderPage-1){
            nextRemindersBtn.setDisable(true);
        }
    }

    public void clickNextReminders(ActionEvent event) {
        reminderPageNo++;
        displayReminders();
        prevRemindersBtn.setDisable(false);
        if (reminderPageNo==maxReminderPage-1){
            nextRemindersBtn.setDisable(true);
        }
        if (reminderPageNo==0){
            prevRemindersBtn.setDisable(true);
        }
    }

    public void clickSort(ActionEvent event) {
        String sortBy = sortByComboBox.getValue();
        if (sortBy.equals("REMINDER DATE")){
            Collections.sort(remindersPopUpArrayList,new ReminderSortByReminderDate());
        }else if(sortBy.equals("DATE CREATED")){
            Collections.sort(remindersPopUpArrayList,new ReminderSortByCreateDate());
        }else{
            Collections.sort(remindersPopUpArrayList,new ReminderSortByType());
        }
        displayReminders();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sortByList = FXCollections.observableArrayList("REMINDER DATE","DATE CREATED","TYPE OF REMINDER");
        sortByComboBox.setItems(sortByList);
        arrDeleteButton = new Button[]{rpDeleteBtn1, rpDeleteBtn2, rpDeleteBtn3, rpDeleteBtn4, rpDeleteBtn5, rpDeleteBtn6, rpDeleteBtn7};
        remindersSearchTF.textProperty().addListener(e->{
            String searchText = remindersSearchTF.getText();
            Pattern pattern = Pattern.compile(searchText);
            ObservableList<RemindersPopUp> tempReminderArrayList = FXCollections.observableArrayList();
            for (int i = 0;i<User.getRemindersUser().getRemindersPopUpDB().size();i++){
                Matcher matcher = pattern.matcher(User.getRemindersUser().getRemindersPopUpDB().get(i).getTitle());
                if (matcher.find()){
                    tempReminderArrayList.add(User.getRemindersUser().getRemindersPopUpDB().get(i));
                }
            }
            remindersPopUpArrayList = tempReminderArrayList;
            reminderPageNo = 0;
            nextRemindersBtn.setDisable(false);
            if (remindersPopUpArrayList.size()<=7){
                nextRemindersBtn.setDisable(true);
            }
            prevRemindersBtn.setDisable(true);
            if (remindersPopUpArrayList.size()%7==0){
                maxReminderPage = remindersPopUpArrayList.size()/7;
            }else{
                maxReminderPage = remindersPopUpArrayList.size()/7+1;
            }
            displayReminders();
        });
/*
        remindersPopUpArrayList.addListener((ListChangeListener.Change<? extends RemindersPopUp> change) ->{
            System.out.println("change detected");
            while(change.next()){
                if (change.wasRemoved()){
                    System.out.println("happened");
                    displayReminders();
                }
            }
        });

 */


    }

    public void clickReminderDelete1(ActionEvent event) { deleteReminderAtIndex(reminderPageNo*7); }

    public void clickReminderDelete2(ActionEvent event) { deleteReminderAtIndex(reminderPageNo*7+1); }

    public void clickReminderDelete3(ActionEvent event) { deleteReminderAtIndex(reminderPageNo*7+2); }

    public void clickReminderDelete4(ActionEvent event) { deleteReminderAtIndex(reminderPageNo*7+3); }

    public void clickReminderDelete5(ActionEvent event) { deleteReminderAtIndex(reminderPageNo*7+4); }

    public void clickReminderDelete6(ActionEvent event) { deleteReminderAtIndex(reminderPageNo*7+5); }

    public void clickReminderDelete7(ActionEvent event) { deleteReminderAtIndex(reminderPageNo*7+6); }

    public void deleteReminderAtIndex(int indexNo){
        User.getRemindersUser().deleteReminder(remindersPopUpArrayList.get(indexNo));
        remindersPopUpArrayList.remove(remindersPopUpArrayList.get(indexNo));
        if (remindersPopUpArrayList.size()%7==0){
            maxReminderPage = remindersPopUpArrayList.size()/7;
        }else{
            maxReminderPage = remindersPopUpArrayList.size()/7+1;
        }
        displayReminders();
    }

    public void clickAddReminder(ActionEvent event) {
        try {
            Main.changePage("/ReadyPlan/view/fxml/createNewReminder.fxml","/ReadyPlan/view/css/designNewReminder.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (remindersPopUpArrayList.size()%7==0){
            maxReminderPage = remindersPopUpArrayList.size()/7;
        }else{
            maxReminderPage = remindersPopUpArrayList.size()/7+1;
        }
        System.out.println(maxReminderPage);
    }

    public void displayReminders(){
        //reset all variables
        remindersPane.getChildren().clear();
        for (int i = 0;i<arrDeleteButton.length;i++){
            arrDeleteButton[i].setDisable(false);
        }
        remindersPane.setStyle("-fx-font-family: 'Lucida Sans';-fx-font-size: 15px;");
        int rowToFillUp;
        if (remindersPopUpArrayList.size()-reminderPageNo*7>=7){
            rowToFillUp = 7;
        }else{
            rowToFillUp = remindersPopUpArrayList.size()-reminderPageNo*7;
        }
        for (int row = 0;row<rowToFillUp;row++) {
            //initializing data into labels
            Label tempTitle = new Label();
            Label tempDescription = new Label();
            Label tempDateCreated = new Label();
            Label tempDateReminder = new Label();
            Label tempTypeReminder = new Label();

            tempTitle.setText(remindersPopUpArrayList.get(reminderPageNo * 7 + row).getTitle());
            tempTitle.setPrefWidth(200);
            tempTitle.setAlignment(Pos.CENTER);

            tempDescription.setText(remindersPopUpArrayList.get(reminderPageNo * 7 + row).getDescription());
            tempDescription.setPrefWidth(170);
            tempDescription.setAlignment(Pos.CENTER);

            tempDateCreated.setText(remindersPopUpArrayList.get(reminderPageNo * 7 + row).getDateCreated());
            tempDateCreated.setPrefWidth(147.5);
            tempDateCreated.setAlignment(Pos.CENTER);

            tempDateReminder.setText(remindersPopUpArrayList.get(reminderPageNo * 7 + row).getDateForReminder());
            tempDateReminder.setPrefWidth(147.5);
            tempDateReminder.setAlignment(Pos.CENTER);

            tempTypeReminder.setText(remindersPopUpArrayList.get(reminderPageNo * 7 + row).getTypeOfReminder());
            tempTypeReminder.setPrefWidth(180);
            tempTypeReminder.setAlignment(Pos.CENTER);
            Label[] dataRow = {tempTitle, tempDescription, tempDateCreated, tempDateReminder, tempTypeReminder};
            for (int column = 0; column < 5; column++) {
                remindersPane.add(dataRow[column], column, row);
            }
        }
        if (rowToFillUp<7){
            for (int i = rowToFillUp;i<7;i++){
                arrDeleteButton[i].setDisable(true);
            }
        }
    }

    public void initializeUserData(){
        displayReminders();
        if (remindersPopUpArrayList.size()<=7){
            nextRemindersBtn.setDisable(true);
        }
        prevRemindersBtn.setDisable(true);
        if (remindersPopUpArrayList.size()%7==0){
            maxReminderPage = remindersPopUpArrayList.size()/7;
        }else{
            maxReminderPage = remindersPopUpArrayList.size()/7+1;
        }
    }
}
