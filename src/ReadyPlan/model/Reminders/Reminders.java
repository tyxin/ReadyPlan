package ReadyPlan.model.Reminders;

import ReadyPlan.Main;
import ReadyPlan.controller.ControllerReminders;
import ReadyPlan.model.Security.Security;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import ReadyPlan.model.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Reminders {
    private ArrayList<RemindersPopUp> remindersPopUpDB;
    private ArrayList<RemindersPopUp> remindersToday;
    private String filename;

    public Reminders(String filename){
        this.filename = filename;
        remindersPopUpDB = new ArrayList<RemindersPopUp>();
        loadReminderPopUps(filename);
        remindersToday = new ArrayList<RemindersPopUp>();
    }

    public void loadRemindersToday(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToday = formatter.format(LocalDate.now());
        for (int i = 0;i<remindersPopUpDB.size();i++){
            if (remindersPopUpDB.get(i).getDateForReminder().equals(dateToday)){
                remindersToday.add(remindersPopUpDB.get(i));
            }
        }
    }

    public void loadReminderPopUps(String filename){
        //File file = new File(filename);
        File file = new File("src/ReadyPlan/fileResources/reminders/RemindersData"+ Security.getCurrentUserNameFormat()+".txt");
        try {
            Scanner input = new Scanner(file);
            String tempLine = "";
            while (input.hasNextLine()){
                tempLine = input.nextLine();
                String[] temp = tempLine.split("[,]");
                if (temp[4].equals("DAILY")){
                    remindersPopUpDB.add(new RemindersPopUpDaily(temp[0],temp[1],temp[2],temp[3],temp[4]));
                }else if(temp[4].equals("WEEKLY")){
                    remindersPopUpDB.add(new RemindersPopUpWeekly(temp[0],temp[1],temp[2],temp[3],temp[4]));
                }else if(temp[4].equals("MONTHLY")){
                    remindersPopUpDB.add(new RemindersPopUpMonthly(temp[0],temp[1],temp[2],temp[3],temp[4]));
                }else if(temp[4].equals("YEARLY")){
                    remindersPopUpDB.add(new RemindersPopUpYearly(temp[0],temp[1],temp[2],temp[3],temp[4]));
                }else if(temp[4].equals("NO REPEAT")){
                    remindersPopUpDB.add(new RemindersPopUpNoRepeat(temp[0],temp[1],temp[2],temp[3],temp[4]));
                }else{
                    remindersPopUpDB.add(new RemindersPopUpCustom(temp[0],temp[1],temp[2],temp[3],temp[4]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String processString(String inputTextArea){
        return inputTextArea.replaceAll("\\n","\\\\n");
    }

    private String decodeString(String inputTextArea){
        return inputTextArea.replaceAll("\\\\n","\n");
    }

    public boolean checkDuplicateReminder(RemindersPopUp inputReminder){
        //File file = new File(filename);
        File file = new File("src/ReadyPlan/fileResources/reminders/RemindersData"+ Security.getCurrentUserNameFormat()+".txt");
        try {
            Scanner input = new Scanner(file);
            String tempLine = "";
            while(input.hasNextLine()){
                tempLine = input.nextLine();
                String[] temp = tempLine.split("[,]");
                RemindersPopUp popUp;
                if (temp[4].equals("DAILY")){
                    popUp = new RemindersPopUpDaily(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else if(temp[4].equals("WEEKLY")){
                    popUp = new RemindersPopUpWeekly(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else if(temp[4].equals("MONTHLY")){
                    popUp = new RemindersPopUpMonthly(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else if(temp[4].equals("YEARLY")){
                    popUp = new RemindersPopUpYearly(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else if(temp[4].equals("NO REPEAT")){
                    popUp = new RemindersPopUpNoRepeat(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else{
                    popUp = new RemindersPopUpCustom(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }
                if (popUp.equals(inputReminder)){
                    return true;
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param toAddPopup
     * to add deep copy of pop up
     */

    public void addReminders(RemindersPopUp toAddPopup){
        RemindersPopUp deepCopyPopUp;
        if (toAddPopup.getTypeOfReminder().equals("DAILY")){
           deepCopyPopUp = new RemindersPopUpDaily(toAddPopup.getTitle(),toAddPopup.getDescription(),toAddPopup.getDateCreated(),toAddPopup.getDateForReminder(),toAddPopup.getTypeOfReminder());
        }else if(toAddPopup.getTypeOfReminder().equals("WEEKLY")){
            deepCopyPopUp = new RemindersPopUpWeekly(toAddPopup.getTitle(),toAddPopup.getDescription(),toAddPopup.getDateCreated(),toAddPopup.getDateForReminder(),toAddPopup.getTypeOfReminder());
        }else if(toAddPopup.getTypeOfReminder().equals("MONTHLY")){
            deepCopyPopUp = new RemindersPopUpMonthly(toAddPopup.getTitle(),toAddPopup.getDescription(),toAddPopup.getDateCreated(),toAddPopup.getDateForReminder(),toAddPopup.getTypeOfReminder());
        }else if(toAddPopup.getTypeOfReminder().equals("YEARLY")){
            deepCopyPopUp = new RemindersPopUpYearly(toAddPopup.getTitle(),toAddPopup.getDescription(),toAddPopup.getDateCreated(),toAddPopup.getDateForReminder(),toAddPopup.getTypeOfReminder());
        }else if(toAddPopup.getTypeOfReminder().equals("NO REPEAT")){
            deepCopyPopUp = new RemindersPopUpNoRepeat(toAddPopup.getTitle(),toAddPopup.getDescription(),toAddPopup.getDateCreated(),toAddPopup.getDateForReminder(),toAddPopup.getTypeOfReminder());
        }else{
            deepCopyPopUp = new RemindersPopUpCustom(toAddPopup.getTitle(),toAddPopup.getDescription(),toAddPopup.getDateCreated(),toAddPopup.getDateForReminder(),toAddPopup.getTypeOfReminder());
        }
        remindersPopUpDB.add(deepCopyPopUp);
        updateReminder();
    }

    /**
     * display reminders in table form
     * @param reminder
     * @param title
     * @param description
     * @param dateCreated
     * @param dateForReminder
     * @param typeOfReminder
     */

    /**
     * display reminders for today in for home page
     * display three at a time
     * @param boxToAddIn
     */
    public void displayTodayReminders(HBox boxToAddIn, int startIndexNo){
        System.out.println(startIndexNo);
        boxToAddIn.setSpacing(10);
        boxToAddIn.getChildren().clear();
        // to be continued, done creating array list of the reminders dated today
        int count  = 0;
        System.out.println(remindersToday.size());
        System.out.println(remindersToday.size()-startIndexNo);
        if (remindersToday.size()-startIndexNo<3){
            count = remindersToday.size()-startIndexNo;
        }else {count = 3;}
        System.out.println(count);
        for (int i = startIndexNo;i<startIndexNo+count;i++){
            VBox reminderNode = remindersToday.get(i).setUpOneReminder();
            reminderNode.setOnMouseClicked(e->{
                try {
                    ((ControllerReminders) Main.changePage("/ReadyPlan/view/fxml/remindersPage.fxml","/ReadyPlan/view/css/designReminders.css")).initializeUserData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            boxToAddIn.getChildren().add(reminderNode);
        }
    }

    public void deleteReminder(RemindersPopUp popUpReminder){
        File file = new File("src/ReadyPlan/fileResources/reminders/RemindersData"+ Security.getCurrentUserNameFormat()+".txt");
        try {
            Scanner input = new Scanner(file);
            String tempFileName = "";
            while(input.hasNextLine()){
                tempFileName = input.nextLine();
                String[] temp = tempFileName.split("[,]");
                RemindersPopUp reminder;
                if (temp[4].equals("DAILY")){
                    reminder = new RemindersPopUpDaily(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else if(temp[4].equals("WEEKLY")){
                    reminder = new RemindersPopUpWeekly(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else if(temp[4].equals("MONTHLY")){
                    reminder = new RemindersPopUpMonthly(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else if(temp[4].equals("YEARLY")){
                    reminder = new RemindersPopUpYearly(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else if(temp[4].equals("NO REPEAT")){
                    reminder = new RemindersPopUpNoRepeat(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }else{
                    reminder = new RemindersPopUpCustom(temp[0],temp[1],temp[2],temp[3],temp[4]);
                }

                if (reminder.equals(popUpReminder)) {
                    User.getRemindersUser().getRemindersPopUpDB().remove(popUpReminder);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        //write back data without file deleted name
        updateReminder();
    }

    public void updateReminder(){
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/reminders/RemindersData"+Security.getCurrentUserNameFormat()+".txt");
            for (int i = 0;i<User.getRemindersUser().getRemindersPopUpDB().size();i++){
                output.println(User.getRemindersUser().getRemindersPopUpDB().get(i));
            }
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RemindersPopUp> getRemindersPopUpDB() {
        return remindersPopUpDB;
    }

    public ArrayList<RemindersPopUp> getRemindersToday() {
        return remindersToday;
    }

}
