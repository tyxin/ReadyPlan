package ReadyPlan.model.Reminders;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RemindersPopUpDaily extends RemindersPopUp {

    public RemindersPopUpDaily(String title, String description, String dateCreated, String dateForReminder, String typeOfReminder) {
        super(title, description, dateCreated, dateForReminder, typeOfReminder);
    }

    public RemindersPopUpDaily(RemindersPopUp popUp) {
        super(popUp);
    }

    //purple color for daily
    @Override
    public void styleReminderPopUp(HBox firstline, Label typeOfReminderLbl) {
        firstline.setStyle("-fx-background-color: #ffd6ff;-fx-background-radius: 10px 10px 0px 0px;");
        typeOfReminderLbl.setText(getTypeOfReminder()+" Reminder");
    }

    @Override
    public LocalDate setNextDateForReminder() {
        dateForReminder = dateForReminder.plusDays(1);
        return dateForReminder;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
