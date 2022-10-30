package ReadyPlan.model.Reminders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class ReminderSortByType implements Comparator<RemindersPopUp> {
    @Override
    public int compare(RemindersPopUp o1, RemindersPopUp o2) {
        //no repeat, daily, weekly, monthly, yearly, custom
        //representative value
        final int noRepeat = 0;
        final int daily = 1;
        final int weekly = 2;
        final int monthly = 3;
        final int yearly = 4;
        final int custom = 5;
        int valueo1;
        int valueo2;
        switch (o1.getTypeOfReminder()){
            case "NO REPEAT": valueo1 = noRepeat; break;
            case "DAILY": valueo1 = daily; break;
            case "WEEKLY": valueo1 = weekly; break;
            case "MONTHLY": valueo1 = monthly; break;
            case "YEARLY": valueo1 = yearly; break;
            default: valueo1 = custom; break;
        }

        switch (o2.getTypeOfReminder()){
            case "NO REPEAT": valueo2 = noRepeat; break;
            case "DAILY": valueo2 = daily; break;
            case "WEEKLY": valueo2 = weekly; break;
            case "MONTHLY": valueo2 = monthly; break;
            case "YEARLY": valueo2 = yearly; break;
            default: valueo2 = custom; break;
        }

        return valueo1-valueo2;
    }
}