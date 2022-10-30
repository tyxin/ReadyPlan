package ReadyPlan.model.Tasks;

import java.time.LocalDate;

public class TasksSetUpNoRepeat extends TasksSetUpTemplate{
    public TasksSetUpNoRepeat(String taskTitle, String taskDescription, String date, String repeat, String isCompleted) {
        super(taskTitle, taskDescription, date, repeat, isCompleted);
    }

    public TasksSetUpNoRepeat(String taskTitle, String date, String repeat, String isCompleted) {
        super(taskTitle, date, repeat, isCompleted);
    }

    @Override
    public LocalDate updateNextDate() {
        return null;
    }
}
