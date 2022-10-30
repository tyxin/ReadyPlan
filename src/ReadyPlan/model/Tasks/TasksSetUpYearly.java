package ReadyPlan.model.Tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TasksSetUpYearly extends TasksSetUpTemplate {
    public TasksSetUpYearly(String taskTitle, String taskDescription, String date, String repeat, String isCompleted) {
        super(taskTitle, taskDescription, date, repeat, isCompleted);
    }

    public TasksSetUpYearly(String taskTitle, String date, String repeat, String isCompleted) {
        super(taskTitle, date, repeat, isCompleted);
    }

    @Override
    public LocalDate updateNextDate() {
        dateForTask = dateForTask.plusYears(1);
        return dateForTask;
    }
}
