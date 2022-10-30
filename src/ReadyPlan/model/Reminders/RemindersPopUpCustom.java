package ReadyPlan.model.Reminders;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RemindersPopUpCustom extends RemindersPopUp {
    public RemindersPopUpCustom(String title, String description, String dateCreated, String dateForReminder, String typeOfReminder) {
        super(title, description, dateCreated, dateForReminder, typeOfReminder);
    }

    public RemindersPopUpCustom(RemindersPopUp popUp) {
        super(popUp);
    }


    //red for monthly
    @Override
    public void styleReminderPopUp(HBox firstline, Label typeOfReminderLbl) {
        firstline.setStyle("-fx-background-color: #ffc2c2;-fx-background-radius: 10px 10px 0px 0px;");
        typeOfReminderLbl.setText(getTypeOfReminder()+" Reminder");
    }


    @Override
    public LocalDate setNextDateForReminder() {
        String formattedDate = "";
        String[] temp = typeOfReminder.split("[ ]");
        if (temp.length!=2){ return null; }
        if (typeOfReminder.endsWith("DAYS")){
            dateForReminder = dateForReminder.plusDays(Integer.parseInt(temp[0]));
        }else if(typeOfReminder.endsWith("WEEKS")){
            dateForReminder = dateForReminder.plusWeeks(Integer.parseInt(temp[0]));
        }else if(typeOfReminder.endsWith("MONTHS")){
            dateForReminder = dateForReminder.plusMonths(Integer.parseInt(temp[0]));
        }else if(typeOfReminder.endsWith("YEARS")) {
            dateForReminder = dateForReminder.plusYears(Integer.parseInt(temp[0]));
        }else {return null;}
        return dateForReminder;
    }
}
