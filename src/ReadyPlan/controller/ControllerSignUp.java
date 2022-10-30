package ReadyPlan.controller;

import ReadyPlan.model.Security.Security;
import ReadyPlan.model.Security.SecurityUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ReadyPlan.Main;
import ReadyPlan.model.User;

import java.io.IOException;

public class ControllerSignUp extends Controller{

    @FXML
    private TextField suFullNameTF;
    @FXML
    private TextField suEmailTF;
    @FXML
    private TextField suShortDesTF;
    @FXML
    private TextField suPasswordTF;
    @FXML
    private Button suSignUpBtn;

    public void clickSignUp(ActionEvent event) {
        SecurityUser newUser = new SecurityUser(suFullNameTF.getText().trim(),suPasswordTF.getText(),suEmailTF.getText(),suShortDesTF.getText());
        //Security.setCurrentUser(newUser);
        if (notDuplicateUser(suFullNameTF.getText().trim(),suEmailTF.getText())){
            if (!suPasswordTF.getText().trim().equals("")){
                if (SecurityUser.checkNoComma(suShortDesTF.getText(),suPasswordTF.getText())){
                    if (SecurityUser.checkValidEmail(suEmailTF.getText())){
                        if (SecurityUser.checkValidName(suFullNameTF.getText().trim())){
                            User.getSecure().addNewUser(newUser);
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sign Up Successful");
                            alert.setHeaderText("You have signed up successfully!");
                            alert.showAndWait();
                        }else{
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Invalid name format");
                            alert.setContentText("Please include only characters and white spaces in your name");
                            alert.showAndWait();
                        }
                    }else{
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid email format");
                        alert.setContentText("Please include your email in the format h*****(any number of digits starting with 1)\n" +
                                "with the domain @nushigh.edu.sg.");
                        alert.showAndWait();
                    }
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Fields cannot contain commas");
                    alert.setContentText("Please remove commas.");
                    alert.showAndWait();
                }
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid password!");
                alert.setContentText("Password cannot be empty or only whitespace characters.");
                alert.showAndWait();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Name or email is used");
            alert.setContentText("Please use another name or email.");
            alert.showAndWait();
        }

        //refresh landing page
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("view/fxml/landingPage.fxml"));
            Scene scene = new Scene(root);
            Main.getPrimaryStage().setScene(scene);
            Main.getPrimaryStage().setTitle("OOP II Project");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean notDuplicateUser(String name, String email) {
        for (int i = 0;i<User.getSecure().getUsersDB().size();i++){
            if (User.getSecure().getUsersDB().get(i).getName().equals(name)){
                return false;
            }
            if (User.getSecure().getUsersDB().get(i).getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }
}
