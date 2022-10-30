package ReadyPlan.controller;

import ReadyPlan.model.Resources.Resources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerResources extends Controller implements Initializable {

    @FXML
    private ImageView imageArea;
    @FXML
    private ComboBox<String> fileTypeComboBox;
    @FXML
    private Button resourcesSaveBtn;
    @FXML
    private Button resourcesChooseFileBtn;
    @FXML
    private Label resourceFileNameLbl;
    @FXML
    private TextArea resourceFileTA;
    @FXML
    private Resources resourcesUser;

    public void initializeUserData(){
    }

    public void clickResourceSaveBtn(ActionEvent event) {
        String textToWriteIn = resourceFileTA.getText();
        boolean success = resourcesUser.SaveEditedResource(textToWriteIn);
        if (success){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Text file saved successfully!");
            alert.showAndWait();
        }
    }

    public void clickChooseFile(ActionEvent event) {
        String fileType = fileTypeComboBox.getValue();
        try{
            if (fileType.equals("TEXT")){
                resourcesUser.OpenResourceTextFile();
                resourcesSaveBtn.setDisable(false);
                imageArea.setVisible(false);
            }else {
                resourcesUser.OpenResourceImage(imageArea);
                resourcesSaveBtn.setDisable(true);
            }
        }catch(NullPointerException e){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File reading error");
            alert.setHeaderText("File Type is not selected or File not selected.");
            alert.setContentText("Please select File Type 'TEXT' or 'IMAGE' or select a file to open.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resourcesUser = new Resources(resourceFileTA);
        ObservableList<String> sortByFileType = FXCollections.observableArrayList("IMAGE","TEXT");
        fileTypeComboBox.setItems(sortByFileType);
    }
}
