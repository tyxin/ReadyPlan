package ReadyPlan.model.Tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TasksSetUpDaily extends TasksSetUpTemplate {
    public TasksSetUpDaily(String taskTitle, String taskDescription, String date, String repeat, String isCompleted) {
        super(taskTitle, taskDescription, date, repeat, isCompleted);
    }

    public TasksSetUpDaily(String taskTitle, String date, String repeat, String isCompleted) {
        super(taskTitle, date, repeat, isCompleted);
    }

    @Override
    public LocalDate updateNextDate() {
        dateForTask = dateForTask.plusDays(1);
        return dateForTask;
    }
}
