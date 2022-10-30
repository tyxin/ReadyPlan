package ReadyPlan.model.Resources;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ReadyPlan.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Resources {

    private TextArea toWriteInto;
    private String filePath;

    public Resources(TextArea toWriteInto){
        this.toWriteInto = toWriteInto;
        this.toWriteInto.setEditable(true);
    }

    public void OpenResourceTextFile(){
        FileChooser jfc = new FileChooser();
        jfc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = jfc.showOpenDialog(null);
        filePath = selectedFile.getPath();
        System.out.println(filePath);
        String textInput = "";
        try {
            Scanner input = new Scanner(selectedFile);
            while(input.hasNextLine()){
                textInput+=input.nextLine()+"\n";
            }
            toWriteInto.setText(textInput);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void OpenResourceImage(ImageView imageView){
        FileChooser jfc = new FileChooser();
        jfc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg","*.png"));
        File selectedFile = jfc.showOpenDialog(null);
        filePath = selectedFile.getAbsoluteFile().toURI().toString();
        if (selectedFile!=null){
            //to fix
            Image image = new Image(filePath);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            //value to change
            imageView.setFitHeight(360);
        }

    }


    /**
     * write back into file with edits
     * @param textToWriteIn
     */
    public boolean SaveEditedResource(String textToWriteIn){
        boolean isWritten = true;
        if (filePath!=null){
            File file = new File(filePath);
            try {
                PrintWriter output = new PrintWriter(file);
                output.println(textToWriteIn);
                output.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            isWritten = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing to save.");
            alert.setContentText("Text file is not selected to save.");
            alert.showAndWait();
        }
        return isWritten;
    }
}
