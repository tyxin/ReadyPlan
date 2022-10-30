package ReadyPlan.model.Tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TasksSetUpCustom extends TasksSetUpTemplate{
    public TasksSetUpCustom(String taskTitle, String taskDescription, String date, String repeat, String isCompleted) {
        super(taskTitle, taskDescription, date, repeat, isCompleted);
    }

    public TasksSetUpCustom(String taskTitle, String date, String repeat, String isCompleted) {
        super(taskTitle, date, repeat, isCompleted);
    }

    @Override
    public LocalDate updateNextDate() {
        String[] temp = repeat.split("[ ]");
        if (temp.length!=2){ return null; }
        if (repeat.endsWith("DAYS")){
            dateForTask = dateForTask.plusDays(Integer.parseInt(temp[0]));
        }else if(repeat.endsWith("WEEKS")){
            dateForTask = dateForTask.plusWeeks(Integer.parseInt(temp[0]));
        }else if(repeat.endsWith("MONTHS")){
            dateForTask = dateForTask.plusMonths(Integer.parseInt(temp[0]));
        }else{ return null;}
        return dateForTask;

    }
}
