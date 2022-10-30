package ReadyPlan.controller;

import ReadyPlan.Main;
import ReadyPlan.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStatistics extends Controller implements Initializable {

    @FXML
    private VBox overviewVBox;
    @FXML
    private HBox statisticsTopHBox;
    @FXML
    private HBox statisticsBottomHBox;
    @FXML
    private Button prevStatisticsBtn;
    @FXML
    private Button nextStatisticsBtn;
    private int startIndexNo = 0;

    public void initializeUserData() {
        User.getStatisticsUser().loadStatisticsList();
        statisticsTopHBox.setSpacing(20);
        statisticsTopHBox.setPadding(new Insets(20,0,0,20));
        statisticsBottomHBox.setSpacing(20);
        statisticsBottomHBox.setPadding(new Insets(10,0,0,20));
        displayStatistics(startIndexNo);
        if(User.getStatisticsUser().getStatisticsList().size()<=8){
            nextStatisticsBtn.setDisable(true);
        }
        prevStatisticsBtn.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void clickPrevStatistics(ActionEvent event) {
        startIndexNo-=8;
        displayStatistics(startIndexNo);
        nextStatisticsBtn.setDisable(false);
        prevStatisticsBtn.setDisable(false);
        if (startIndexNo<8){
            prevStatisticsBtn.setDisable(true);
        }

    }

    public void clickNextStatistics(ActionEvent event) {
        startIndexNo+=8;
        displayStatistics(startIndexNo);
        prevStatisticsBtn.setDisable(false);
        if (User.getStatisticsUser().getStatisticsList().size()-startIndexNo<=8){
            nextStatisticsBtn.setDisable(true);
        }
    }

    private void displayStatistics(int startIndex){
        statisticsTopHBox.getChildren().clear();
        statisticsBottomHBox.getChildren().clear();
        for (int i = startIndex;i<startIndex+4;i++){
            if (i<User.getStatisticsUser().getStatisticsList().size()){
                VBox statisticNode = User.getStatisticsUser().getStatisticsList().get(i).setUpTaskStat(195,250);
                statisticNode.setOnMouseClicked(e->{
                    try {
                        ((ControllerTasks) Main.changePage("/ReadyPlan/view/fxml/tasksPage.fxml","/ReadyPlan/view/css/designTasks.css")).initializeUserData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                statisticsTopHBox.getChildren().add(statisticNode);
            }
        }
        for (int j = startIndex+4;j<startIndex+8;j++){
            if (j<User.getStatisticsUser().getStatisticsList().size()){
                VBox statisticNode = User.getStatisticsUser().getStatisticsList().get(j).setUpTaskStat(195,250);
                statisticNode.setOnMouseClicked(e->{
                    try {
                        ((ControllerTasks) Main.changePage("/ReadyPlan/view/fxml/tasksPage.fxml","/ReadyPlan/view/css/designTasks.css")).initializeUserData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                statisticsBottomHBox.getChildren().add(statisticNode);
            }
        }

        if (User.getStatisticsUser().getStatisticsList().size()==0){
            String filePath = null;
            try {
                filePath = Main.class.getResource("view/noData.png").toURI().toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            VBox noDatavBox = new VBox();
            Label noDatalbl = new Label("There is no data here. Start creating tasks, reminders, notes!");
            noDatalbl.setStyle("-fx-font-family: 'Lucida Sans';-fx-font-size: 15px;-fx-font-fill: #212121; ");
            ImageView imageView = new ImageView();
            Image image = new Image(filePath);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(360);
            noDatavBox.getChildren().addAll(imageView,noDatalbl);
            statisticsTopHBox.setAlignment(Pos.CENTER);
            statisticsTopHBox.getChildren().add(noDatavBox);
        }
    }

}
