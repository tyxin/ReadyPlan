package ReadyPlan.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ReadyPlan.Main;
import ReadyPlan.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable{
    @FXML
    private Hyperlink lpHelpInfo;
    @FXML
    private AnchorPane backgroundPane;
    //landing login page
    @FXML
    private TextField lpNameTF;
    @FXML
    private PasswordField lpPasswordTF;
    @FXML
    private Hyperlink lpSignupLink;
    @FXML
    private Button lpLoginBtn;
    private Alert alert;
    private User userData;

    public void clickSignUp(ActionEvent event) {
        try{
            Stage popupwindow = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/ReadyPlan/view/fxml/signUpPopUp.fxml"));
            popupwindow.setScene(new Scene(root));
            popupwindow.setTitle("Sign Up Form");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.initOwner(lpSignupLink.getScene().getWindow());
            popupwindow.showAndWait();
            lpSignupLink.setVisited(false);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void clickLogin(ActionEvent event) {
        // to work on
        System.out.println(lpNameTF.getText()+lpPasswordTF.getText());
        System.out.println(User.getSecure().getUsersDB());
        boolean isValid = User.getSecure().validLogin(lpNameTF.getText(),lpPasswordTF.getText());
        if (isValid){
            try {
                initializeData();
                ((ControllerHome) Main.changePage("/ReadyPlan/view/fxml/homePage.fxml", "/ReadyPlan/view/css/designHome.css")).initializeUserData();
            } catch (IOException e){
                e.printStackTrace();
            } catch (Exception ex){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Files are corrupted");
                alert.setContentText("Oops! Please download Ready!Plan project again.");
                alert.showAndWait();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Login.");
            alert.setContentText("Oops! Please check your userid and password again");
            alert.showAndWait();
        }
    }

    public void clickHelpInfo(ActionEvent event) {
        try{
            Stage popupwindow = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/ReadyPlan/view/fxml/helpInfoPage.fxml"));
            popupwindow.setScene(new Scene(root));
            popupwindow.setTitle("Help information");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.initOwner(lpHelpInfo.getScene().getWindow());
            popupwindow.showAndWait();
            lpHelpInfo.setVisited(false);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //only called after login
    public void initializeData(){
        User.initializeUser();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
