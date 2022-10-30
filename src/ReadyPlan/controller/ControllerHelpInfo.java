package ReadyPlan.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerHelpInfo {

    @FXML
    private Label chooseLanguageLbl;
    @FXML
    private RadioButton chineseRadioButton;
    @FXML
    private RadioButton englishRadioButton;
    @FXML
    private Label referDocumentationLbl;
    @FXML
    private Label aboutProgrammerLbl;
    @FXML
    private Label doneByLbl;
    private String language = "en";
    private String country = "US";
    private Locale currentLocale;
    private ResourceBundle messages;

    public void changeLanguage(ActionEvent event) {
        if (chineseRadioButton.isSelected()){
            language = "zh";
            country = "CN";
        }else if(englishRadioButton.isSelected()){
            language = "en";
            country = "US";
        }

        currentLocale = new Locale(language,country);
        messages = ResourceBundle.getBundle("ReadyPlan.MessagesBundle",currentLocale);
        referDocumentationLbl.setText(messages.getString("referDocumentationVar"));
        aboutProgrammerLbl.setText(messages.getString("aboutProgrammerVar"));
        doneByLbl.setText(messages.getString("doneByVar"));
        chooseLanguageLbl.setText(messages.getString("chooseLanguageVar"));
    }
}
