package ReadyPlan.controller;

import ReadyPlan.model.Reminders.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ReadyPlan.Main;
import ReadyPlan.model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerNewReminder extends Controller implements Initializable {

    @FXML
    private ComboBox customComboBox;
    @FXML
    private Spinner customSpinner;
    @FXML
    private Button newReminderBackBtn;
    @FXML
    private Button newReminderSaveBtn;
    @FXML
    private Label taskTitleLbl;
    @FXML
    private DatePicker reminderDatePicker;
    @FXML
    private TextArea reminderDescriptionTA;
    @FXML
    private ComboBox typeOfReminderCB;
    @FXML
    private TextField reminderTitleTF;

    /**
     * adds new reminder
     * @param event
     */
    public void clickSaveReminderBtn(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        RemindersPopUp newReminderPopUp = new RemindersPopUpDaily("","","01/01/2020","01/01/2020","");
        if (checkValidInputReminder(reminderTitleTF.getText().trim(),reminderDescriptionTA.getText())&&reminderDatePicker.getValue()!=null&&typeOfReminderCB.getValue()!=null){
            switch (typeOfReminderCB.getValue().toString()){
                case "NO REPEAT":
                    newReminderPopUp = new RemindersPopUpNoRepeat(reminderTitleTF.getText().trim(),
                            processString(reminderDescriptionTA.getText()),LocalDate.now().format(formatter),
                            reminderDatePicker.getValue().format(formatter),typeOfReminderCB.getValue().toString());
                    break;
                case "DAILY":
                    newReminderPopUp = new RemindersPopUpDaily(reminderTitleTF.getText().trim(),
                            processString(reminderDescriptionTA.getText()),LocalDate.now().format(formatter),
                            reminderDatePicker.getValue().format(formatter),typeOfReminderCB.getValue().toString());
                    break;
                case "WEEKLY":
                    newReminderPopUp = new RemindersPopUpWeekly(reminderTitleTF.getText().trim(),
                            processString(reminderDescriptionTA.getText()),LocalDate.now().format(formatter),
                            reminderDatePicker.getValue().format(formatter),typeOfReminderCB.getValue().toString());
                    break;
                case "MONTHLY":
                    newReminderPopUp = new RemindersPopUpMonthly(reminderTitleTF.getText().trim(),
                            processString(reminderDescriptionTA.getText()),LocalDate.now().format(formatter),
                            reminderDatePicker.getValue().format(formatter),typeOfReminderCB.getValue().toString());
                    break;
                case "YEARLY":
                    newReminderPopUp = new RemindersPopUpYearly(reminderTitleTF.getText().trim(),
                            processString(reminderDescriptionTA.getText()),LocalDate.now().format(formatter),
                            reminderDatePicker.getValue().format(formatter),typeOfReminderCB.getValue().toString());
                    break;
                case "CUSTOM":
                    if (customComboBox.getValue()!=null){
                        newReminderPopUp = new RemindersPopUpCustom(reminderTitleTF.getText().trim(),
                                processString(reminderDescriptionTA.getText()),LocalDate.now().format(formatter),
                                reminderDatePicker.getValue().format(formatter),
                                customSpinner.getValue().toString()+" "+customComboBox.getValue().toString());
                    }else{
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Custom field not selected.");
                        alert.setContentText("Please key in all fields in custom settings if reminder is of custom type");
                        alert.showAndWait();
                    }
                    break;
                default:
                    System.out.println("Unexpected value "+typeOfReminderCB.getValue().toString());
            }
            if (customComboBox.isDisabled()||customComboBox.getValue()!=null){
                if (!User.getRemindersUser().checkDuplicateReminder(newReminderPopUp)){
                    User.getRemindersUser().addReminders(newReminderPopUp);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Reminder created successfully!");
                    reminderTitleTF.clear();
                    reminderDescriptionTA.clear();
                    reminderDatePicker.setValue(null);
                    typeOfReminderCB.setValue(null);
                    customComboBox.setValue(null);
                }else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Duplicate Reminder");
                    alert.setHeaderText("Reminder has already been created.");
                    alert.setContentText("Please change details of reminder.");
                }
                alert.showAndWait();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to create reminder");
            alert.setHeaderText("Reminder title, description or date is not in correct format.");
            alert.setContentText("Please only include word characters for reminder title. \n" +
                    "Please fill in all fields"+
                    "\nPlease do not use commas in description.");
            alert.showAndWait();
        }

        //add back button or exit out of pop up window after pressing save
    }

    private String processString(String inputTextArea){
        return inputTextArea.replaceAll("\\n","\\\\n");
    }

    private String decodeString(String inputTextArea){
        return inputTextArea.replaceAll("\\\\n","\\n");
    }


    private boolean checkValidInputReminder(String reminderTitle, String reminderDescription){
        String titleRegex = "(\\w| )+";
        String descriptionRegex = "(\\n|[.[^,]])*";
        return reminderTitle.matches(titleRegex)&&reminderDescription.matches(descriptionRegex);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reminderDescriptionTA.setWrapText(true);
        ObservableList<String> typeOfReminder = FXCollections.observableArrayList("NO REPEAT","DAILY","WEEKLY","MONTHLY","YEARLY","CUSTOM");
        typeOfReminderCB.setItems(typeOfReminder);
        ObservableList<String> typeOfRepeat = FXCollections.observableArrayList("DAYS","WEEKS","MONTHS","YEARS");
        customComboBox.setItems(typeOfRepeat);
        SpinnerValueFactory<Integer> numberSpinnerValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,60);
        customSpinner.setValueFactory(numberSpinnerValues);
        customComboBox.setDisable(true);
        customSpinner.setDisable(true);
        typeOfReminderCB.valueProperty().addListener(e->{
            if (typeOfReminderCB.getValue()!=null){
                if (typeOfReminderCB.getValue().toString().equals("CUSTOM")){
                    customSpinner.setDisable(false);
                    customComboBox.setDisable(false);
                }else{
                    customComboBox.setDisable(true);
                    customSpinner.setDisable(true);
                }
            }
        });
    }

    public void clickBackReminderBtn(ActionEvent event) {
        try {
            ((ControllerReminders)Main.changePage("/ReadyPlan/view/fxml/remindersPage.fxml","/ReadyPlan/view/css/designReminders.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
