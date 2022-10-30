package ReadyPlan.controller;

import ReadyPlan.model.Tasks.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ReadyPlan.Main;
import ReadyPlan.model.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerTaskList extends Controller implements Initializable {

    @FXML
    private Button howTaskCompletedInfoBtn;
    @FXML
    private Button viewCompleteTaskBtn;
    @FXML
    private Spinner customTaskSpinner;
    @FXML
    private ComboBox customTaskComboBox;
    @FXML
    private TextField taskTitleTF;
    @FXML
    private ComboBox<String> typeOfTaskComboBox;
    @FXML
    private VBox taskVBox;
    @FXML
    private Button addTaskBtn;
    @FXML
    private Button saveTaskBtn;
    @FXML
    private Button viewTaskBackBtn;
    @FXML
    private ScrollPane taskListScrollPane;
    @FXML
    private Label taskListTitle;
    @FXML
    private DatePicker taskDatePicker;
    @FXML
    private TextArea taskDescriptionTA;
    @FXML
    private Button deleteTaskBtn;
    private String taskTitle;
    private TasksSetUpTemplate taskSelected;
    private ArrayList<Label> tasksInVBox = new ArrayList<Label>();
    private int currentTaskIndex = -1;
    private final String notClickedStyle = "-fx-font-family: 'Lucida Sans';-fx-font-size: 22px;";
    private final String clickedStyle = "-fx-font-family: 'Lucida Sans';-fx-font-size: 22px;-fx-background-color: #c2d9ff";
    private int indexOfTaskList = -1;

    public void initialiseTaskListData(String filename){
        this.taskTitle = filename;
        taskListTitle.setText(taskTitle);
        displayTasksInTaskList();
    }

    public void clickTaskBack(ActionEvent event) {
        try {
            ((ControllerTasks) Main.changePage("/ReadyPlan/view/fxml/tasksPage.fxml",
                    "/ReadyPlan/view/css/designTasks.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickDeleteTask(ActionEvent event) {
        if (taskSelected==null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Task not selected.");
            alert.setContentText("Please select a task before deleting");
            alert.showAndWait();
        }else{
            User.getTasksUser().deleteTaskInList(taskTitle,taskSelected);
            displayTasksInTaskList();
            currentTaskIndex = -1;
        }
    }

    public void displayTasksInTaskList(){
        taskVBox.getChildren().clear();
        tasksInVBox.clear();
        taskVBox.setStyle("-fx-background-color: #fffffc;");
        taskListScrollPane.setContent(taskVBox);
        taskListScrollPane.setStyle("-fx-background-color: #fffffc;");
        for (int i = 0;i<User.getTasksUser().getListOfTasks().size();i++){
            if (User.getTasksUser().getListOfTasks().get(i).equals(taskTitle)){
                indexOfTaskList = i;
                break;
            }
        }

        for (int j = 0;j<User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).size();j++){
            Label tempTask = new Label();
            System.out.println(indexOfTaskList+"index");
            System.out.println(User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList));
            tempTask.setText(User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).get(j).getTaskTitle());
            /*
            int finalIndexOfTaskList = indexOfTaskList;
            int finalJ = j;
            tempTask.setOnMouseClicked(e -> {
                tempTask.setStyle("-fx-background-color: #f7f7f7;-fx-font-family: 'Lucida Sans';-fx-font-size: 22px;");
                taskSelected = User.getTasksUser().getListOfTasksInLists().get(finalIndexOfTaskList).get(finalJ);
                displayTaskDetails(taskSelected); });
             */
            tempTask.setAlignment(Pos.CENTER);
            tempTask.setPrefWidth(334);
            tempTask.setMinHeight(50);
            tempTask.setPrefHeight(50);
            tasksInVBox.add(tempTask);
            taskVBox.getChildren().add(tempTask);
            updateLabelStyle();
            updateClickedLabelIndex();
        }
    }

    private void updateLabelStyle(){
        for (int i = 0;i<tasksInVBox.size();i++){
            if (i==currentTaskIndex){
                tasksInVBox.get(i).setStyle(clickedStyle);
            }else{ tasksInVBox.get(i).setStyle(notClickedStyle);}
        }
    }

    private void updateClickedLabelIndex(){
        for (int i = 0;i<tasksInVBox.size();i++){
            int finalI = i;
            tasksInVBox.get(i).setOnMouseClicked(e -> {currentTaskIndex = finalI; updateLabelStyle();
            taskSelected = User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).get(finalI);
            displayTaskDetails(taskSelected);
            });
        }
    }

    private void displayTaskDetails(TasksSetUpTemplate taskSelected) {
        taskTitleTF.setText(taskSelected.getTaskTitle());
        System.out.println(taskSelected.getTaskDescription());
        System.out.println(decodeString(taskSelected.getTaskDescription()));
        taskDescriptionTA.setText(decodeString(taskSelected.getTaskDescription()));
        taskDatePicker.setValue(taskSelected.getDateForTask());
        if (taskSelected.getRepeat().split("[ ]").length==2){
            customTaskSpinner.getValueFactory().setValue(Integer.parseInt(taskSelected.getRepeat().split("[ ]")[0]));
            customTaskComboBox.setValue(taskSelected.getRepeat().split("[ ]")[1]);
            typeOfTaskComboBox.setValue("CUSTOM");
        }else{
            typeOfTaskComboBox.setValue(taskSelected.getRepeat());
            customTaskComboBox.setValue(null);
        }
    }

    public void saveTask(ActionEvent event) {
        if (currentTaskIndex==-1){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Task not selected.");
            alert.setContentText("Please select a task before saving");
            alert.showAndWait();
        }else if (taskDatePicker.getValue()!=null&&checkValidInputTask(taskTitleTF.getText().trim(),taskDescriptionTA.getText())){
            boolean success = User.getTasksUser().updateTaskInList(taskTitle,taskSelected,taskTitleTF.getText().trim(),processString(taskDescriptionTA.getText()),
                    taskDatePicker.getValue(),typeOfTaskComboBox.getValue());
            displayTasksInTaskList();
            if (success){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Task saved successfully!");
                alert.showAndWait();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to save task");
            alert.setHeaderText("Task title, description or date is not in correct format.");
            alert.setContentText("Please only include word characters for task title. \n " +
                    "Please fill in all fields"+
                    "\nPlease do not use commas in description.");
            alert.showAndWait();
        }
    }

    private String processString(String inputTextArea){
        return inputTextArea.replaceAll("\\n","\\\\n");
    }

    private String decodeString(String inputTextArea){
        return inputTextArea.replaceAll("\\\\n","\n");
    }


    public void addTask(ActionEvent event) {
        try{
            String typeOfReminder = typeOfTaskComboBox.getValue().toString();
            TasksSetUpTemplate newTask = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (typeOfTaskComboBox.getValue()!=null&&taskDatePicker.getValue()!=null&&checkValidInputTask(taskTitleTF.getText().trim(),taskDescriptionTA.getText())){
                String formattedDateForTask =  taskDatePicker.getValue().format(formatter);
                switch (typeOfReminder){
                    case "NO REPEAT": newTask = new TasksSetUpNoRepeat(taskTitleTF.getText().trim(),
                            processString(taskDescriptionTA.getText()),formattedDateForTask,typeOfReminder,"false"); break;
                    case "DAILY": newTask = new TasksSetUpDaily(taskTitleTF.getText().trim(),
                            processString(taskDescriptionTA.getText()),formattedDateForTask,typeOfReminder,"false"); break;
                    case "WEEKLY": newTask = new TasksSetUpWeekly(taskTitleTF.getText().trim(),
                            processString(taskDescriptionTA.getText()),formattedDateForTask,typeOfReminder,"false"); break;
                    case "MONTHLY": newTask = new TasksSetUpMonthly(taskTitleTF.getText().trim(),
                            processString(taskDescriptionTA.getText()),formattedDateForTask,typeOfReminder,"false"); break;
                    case "YEARLY": newTask = new TasksSetUpYearly(taskTitleTF.getText().trim(),
                            processString(taskDescriptionTA.getText()),formattedDateForTask,typeOfReminder,"false"); break;
                    case "CUSTOM":
                        if (customTaskComboBox.getValue()!=null){
                            newTask = new TasksSetUpCustom(taskTitleTF.getText().trim(),
                                    processString(taskDescriptionTA.getText()),formattedDateForTask,
                                    customTaskSpinner.getValue().toString()+" "+customTaskComboBox.getValue().toString(),"false");
                            System.out.println("custom task created"); break;
                        }else{
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Unable to create task");
                            alert.setHeaderText("Custom settings not selected.");
                            alert.setContentText("Please select custom settings if you have selected custom type.");
                            alert.showAndWait();
                        }
                }
                if (newTask!=null){
                    if (isDuplicateTask(newTask)){
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Duplicate task");
                        alert.setHeaderText("Task already exists. There cannot be duplicated tasks.");
                        alert.setContentText("Please add a different task.");
                        alert.showAndWait();
                    }else{
                        User.getTasksUser().addTaskInList(taskTitle,newTask);
                        displayTasksInTaskList();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Task created successfully!");
                        alert.showAndWait();
                    }
                }
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unable to create task");
                alert.setHeaderText("Task title, description or date is not in correct format.");
                alert.setContentText("Please only include word characters for task title.\n " +
                        "Please fill in all fields"+
                        "\nPlease do not use commas in description.");
                alert.showAndWait();
            }
        }catch (NullPointerException e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unable to create task");
            alert.setHeaderText("Task title, description or date is not in correct format.");
            alert.setContentText("Please only include word characters for task title.\n " +
                    "Please fill in all fields"+
                    "\nPlease do not use commas in description.");
            alert.showAndWait();
        }

    }

    private boolean isDuplicateTask(TasksSetUpTemplate tasksSetUpTemplate){
        for (int i = 0;i<User.getTasksUser().getListOfAllTasksInLists().size();i++){
            if (User.getTasksUser().getListOfAllTasksInLists().get(i).contains(tasksSetUpTemplate)){
                return true;
            }
        }
        return false;
    }

    private boolean checkValidInputTask(String taskTitle, String taskDescription){
        String titleRegex = "(\\w| )+";
        String descriptionRegex = "(\\n|[.[^,]])*";
        return taskTitle.matches(titleRegex)&&taskDescription.matches(descriptionRegex);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskDescriptionTA.setWrapText(true);
        ObservableList<String> sortByList = FXCollections.observableArrayList("NO REPEAT","DAILY",
                "WEEKLY","MONTHLY","YEARLY","CUSTOM");
        typeOfTaskComboBox.setItems(sortByList);
        ObservableList<String> typeOfRepeat = FXCollections.observableArrayList("DAYS","WEEKS","MONTHS","YEARS");
        customTaskComboBox.setItems(typeOfRepeat);
        SpinnerValueFactory<Integer> numberSpinnerValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,60);
        customTaskSpinner.setValueFactory(numberSpinnerValues);
        customTaskComboBox.setDisable(true);
        customTaskSpinner.setDisable(true);
        typeOfTaskComboBox.valueProperty().addListener(e->{
            if (typeOfTaskComboBox.getValue()!=null){
                if (typeOfTaskComboBox.getValue().toString().equals("CUSTOM")){
                    customTaskSpinner.setDisable(false);
                    customTaskComboBox.setDisable(false);
                }else{
                    customTaskSpinner.setDisable(true);
                    customTaskComboBox.setDisable(true);
                }
            }
        });
    }

    public void clickViewCompleteTasks(ActionEvent event) {
        Stage popUpWindow = new Stage();
        ScrollPane pane = new ScrollPane();
        VBox completedTaskVBox = new VBox();
        completedTaskVBox.getChildren().clear();
        completedTaskVBox.setStyle("-fx-background-color: #fffffc;");
        pane.setContent(completedTaskVBox);
        pane.setStyle("-fx-background-color: #fffffc;");
        int indexOfCompletedTaskList = -1;
        for (int i = 0;i<User.getTasksUser().getListOfCompletedTasksInLists().size();i++){
            if (User.getTasksUser().getListOfTasks().get(i).equals(taskTitle)){
                indexOfCompletedTaskList = i;
                break;
            }
        }

        ArrayList<Label> listOfCompletedTasks = new ArrayList<Label>();

        System.out.println(User.getTasksUser().getListOfCompletedTasksInLists().get(indexOfCompletedTaskList));
        for (int j = 0;j<User.getTasksUser().getListOfCompletedTasksInLists().get(indexOfCompletedTaskList).size();j++){
            Label tempCompletedTask = new Label();
            tempCompletedTask.setText(User.getTasksUser().getListOfCompletedTasksInLists().get(indexOfCompletedTaskList).get(j).getTaskTitle());
            tempCompletedTask.setStyle(notClickedStyle);
            tempCompletedTask.setAlignment(Pos.CENTER);
            tempCompletedTask.setPrefWidth(334);
            tempCompletedTask.setMinHeight(50);
            tempCompletedTask.setPrefHeight(50);
            listOfCompletedTasks.add(tempCompletedTask);
            completedTaskVBox.getChildren().add(tempCompletedTask);
        }

        if (listOfCompletedTasks.size()==0){
            Label noCompletedTasksLbl = new Label("No tasks completed so far,");
            Label noCompletedTasksLbl2 = new Label("get to work!");
            noCompletedTasksLbl.setPrefWidth(334);
            noCompletedTasksLbl.setAlignment(Pos.CENTER);
            noCompletedTasksLbl2.setAlignment(Pos.CENTER);
            noCompletedTasksLbl2.setPrefWidth(334);
            noCompletedTasksLbl.setStyle(notClickedStyle);
            noCompletedTasksLbl2.setStyle(notClickedStyle);
            completedTaskVBox.getChildren().addAll(noCompletedTasksLbl,noCompletedTasksLbl2);
        }

        try {
            popUpWindow.getIcons().add(new Image(Main.class.getResource("view/240112.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        popUpWindow.setTitle("Ready!Plan: Completed Tasks");

        Scene scene = new Scene(pane,335,550);
        popUpWindow.setScene(scene);
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.showAndWait();
    }

    public void displayHowTaskCompleted(ActionEvent event) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How task is completed");
        alert.setHeaderText("Information on how task is completed");
        alert.setContentText("Task is automatically completed by application.\n"+
                "A task is mark completed if it is of no repeat type\n"+"" +
                "and if the date has passed today's date");
        alert.showAndWait();
    }
}
