package ReadyPlan.model.Reminders;

import ReadyPlan.model.Security.Security;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public abstract class RemindersPopUp extends HBox {
    protected String title;
    protected String description;
    protected String typeOfReminder;
    protected LocalDate dateCreated;
    protected LocalDate dateForReminder;


    public RemindersPopUp(String title, String description, String dateCreated, String dateForReminder,String typeOfReminder){
        this.title = title;
        this.description = description;
        this.typeOfReminder = typeOfReminder;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateCreated = LocalDate.parse(dateCreated,formatter);
        this.dateForReminder = LocalDate.parse(dateForReminder,formatter);
    }

    public RemindersPopUp(RemindersPopUp popUp){
        this(popUp.getTitle(),popUp.getDescription(),popUp.getDateCreated(),popUp.getDateForReminder(),popUp.getTypeOfReminder());
    }

    public VBox setUpOneReminder(){
        VBox oneReminder = new VBox();
        HBox firstLine = new HBox();
        firstLine.setPrefWidth(240);
        firstLine.setPrefHeight(45);
        oneReminder.setSpacing(5);
        oneReminder.setPrefHeight(330);
        oneReminder.setPrefWidth(240);
        oneReminder.setStyle("-fx-border-radius: 15px;-fx-font-family: 'Lucida Sans';-fx-font-size: 15px;-fx-background-radius: 15px; -fx-background-color: #fffffc;-fx-font-fill: #212121; -fx-effect: dropshadow(gaussian,#c9c9c9,2,2,2,2);");
        Label titleLbl = new Label();
        titleLbl.setAlignment(Pos.CENTER);
        firstLine.setPadding(new Insets(5,0,0,0));
        titleLbl.setPadding(new Insets(5,0,0,0));
        titleLbl.setPrefWidth(240);
        titleLbl.setText(title);

        oneReminder.setOnMouseEntered(e->{
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),oneReminder);
            scaleTransition.setToX(1.05);
            scaleTransition.setToY(1.05);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();
        });

        oneReminder.setOnMouseExited(e->{
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),oneReminder);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();
        });

        /*
        Button deleteButton = new Button();
        deleteButton.setText("x");
        deleteButton.setStyle("-fx-background-color: #ff8f8f;-fx-text-fill: white; -fx-border-width: 0.8px; -fx-font-family:'Lucida Sans';");
        deleteButton.setOnAction(e -> clickDelete(e));
        //make it round
        deleteButton.setPrefHeight(30);
        deleteButton.setPrefWidth(30);
        //deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-scale-x: 1.1; -fx-scale-y: 1.1;-fx-background-color: #ffc2c2;-fx-text-fill: white; -fx-border-width: 0.8px; -fx-font-family:'Lucida Sans';"));
        //deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #ffc2c2;-fx-text-fill: white; -fx-border-width: 0.8px; -fx-font-family:'Lucida Sans';"));
        deleteButton.styleProperty().bind(Bindings.when(deleteButton.hoverProperty())
                .then("-fx-background-color: #ff8f8f;-fx-text-fill: white; -fx-border-width: 0.8px; -fx-font-family:'Lucida Sans';-fx-scale-x:1.1;-fx-scale-y:1.1;")
                .otherwise("-fx-background-color: #ff8f8f;-fx-text-fill: white; -fx-border-width: 0.8px; -fx-font-family:'Lucida Sans';"));
        Circle circle = new Circle();
        circle.setRadius(1);
        deleteButton.setShape(circle);
         */
        firstLine.getChildren().addAll(titleLbl);

        Label descriptionLbl = new Label();
        descriptionLbl.setWrapText(true);
        descriptionLbl.setPadding(new Insets(0,0,0,10));
        descriptionLbl.setPrefWidth(240);
        System.out.println(description);
        System.out.println(decodeString(description));
        String formattedDescription = String.format("%-500s",decodeString(description));
        descriptionLbl.setText(formattedDescription+"\n");



        Label dateForReminderLbl = new Label();
        dateForReminderLbl.setPadding(new Insets(0,0,0,10));
        dateForReminderLbl.setText("Reminder for: "+getDateForReminder());
        dateForReminderLbl.setPrefWidth(230);

        Label dateCreatedLbl = new Label();
        dateCreatedLbl.setPadding(new Insets(0,0,0,10));
        dateCreatedLbl.setText("Created: "+getDateCreated());
        dateCreatedLbl.setPrefWidth(230);

        Label typeOfReminderLbl = new Label();
        typeOfReminderLbl.setPadding(new Insets(0,0,0,10));
        typeOfReminderLbl.setPrefWidth(230);

        styleReminderPopUp(firstLine,typeOfReminderLbl);

        oneReminder.getChildren().add(firstLine);
        oneReminder.getChildren().add(descriptionLbl);
        oneReminder.getChildren().add(dateForReminderLbl);
        oneReminder.getChildren().add(dateCreatedLbl);
        oneReminder.getChildren().add(typeOfReminderLbl);

        return oneReminder;
    }

    private String processString(String inputTextArea){
        return inputTextArea.replaceAll("\\n","\\\\n");
    }

    private String decodeString(String inputTextArea){ return inputTextArea.replaceAll("\\\\n","\n"); }


    //styling that differs for differnt types of reminders
    public abstract void styleReminderPopUp(HBox firstline, Label typeOfReminderLbl);

    public abstract LocalDate setNextDateForReminder();

    /*
    //can be used for the delete button in table --> check out next time
    public void clickDelete(ActionEvent e) {
        //delete reminder
        File file = new File("reminders/RemindersData"+ Security.getCurrentUserNameFormat()+".txt");
        String writeBack = "";
        try {
            Scanner input = new Scanner(file);
            String tempFileName = "";
            while(input.hasNextLine()){
                tempFileName = input.nextLine();
                String[] temp = tempFileName.split("[,]");
                if (temp[0].equals(title)){
                    File selectedFileToDelete = new File("reminders/"+title+Security.getCurrentUserNameFormat()+".txt");
                    selectedFileToDelete.delete();
                }else{
                    writeBack+=tempFileName+"\n";
                }
            }
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        //write back data without file deleted name
        try {
            PrintWriter output = new PrintWriter("reminders/RemindersData"+Security.getCurrentUserNameFormat()+".txt");
            output.print(writeBack);
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

     */

    public String getTypeOfReminder() {
        return typeOfReminder;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       return dateCreated.format(formatter);
    }

    public String getDateForReminder() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateForReminder.format(formatter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }else if(this instanceof RemindersPopUp){
            RemindersPopUp popUp =(RemindersPopUp) obj;
            return (popUp.getTitle().equals(title)&&popUp.getDescription().equals(description)&&
                    popUp.getDateCreated().equals(this.getDateCreated())&&
                    popUp.getDateForReminder().equals(this.getDateForReminder())&&
                    popUp.getTypeOfReminder().equals(this.getTypeOfReminder()));
        }else{return false;}
    }

    public void setDateForReminder(LocalDate dateForReminder) {
        this.dateForReminder = dateForReminder;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateReminder =  dateForReminder.format(formatter);
        String formattedDateCreated = dateCreated.format(formatter);
        return title+","+description+","+formattedDateCreated+","+formattedDateReminder+","+typeOfReminder;
    }
}
