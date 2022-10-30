package ReadyPlan.model.Reminders;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class RemindersPopUpNoRepeat extends RemindersPopUp {
    private boolean isCompleted;
    public RemindersPopUpNoRepeat(String title, String description, String dateCreated, String dateForReminder, String typeOfReminder) {
        super(title, description, dateCreated, dateForReminder, typeOfReminder);
    }

    public RemindersPopUpNoRepeat(RemindersPopUp popUp) {
        super(popUp);
    }

    //orange for monthly
    @Override
    public void styleReminderPopUp(HBox firstline, Label typeOfReminderLbl) {
        firstline.setStyle("-fx-background-color: #ffe3c2;-fx-background-radius: 10px 10px 0px 0px;");
        typeOfReminderLbl.setText(getTypeOfReminder()+" Reminder");
    }


    @Override
    public LocalDate setNextDateForReminder() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RemindersPopUpNoRepeat that = (RemindersPopUpNoRepeat) o;
        return isCompleted == that.isCompleted;
    }
}
