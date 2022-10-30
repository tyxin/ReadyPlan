package ReadyPlan.controller;

import ReadyPlan.model.Reminders.RemindersPopUp;
import ReadyPlan.model.Security.Security;
import ReadyPlan.model.User;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerTasks extends Controller implements Initializable {

    @FXML
    private TextField newTaskListTitle;
    @FXML
    private Button addNewTaskListBtn;
    @FXML
    private GridPane tasksPane;
    @FXML
    private TextField tasksSearchTF;
    @FXML
    private Button rpViewBtn1;
    @FXML
    private Button rpDeleteBtn1;
    @FXML
    private Button rpViewBtn2;
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
    private Button rpViewBtn3;
    @FXML
    private Button rpViewBtn4;
    @FXML
    private Button rpViewBtn5;
    @FXML
    private Button rpViewBtn6;
    @FXML
    private Button rpViewBtn7;
    @FXML
    private ComboBox<String> sortByComboBox;
    @FXML
    private Button nextTasksBtn;
    @FXML
    private Button prevTasksBtn;
    //indexed 0
    private int taskListPage = 0;
    //not indexed 0
    private int maxTaskListPage;
    private Button[] arrDeleteButton;
    private Button[] arrViewButton;
    private ObservableList<String> taskListArrayList = FXCollections.observableArrayList(User.getTasksUser().getListOfTasks());

    public void initializeUserData(){
        displayTasks();
        if (taskListArrayList.size()<=7){
            nextTasksBtn.setDisable(true);
        }
        prevTasksBtn.setDisable(true);
        if (taskListArrayList.size()%7==0){
            maxTaskListPage = taskListArrayList.size()/7;
        }else{
            maxTaskListPage = taskListArrayList.size()/7+1;
        }
    }

    public void clickPrevTasks(ActionEvent event) {
        taskListPage--;
        displayTasks();
        nextTasksBtn.setDisable(false);
        if (taskListPage==0){
            prevTasksBtn.setDisable(true);
        }
        if (taskListPage==maxTaskListPage-1){
            nextTasksBtn.setDisable(true);
        }
    }

    public void clickNextTasks(ActionEvent event) {
        taskListPage++;
        displayTasks();
        prevTasksBtn.setDisable(false);
        if (taskListPage==maxTaskListPage-1){
            nextTasksBtn.setDisable(true);
        }
        if (taskListPage==0){
            prevTasksBtn.setDisable(true);
        }
    }

    public void displayTasks() {
        //reset all variables
        tasksPane.getChildren().clear();
        for (int i = 0;i<arrDeleteButton.length;i++){
            arrDeleteButton[i].setDisable(false);
            arrViewButton[i].setDisable(false);
        }
        tasksPane.setStyle("-fx-font-family: 'Lucida Sans';-fx-font-size: 15px;");
        int rowToFillUp;
        if (taskListArrayList.size()-taskListPage*7>=7){
            rowToFillUp = 7;
        }else{
            rowToFillUp = taskListArrayList.size()-taskListPage*7;
        }
        for (int row = 0;row<rowToFillUp;row++) {
            //initializing data into labels
            Label tempTitle = new Label();

            tempTitle.setText(taskListArrayList.get(taskListPage * 7 + row));
            tempTitle.setPrefWidth(820);
            tempTitle.setAlignment(Pos.CENTER);

            tasksPane.add(tempTitle,0,row);
        }

        if (rowToFillUp<7){
            for (int i = rowToFillUp;i<7;i++){
                arrDeleteButton[i].setDisable(true);
                arrViewButton[i].setDisable(true);
            }
        }
    }

    public void clickSort(ActionEvent event) {
        String sortBy = sortByComboBox.getValue();
        if (sortBy.equals("ASCENDING")) {
            Collections.sort(taskListArrayList);
        } else {
            Collections.sort(taskListArrayList);
            Collections.reverse(taskListArrayList);
        }
        displayTasks();
    }

    public void clickTaskListDelete1(ActionEvent event) { deleteTaskAtIndex(taskListPage*7); }

    public void clickTaskListDelete2(ActionEvent event) { deleteTaskAtIndex(taskListPage*7+1); }

    public void clickTaskListDelete3(ActionEvent event) { deleteTaskAtIndex(taskListPage*7+2); }

    public void clickTaskListDelete4(ActionEvent event) { deleteTaskAtIndex(taskListPage*7+3); }

    public void clickTaskListDelete5(ActionEvent event) { deleteTaskAtIndex(taskListPage*7+4); }

    public void clickTaskListDelete6(ActionEvent event) { deleteTaskAtIndex(taskListPage*7+5); }

    public void clickTaskListDelete7(ActionEvent event) { deleteTaskAtIndex(taskListPage*7+6); }

    public void clickTaskListView1(ActionEvent event) { viewTaskAtIndex(taskListPage*7); }

    public void clickTaskListView2(ActionEvent event) { viewTaskAtIndex(taskListPage*7+1); }

    public void clickTaskListView3(ActionEvent event) { viewTaskAtIndex(taskListPage*7+2); }

    public void clickTaskListView4(ActionEvent event) { viewTaskAtIndex(taskListPage*7+3); }

    public void clickTaskListView5(ActionEvent event) { viewTaskAtIndex(taskListPage*7+4); }

    public void clickTaskListView6(ActionEvent event) { viewTaskAtIndex(taskListPage*7+5); }

    public void clickTaskListView7(ActionEvent event) { viewTaskAtIndex(taskListPage*7+6); }

    public void viewTaskAtIndex(int indexNo){
        String filePath = "src/ReadyPlan/tasks_content/"+ Security.getCurrentUserNameFormat()+"_"+taskListArrayList.get(indexNo)+".txt";
        try {
            ((ControllerTaskList) Main.changePage("/ReadyPlan/view/fxml/viewTaskList.fxml",
                    "/ReadyPlan/view/css/designViewTaskList.css")).initialiseTaskListData(taskListArrayList.get(indexNo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayTasks();
    }

    public void deleteTaskAtIndex(int indexNo){
        User.getTasksUser().deleteTaskList(taskListArrayList.get(indexNo));
        taskListArrayList.remove(taskListArrayList.get(indexNo));
        if (taskListArrayList.size()%7==0){
            maxTaskListPage = taskListArrayList.size()/7;
        }else{
            maxTaskListPage = taskListArrayList.size()/7+1;
        }
        displayTasks();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sortByList = FXCollections.observableArrayList("ASCENDING","DESCENDING");
        sortByComboBox.setItems(sortByList);
        arrDeleteButton = new Button[]{rpDeleteBtn1, rpDeleteBtn2, rpDeleteBtn3, rpDeleteBtn4, rpDeleteBtn5, rpDeleteBtn6, rpDeleteBtn7};
        arrViewButton = new Button[]{rpViewBtn1, rpViewBtn2, rpViewBtn3, rpViewBtn4, rpViewBtn5, rpViewBtn6, rpViewBtn7};
        tasksSearchTF.textProperty().addListener(e->{
            String searchText = tasksSearchTF.getText();
            Pattern pattern = Pattern.compile(searchText);
            ObservableList<String> tempTaskListArrayList = FXCollections.observableArrayList();
            for (int i = 0;i<User.getTasksUser().getListOfTasks().size();i++){
                Matcher matcher = pattern.matcher(User.getTasksUser().getListOfTasks().get(i));
                if (matcher.find()){
                    tempTaskListArrayList.add(User.getTasksUser().getListOfTasks().get(i));
                }
            }
            taskListArrayList = tempTaskListArrayList;
            taskListPage = 0;
            nextTasksBtn.setDisable(false);
            if (taskListArrayList.size()<=7){
                nextTasksBtn.setDisable(true);
            }
            prevTasksBtn.setDisable(true);
            if (taskListArrayList.size()%7==0){
                maxTaskListPage = taskListArrayList.size()/7;
            }else{
                maxTaskListPage = taskListArrayList.size()/7+1;
            }
            displayTasks();
        });
    }

    public void addNewTaskList(ActionEvent event) {
        String newTaskTitle = newTaskListTitle.getText();
        //prevents case of spaces as title
        newTaskTitle = newTaskTitle.trim();
        if (newTaskTitle.equals("")){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Creating New Task List Error");
            alert.setHeaderText("Task Title cannot be empty or have only white space characters");
            alert.setContentText("Please key in a valid task title.");
            alert.showAndWait();
        }else if (checkValidTaskListName(newTaskTitle)){
            User.getTasksUser().addTaskList(newTaskListTitle.getText());
            taskListArrayList.add(newTaskListTitle.getText());
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("TaskList created successfully!");
            alert.showAndWait();
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Filename Error");
            alert.setHeaderText("Invalid file name");
            alert.setContentText("Please key in a valid task title.");
            alert.showAndWait();
        }
        if (taskListArrayList.size()%7==0){
            maxTaskListPage = taskListArrayList.size()/7;
        }else{
            maxTaskListPage = taskListArrayList.size()/7+1;
        }
        if (taskListPage==maxTaskListPage-1){
            nextTasksBtn.setDisable(true);
        }else{
            nextTasksBtn.setDisable(false);
        }
        displayTasks();
    }

    private boolean checkValidTaskListName(String taskListName){
        String regexDoNotMatch = "(CON|AUX|PRN|LST|COM[\\d]|LTP[\\d]|NUL| +|)";
        String regexToMatch = "(\\w|_| )+";
        return !taskListName.matches(regexDoNotMatch)&&taskListName.matches(regexToMatch);
    }
}