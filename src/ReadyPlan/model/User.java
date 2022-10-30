package ReadyPlan.model;

import ReadyPlan.model.Notes.Notes;
import ReadyPlan.model.Overview.Overview;
import ReadyPlan.model.Reminders.Reminders;
import ReadyPlan.model.Security.Security;
import ReadyPlan.model.Statistics.Statistics;
import ReadyPlan.model.Tasks.Tasks;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private static Reminders remindersUser;
    private static Notes notesUser;
    private static Tasks tasksUser;
    private static Statistics statisticsUser;
    private static Overview overviewUser;
    private static Security secure = new Security("src/ReadyPlan/fileResources/security/userDataSecurity.txt");

    public static void initializeUser() {
        remindersUser = new Reminders("src/ReadyPlan/fileResources/reminders/RemindersData" + Security.getCurrentUserNameFormat() + ".txt");
        updateRemindersDate();
        notesUser = new Notes("src/ReadyPlan/fileResources/notes/NotesTitles" + Security.getCurrentUserNameFormat() + ".txt");
        tasksUser = new Tasks("src/ReadyPlan/fileResources/tasks/TasksLists" + Security.getCurrentUserNameFormat() + ".txt");
        updateTasksDateAndCompletion();
        statisticsUser = new Statistics();
        overviewUser = new Overview();
    }

    private static void updateRemindersDate(){
        System.out.println(User.getRemindersUser().getRemindersPopUpDB());
        for (int i = 0;i<User.getRemindersUser().getRemindersPopUpDB().size();i++){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.println(User.getRemindersUser().getRemindersPopUpDB().get(i).getDateForReminder());
            LocalDate dateReminder = LocalDate.parse(User.getRemindersUser().getRemindersPopUpDB().get(i).getDateForReminder(),formatter);
            System.out.println(dateReminder+"\n");
            if (dateReminder.isBefore(LocalDate.now())){
                if (!User.getRemindersUser().getRemindersPopUpDB().get(i).getTypeOfReminder().equals("NO REPEAT")){
                    LocalDate updatedDate;
                    do {
                        updatedDate = User.getRemindersUser().getRemindersPopUpDB().get(i).setNextDateForReminder();
                        User.getRemindersUser().getRemindersPopUpDB().get(i).setDateForReminder(updatedDate);
                    }while(updatedDate.isBefore(LocalDate.now()));
                }
            }
        }
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/reminders/RemindersData"+Security.getCurrentUserNameFormat()+".txt");
            for (int i = 0;i<User.getRemindersUser().getRemindersPopUpDB().size();i++){
                output.println(User.getRemindersUser().getRemindersPopUpDB().get(i));
            }
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private static void updateTasksDateAndCompletion(){
        for (int i = 0;i<User.getTasksUser().getListOfTasks().size();i++){
            for (int j = 0;j<User.getTasksUser().getListOfTasksInLists().get(i).size();j++){
                if (User.getTasksUser().getListOfTasksInLists().get(i).get(j).getDateForTask().isBefore(LocalDate.now())){
                    if (!User.getTasksUser().getListOfTasksInLists().get(i).get(j).getRepeat().equals("NO REPEAT")) {
                        LocalDate updatedDate;
                        do {
                            updatedDate = User.getTasksUser().getListOfTasksInLists().get(i).get(j).updateNextDate();
                            User.getTasksUser().getListOfTasksInLists().get(i).get(j).setDateForTask(updatedDate);
                        } while (updatedDate.isBefore(LocalDate.now()));
                    }
                }
            }
        }

        for (int i = 0;i<User.getTasksUser().getListOfTasks().size();i++){
            for (int j = 0;j<User.getTasksUser().getListOfAllTasksInLists().get(i).size();j++){
                if (User.getTasksUser().getListOfAllTasksInLists().get(i).get(j).getDateForTask().isBefore(LocalDate.now())){
                    if (!User.getTasksUser().getListOfAllTasksInLists().get(i).get(j).getRepeat().equals("NO REPEAT")){
                        LocalDate updatedDate;
                        do {
                            updatedDate = User.getTasksUser().getListOfAllTasksInLists().get(i).get(j).updateNextDate();
                            User.getTasksUser().getListOfAllTasksInLists().get(i).get(j).setDateForTask(updatedDate);
                        }while(updatedDate.isBefore(LocalDate.now()));
                    }else{
                        //if date over, task is completed
                        User.getTasksUser().getListOfAllTasksInLists().get(i).get(j).setCompleted(true);
                    }
                }
            }
        }

        try {
            for (int i = 0;i < User.getTasksUser().getListOfTasks().size();i++){
                PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/tasks_content/"+Security.getCurrentUserNameFormat()+"_"+tasksUser.getListOfTasks().get(i)+".txt");
                for (int j = 0;j<User.getTasksUser().getListOfAllTasksInLists().get(i).size();j++){
                    output.println(User.getTasksUser().getListOfAllTasksInLists().get(i).get(j));
                }
                output.close();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Reminders getRemindersUser() {
        return remindersUser;
    }

    public static Notes getNotesUser() {
        return notesUser;
    }

    public static Tasks getTasksUser() {
        return tasksUser;
    }

    public static Security getSecure() {
        return secure;
    }

    public static Statistics getStatisticsUser() { return statisticsUser; }

    public static Overview getOverviewUser() { return overviewUser; }
}
