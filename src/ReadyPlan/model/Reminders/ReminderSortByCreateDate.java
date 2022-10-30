package ReadyPlan.model.Reminders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class ReminderSortByCreateDate implements Comparator<RemindersPopUp> {
    @Override
    public int compare(RemindersPopUp o1, RemindersPopUp o2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate o1DateCreate = LocalDate.parse(o1.getDateCreated(),formatter);
        LocalDate o2DateCreate = LocalDate.parse(o2.getDateCreated(),formatter);
        if (o1DateCreate.isBefore(o2DateCreate)){
            return 1;
        }else if(o1DateCreate.isAfter(o2DateCreate)){
            return -1;
        }else{ return 0;}
    }
}
