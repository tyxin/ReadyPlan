package ReadyPlan.controller;

import ReadyPlan.model.Notes.Notes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ReadyPlan.Main;

import java.io.IOException;

public class Controller {

    @FXML
    protected Button hpHomeBtn;
    @FXML
    protected Button hpParticularBtn;
    @FXML
    protected Button hpLogoutBtn;
    @FXML
    protected Button hpRemindersBtn;
    @FXML
    protected Button hpTasksBtn;
    @FXML
    protected Button hpNotesBtn;
    @FXML
    protected Button hpResourcesBtn;
    @FXML
    protected Button hpOverviewBtn;
    @FXML
    protected Button hpStatisticsBtn;
    //protected Notes publicNotes;
    protected Alert alert;
    //to be edited
    protected TextArea dummyTA = new TextArea();

    public void clickParticulars(ActionEvent event) {
        try {
            Main.changePage("/ReadyPlan/view/fxml/particularsPage.fxml","/ReadyPlan/view/css/designParticulars.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickHpLogout(ActionEvent event) {
        hpLogoutBtn.setStyle("-fx-background-color: #aaff99; -fx-scale-x: 1.05; -fx-scale-y: 1.05;");
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.showAndWait();
        //if user click ok button
        if (alert.getResult()==ButtonType.OK){
            try {
                Main.changePage("/ReadyPlan/view/fxml/landingPage.fxml","/ReadyPlan/view/css/designLanding.css");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            hpLogoutBtn.setStyle("");
        }
    }

    public void clickHome(ActionEvent event) {
        try {
            ((ControllerHome) Main.changePage("/ReadyPlan/view/fxml/homePage.fxml","/ReadyPlan/view/css/designHome.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickReminders(ActionEvent event) {
        try {
            ((ControllerReminders) Main.changePage("/ReadyPlan/view/fxml/remindersPage.fxml","/ReadyPlan/view/css/designReminders.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickTasks(ActionEvent event) {
        try {
            ((ControllerTasks)Main.changePage("/ReadyPlan/view/fxml/tasksPage.fxml","/ReadyPlan/view/css/designTasks.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickNotes(ActionEvent event) {
        try {
            ((ControllerNotes) Main.changePage("/ReadyPlan/view/fxml/notesPage.fxml","/ReadyPlan/view/css/designNotes.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickResources(ActionEvent event) {
        try {
            ((ControllerResources) Main.changePage("/ReadyPlan/view/fxml/resourcesPage.fxml","/ReadyPlan/view/css/designResources.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickOverview(ActionEvent event) {
        try {
            ((ControllerOverview) Main.changePage("/ReadyPlan/view/fxml/overviewPage.fxml","/ReadyPlan/view/css/designOverview.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickStatistics(ActionEvent event) {
        try {
            ((ControllerStatistics) Main.changePage("/ReadyPlan/view/fxml/statisticsPage.fxml","/ReadyPlan/view/css/designStatistics.css")).initializeUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
