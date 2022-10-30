package ReadyPlan.model.Tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TasksSetUpWeekly extends TasksSetUpTemplate {
    public TasksSetUpWeekly(String taskTitle, String taskDescription, String date, String repeat, String isCompleted) {
        super(taskTitle, taskDescription, date, repeat, isCompleted);
    }

    public TasksSetUpWeekly(String taskTitle, String date, String repeat, String isCompleted) {
        super(taskTitle, date, repeat, isCompleted);
    }

    @Override
    public LocalDate updateNextDate() {
        dateForTask = dateForTask.plusWeeks(1);
        return dateForTask;
    }
}