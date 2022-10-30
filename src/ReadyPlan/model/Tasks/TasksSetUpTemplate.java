package ReadyPlan.model.Tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public abstract class TasksSetUpTemplate {
    private String taskTitle;
    private String taskDescription;
    private boolean isCompleted;
    protected LocalDate dateForTask;
    protected String repeat;

    protected TasksSetUpTemplate(String taskTitle, String taskDescription, String date, String repeat, String isCompleted){
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.dateForTask = formatDate(date);
        this.repeat = repeat;
        if (isCompleted.equals("true")){
            this.isCompleted = true;
        }else {this.isCompleted = false;}
    }

    public LocalDate formatDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate formattedDate = LocalDate.parse(date,formatter);
        return formattedDate;
    }

    protected TasksSetUpTemplate(String taskTitle, String date, String repeat, String isCompleted){
        this(taskTitle,"",date,repeat,isCompleted);
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean isCompleted() { return isCompleted; }

    public void setCompleted(boolean completed) { isCompleted = completed; }


    //update reminder to next date once current date is over, if any, or else mark completed

    public abstract LocalDate updateNextDate();

    public LocalDate getDateForTask() { return dateForTask; }

    /*
    public void updateDateForTask(LocalDate dateForTask, String taskListTitle) {
        this.dateForTask = dateForTask;
        File file = new File("src/ReadyPlan/tasks_content/"+Security.getCurrentUserNameFormat()+"_"+taskListTitle+".txt");
        String writeBack = "";
        try {
            Scanner input = new Scanner(file);
            String tempFileName = "";
            while(input.hasNextLine()){
                tempFileName = input.nextLine();
                String[] temp = tempFileName.split("[,]");
                if (temp[0].equals(taskTitle)){
                    writeBack+=temp[0]+","+taskDescription+"\n";
                }else{
                    writeBack+=tempFileName+"\n";
                }
            }
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        //write back data without file deleted name
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/tasks_content/"+Security.getCurrentUserNameFormat()+"_"+taskListTitle+".txt");
            output.print(writeBack);
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

     */

    public String getRepeat() { return repeat; }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setDateForTask(LocalDate dateForTask) {
        this.dateForTask = dateForTask;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateForTask =  dateForTask.format(formatter);
        return taskTitle+","+taskDescription+","+formattedDateForTask+","+repeat+","+isCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksSetUpTemplate that = (TasksSetUpTemplate) o;
        return isCompleted == that.isCompleted &&
                taskTitle.equals(that.taskTitle) &&
                taskDescription.equals(that.taskDescription) &&
                dateForTask.equals(that.dateForTask) &&
                repeat.equals(that.repeat);
    }

}
