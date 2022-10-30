package ReadyPlan.controller;

import ReadyPlan.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOverview extends Controller implements Initializable {

    @FXML
    private HBox overviewTopHBox;
    @FXML
    private HBox overviewBottomHBox;
    @FXML
    private VBox overviewVBox;

    public void initializeUserData() {
        User.getOverviewUser().loadOverViewList();

        overviewVBox.setPadding(new Insets(0,5,8,5));
        overviewTopHBox.setSpacing(20);
        overviewTopHBox.setPadding(new Insets(0,0,5,20));
        overviewBottomHBox.setSpacing(20);
        overviewBottomHBox.setPadding(new Insets(10,0,5,20));
        System.out.println(User.getOverviewUser().getOverviewList());
        for (int i = 0;i<4;i++){
            overviewTopHBox.getChildren().add(User.getOverviewUser().getOverviewList().get(i).setUpOverview(225,275));
        }

        for (int j = 4;j<7;j++){
            overviewBottomHBox.getChildren().add(User.getOverviewUser().getOverviewList().get(j).setUpOverview(310,235));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
