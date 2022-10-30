package ReadyPlan.model.Tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TasksSetUpMonthly extends TasksSetUpTemplate {
    public TasksSetUpMonthly(String taskTitle, String taskDescription, String date, String repeat, String isCompleted) {
        super(taskTitle, taskDescription, date, repeat, isCompleted);
    }

    public TasksSetUpMonthly(String taskTitle, String date, String repeat, String isCompleted) {
        super(taskTitle, date, repeat, isCompleted);
    }

    @Override
    public LocalDate updateNextDate() {
        dateForTask = dateForTask.plusMonths(1);
        return dateForTask;
    }
}
