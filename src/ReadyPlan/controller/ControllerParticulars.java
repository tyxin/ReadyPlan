package ReadyPlan.controller;

import ReadyPlan.model.Security.Security;
import ReadyPlan.model.Security.SecurityUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ReadyPlan.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerParticulars extends Controller implements Initializable {

    @FXML
    private Button ppEditEmailBtn;
    @FXML
    private Button ppSaveEmailBtn;
    @FXML
    private Button ppSDEditBtn;
    @FXML
    private Button ppSDSaveBtn;
    @FXML
    private Button ppPasswordEditBtn;
    @FXML
    private Button ppSavePasswordBtn;
    @FXML
    private TextField ppPasswordTF;
    @FXML
    private TextField ppShortDesTF;
    @FXML
    private TextField ppEmailTF;
    @FXML
    private TextField ppFullNameTF;

    public void clickppEditEmailBtn(ActionEvent event) {
        ppEmailTF.setDisable(false);
    }

    public void clickppSaveEmailBtn(ActionEvent event) {
        if (Security.getCurrentUser().checkValidEmail(ppEmailTF.getText())){
            Security.getCurrentUser().updateEmail(ppEmailTF.getText());
            ppEmailTF.setDisable(true);
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email.");
            alert.setContentText("Oops! Please check your that your email has the domain @nushigh.edu.sg.");
            alert.showAndWait();
        }
    }

    public void clickppSDEditBtn(ActionEvent event) {
        ppShortDesTF.setDisable(false);
    }

    public void clickppSDSaveBtn(ActionEvent event) {
        if (SecurityUser.checkNoComma(ppShortDesTF.getText(),"")){
            Security.getCurrentUser().updateDescription(ppShortDesTF.getText());
            ppShortDesTF.setDisable(true);
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Description cannot contain commas");
            alert.setContentText("Please remove commas.");
            alert.showAndWait();
        }

    }

    public void clickppEditPasswordBtn(ActionEvent event) { ppPasswordTF.setDisable(false); }

    public void clickppSavePasswordBtn(ActionEvent event) {
        if (SecurityUser.checkNoComma("",ppPasswordTF.getText())&&!ppPasswordTF.getText().trim().equals("")){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Change Password");
            alert.setHeaderText("Are you sure you want to change your password to "+ppPasswordTF.getText());
            alert.showAndWait();
            //if user click ok button
            if (alert.getResult()== ButtonType.OK){
                Security.getCurrentUser().updatePassword(ppPasswordTF.getText());
                ppPasswordTF.setDisable(true);
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password cannot contain commas or only whitespace characters.");
            alert.setContentText("Please remove commas or include characters.");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ppFullNameTF.setText(Security.getCurrentUser().getName());
        ppShortDesTF.setText(Security.getCurrentUser().getDescription());
        ppEmailTF.setText(Security.getCurrentUser().getEmail());
        ppPasswordTF.setText(Security.getCurrentUser().getPassword());
        ppFullNameTF.setDisable(true);
        ppShortDesTF.setDisable(true);
        ppEmailTF.setDisable(true);
        ppPasswordTF.setDisable(true);
    }
}
