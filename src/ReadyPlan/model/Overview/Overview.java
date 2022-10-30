package ReadyPlan.model.Overview;

import ReadyPlan.model.Reminders.RemindersPopUp;
import ReadyPlan.model.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Overview {

    private ArrayList<OverviewSetUp> overviewList;

    public Overview(){
        this.overviewList = new ArrayList<OverviewSetUp>();
        loadOverViewList();
    }

    public void loadOverViewList() {
        overviewList.clear();
        OverviewSetUp[] arrayOverviewList = {null,null,null,null,null,null,null};
        DayOfWeek currentDay = LocalDate.now().getDayOfWeek().minus(1);
        LocalDate currentDate = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        //current problem is other dates are null (those not on the day itself)
        for (int j = 0;j<7;j++){
            DayOfWeek tempDay = currentDay.plus(j+1);
            LocalDate tempDate = currentDate.plusDays(j+1);
            String formatDate = tempDate.format(formatter);
            ArrayList<Object> tempReminderOverview = new ArrayList<Object>();
            for (int i = 0;i<User.getRemindersUser().getRemindersPopUpDB().size();i++){
                if (User.getRemindersUser().getRemindersPopUpDB().get(i).getDateForReminder().equals(formatDate)){
                    tempReminderOverview.add(User.getRemindersUser().getRemindersPopUpDB().get(i));
                }
            }

            for (int l = 0;l<User.getTasksUser().getListOfTasks().size();l++){
                for (int k = 0;k<User.getTasksUser().getListOfTasksInLists().get(l).size();k++){
                    if (User.getTasksUser().getListOfTasksInLists().get(l).get(k).getDateForTask().isEqual(tempDate)){
                        tempReminderOverview.add(User.getTasksUser().getListOfTasksInLists().get(l).get(k));
                    }
                }
            }
            arrayOverviewList[tempDay.getValue()-1] = new OverviewSetUp(tempDay.toString(),tempReminderOverview);
        }

        for (int i = 0;i<7;i++){
            overviewList.add(arrayOverviewList[i]);
        }
    }

    public ArrayList<OverviewSetUp> getOverviewList() { return overviewList; }

}
