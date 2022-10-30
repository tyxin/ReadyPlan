package ReadyPlan.controller;

import ReadyPlan.Main;
import ReadyPlan.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHome extends Controller implements Initializable {

    public GridPane homePane;
    public GridPane homePagePane;
    @FXML
    private HBox todayRemindersHBox;
    @FXML
    private Label hpWelcomeBackLbl;
    @FXML
    private Button hpPrevRemindersBtn;
    @FXML
    private Button hpNextRemindersBtn;
    private int indexNo = 0;

    public void clickPrevReminders(ActionEvent event) {
        indexNo-=3;
        User.getRemindersUser().displayTodayReminders(todayRemindersHBox,indexNo);
        hpNextRemindersBtn.setDisable(false);
        if (indexNo<3){
            hpPrevRemindersBtn.setDisable(true);
        }
    }

    public void clickNextReminders(ActionEvent event) {
        indexNo+=3;
        User.getRemindersUser().displayTodayReminders(todayRemindersHBox,indexNo);
        hpPrevRemindersBtn.setDisable(false);
        if (User.getRemindersUser().getRemindersToday().size()-indexNo<=3){
            hpNextRemindersBtn.setDisable(true);
        }
    }

    public void initializeUserData(){
        User.getRemindersUser().getRemindersToday().clear();
        User.getRemindersUser().loadRemindersToday();
        User.getRemindersUser().displayTodayReminders(todayRemindersHBox,indexNo);
        if (User.getRemindersUser().getRemindersToday().size()==0){
            //File file = new File("src/ReadyPlan/view/noData.png");
            //String filePath = file.getAbsoluteFile().toURI().toString();
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
            todayRemindersHBox.setAlignment(Pos.CENTER);
            todayRemindersHBox.getChildren().add(noDatavBox);
        }
        //if less than or equal to three today reminders
        if(User.getRemindersUser().getRemindersToday().size()<=3){
            hpNextRemindersBtn.setDisable(true);
        }
        hpPrevRemindersBtn.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
