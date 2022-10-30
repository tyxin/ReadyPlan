package ReadyPlan.model.Tasks;

import ReadyPlan.model.Security.Security;
import ReadyPlan.model.User;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import ReadyPlan.Main;
import ReadyPlan.controller.ControllerTaskList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Tasks {
    private ArrayList<ArrayList<TasksSetUpTemplate>> listOfTasksInLists;
    private ArrayList<ArrayList<TasksSetUpTemplate>> listOfCompletedTasksInLists;
    private ArrayList<ArrayList<TasksSetUpTemplate>> listOfAllTasksInLists;
    private ArrayList<String> listOfTasks;
    private String filename;
    private Alert alert;

    public Tasks(String filename){
        this.filename = filename;
        this.listOfTasks = new ArrayList<String>();
        this.listOfTasksInLists = new ArrayList<>();
        this.listOfCompletedTasksInLists = new ArrayList<>();
        this.listOfAllTasksInLists = new ArrayList<>();
        loadTasksDB(filename);
        loadTasksInLists();
    }

    public void loadTasksDB(String filename){
        File file = new File(filename);
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()){
                listOfTasks.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //load tasks in respective lists
    public void loadTasksInLists(){
        for (int i = 0;i<listOfTasks.size();i++){
            //error here cause empty arraylist
            listOfTasksInLists.add(new ArrayList<TasksSetUpTemplate>());
            listOfCompletedTasksInLists.add(new ArrayList<TasksSetUpTemplate>());
            listOfAllTasksInLists.add(new ArrayList<TasksSetUpTemplate>());
            File file = new File("src/ReadyPlan/fileResources/tasks_content/"+ Security.getCurrentUserNameFormat()+
                    "_"+listOfTasks.get(i)+".txt");
            try {
                Scanner input = new Scanner(file);
                String temp = "";
                while(input.hasNextLine()){
                    temp = input.nextLine();
                    String[] tempTask = temp.split("[,]");
                    if (tempTask[4].equals("false")){
                        System.out.println(Arrays.toString(tempTask));
                        if (tempTask[3].equals("DAILY")){
                            listOfTasksInLists.get(i).add(new TasksSetUpDaily(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else if(tempTask[3].equals("WEEKLY")){
                            listOfTasksInLists.get(i).add(new TasksSetUpWeekly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else if(tempTask[3].equals("MONTHLY")){
                            listOfTasksInLists.get(i).add(new TasksSetUpMonthly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else if(tempTask[3].equals("YEARLY")){
                            listOfTasksInLists.get(i).add(new TasksSetUpYearly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else if(tempTask[3].equals("NO REPEAT")){
                            listOfTasksInLists.get(i).add(new TasksSetUpNoRepeat(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else{
                            listOfTasksInLists.get(i).add(new TasksSetUpCustom(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }
                    }else{
                        if (tempTask[3].equals("DAILY")){
                            listOfCompletedTasksInLists.get(i).add(new TasksSetUpDaily(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else if(tempTask[3].equals("WEEKLY")){
                            listOfCompletedTasksInLists.get(i).add(new TasksSetUpWeekly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else if(tempTask[3].equals("MONTHLY")){
                            listOfCompletedTasksInLists.get(i).add(new TasksSetUpMonthly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else if(tempTask[3].equals("YEARLY")){
                            listOfCompletedTasksInLists.get(i).add(new TasksSetUpYearly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else if(tempTask[3].equals("NO REPEAT")){
                            listOfCompletedTasksInLists.get(i).add(new TasksSetUpNoRepeat(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }else{
                            listOfCompletedTasksInLists.get(i).add(new TasksSetUpCustom(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                        }
                    }
                    if (tempTask[3].equals("DAILY")){
                        listOfAllTasksInLists.get(i).add(new TasksSetUpDaily(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                    }else if(tempTask[3].equals("WEEKLY")){
                        listOfAllTasksInLists.get(i).add(new TasksSetUpWeekly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                    }else if(tempTask[3].equals("MONTHLY")){
                        listOfAllTasksInLists.get(i).add(new TasksSetUpMonthly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                    }else if(tempTask[3].equals("YEARLY")){
                        listOfAllTasksInLists.get(i).add(new TasksSetUpYearly(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                    }else if(tempTask[3].equals("NO REPEAT")){
                        listOfAllTasksInLists.get(i).add(new TasksSetUpNoRepeat(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                    }else{
                        listOfAllTasksInLists.get(i).add(new TasksSetUpCustom(tempTask[0],tempTask[1],tempTask[2],tempTask[3],tempTask[4]));
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String processString(String inputTextArea){
        return inputTextArea.replaceAll("\\n","\\\\n");
    }

    private String decodeString(String inputTextArea){
        return inputTextArea.replaceAll("\\\\n","\\n");
    }

    public void deleteTaskList(String taskListTitle) {
        File file = new File("src/ReadyPlan/fileResources/tasks/TasksLists" + Security.getCurrentUserNameFormat() + ".txt");
        int indexOfTaskList = -1;
        for (int i = 0;i<listOfTasks.size();i++){
            if (listOfTasks.get(i).equals(taskListTitle)){
                indexOfTaskList = i;
                break;
            }
        }
        listOfTasks.remove(taskListTitle);
        listOfAllTasksInLists.remove(indexOfTaskList);
        listOfTasksInLists.remove(indexOfTaskList);
        listOfCompletedTasksInLists.remove(indexOfTaskList);
        //write back data without file deleted name
        File fileToDelete = new File("src/ReadyPlan/fileResources/tasks_content/" + Security.getCurrentUserNameFormat() +
                "_"+taskListTitle + ".txt");
        fileToDelete.delete();
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/tasks/TasksLists" +
                    Security.getCurrentUserNameFormat() + ".txt");
            for (int i = 0;i<listOfTasks.size();i++){
                output.println(listOfTasks.get(i));
            }
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteTaskInList(String taskListTitle, TasksSetUpTemplate task) {
        File file = new File("src/ReadyPlan/fileResources/tasks_content/"+ Security.getCurrentUserNameFormat() +
                "_"+taskListTitle + ".txt");
        int indexOfTaskList = -1;
        for (int i = 0;i<listOfTasks.size();i++){
            if (listOfTasks.get(i).equals(taskListTitle)){
                indexOfTaskList = i;
                break;
            }
        }
        listOfTasksInLists.get(indexOfTaskList).remove(task);
        listOfAllTasksInLists.get(indexOfTaskList).remove(task);

        //write back data without deleted task
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/tasks_content/" +
                    Security.getCurrentUserNameFormat() +"_" + taskListTitle+ ".txt");
            for (int i = 0;i<listOfTasksInLists.get(indexOfTaskList).size();i++){
                output.println(listOfTasksInLists.get(indexOfTaskList).get(i));
            }
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void addTaskList(String taskListTitle) {
        boolean isDuplicateTitle = false;
        for (int i = 0; i<listOfTasks.size();i++){
            if (listOfTasks.get(i).equals(taskListTitle)){
                isDuplicateTitle = true;
            }
        }
        if (isDuplicateTitle){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Task List Title Error");
            alert.setHeaderText("There is an existing Task List with the same title");
            alert.setContentText("Please choose another task list title.");
            alert.showAndWait();
        }else{
            File file = new File("src/ReadyPlan/fileResources/tasks_content/" + Security.getCurrentUserNameFormat() +
                    "_"+taskListTitle + ".txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            listOfTasks.add(taskListTitle);
            listOfAllTasksInLists.add(new ArrayList<TasksSetUpTemplate>());
            listOfTasksInLists.add(new ArrayList<TasksSetUpTemplate>());
            listOfCompletedTasksInLists.add(new ArrayList<TasksSetUpTemplate>());
            try {
                PrintWriter output = new PrintWriter(new FileOutputStream("src/ReadyPlan/fileResources/tasks/TasksLists" +
                        Security.getCurrentUserNameFormat() + ".txt",true));
                output.println(taskListTitle);
                output.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void addTaskInList(String taskListTitle, TasksSetUpTemplate task) {
        File file = new File("src/ReadyPlan/fileResources/tasks_content/"+ Security.getCurrentUserNameFormat() +
                "_"+ taskListTitle + ".txt");
        int indexOfTaskList = -1;
        for (int i = 0;i<listOfTasks.size();i++){
            if (listOfTasks.get(i).equals(taskListTitle)){
                indexOfTaskList = i;
                break;
            }
        }
        listOfTasksInLists.get(indexOfTaskList).add(task);
        listOfAllTasksInLists.get(indexOfTaskList).add(task);
        //write task
        try {
            PrintWriter output = new PrintWriter(new FileOutputStream("src/ReadyPlan/fileResources/tasks_content/" +
                    Security.getCurrentUserNameFormat() + "_"+ taskListTitle + ".txt",true));
            output.println(task);
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public boolean updateTaskInList(String taskListTitle, TasksSetUpTemplate task, String newTaskTitle, String newTaskDes,
                                 LocalDate newDateForTask, String newRepeat){
        boolean isSuccess = true;
        if (!newRepeat.equals(task.getRepeat())){
            isSuccess = false;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Task Type Error");
            alert.setHeaderText("Task Type cannot be changed.");
            alert.setContentText("Delete this task and create a new task instead");
            alert.showAndWait();
        }else{
            File file = new File("src/ReadyPlan/fileResources/tasks_content/"+ Security.getCurrentUserNameFormat() +
                    taskListTitle + ".txt");
            // to do -- find task in task list and all task list and set, this way does not affect anything
            int indexOfTaskInList = -1;
            int indexOfTaskList = -1;
            for (int i = 0; i< User.getTasksUser().getListOfTasks().size();i++){
                if (User.getTasksUser().getListOfTasks().get(i).equals(taskListTitle)){
                    indexOfTaskList = i;
                    break;
                }
            }
            for (int j = 0;j<User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).size();j++){
                if (User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).get(j).equals(task)){
                    indexOfTaskInList = j;
                    break;
                }
            }
            User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).get(indexOfTaskInList).setTaskTitle(newTaskTitle);
            User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).get(indexOfTaskInList).setTaskDescription(newTaskDes);
            User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).get(indexOfTaskInList).setDateForTask(newDateForTask);
            //write task
            try {
                PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/tasks_content/" +
                        Security.getCurrentUserNameFormat() +"_" + taskListTitle+ ".txt");
                for (int i = 0;i<User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).size();i++){
                    output.println(User.getTasksUser().getListOfTasksInLists().get(indexOfTaskList).get(i));
                }
                output.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }

    public ArrayList<ArrayList<TasksSetUpTemplate>> getListOfTasksInLists() {
        return listOfTasksInLists;
    }

    public ArrayList<String> getListOfTasks() {
        return listOfTasks;
    }

    public void setListOfTasksInLists(ArrayList<ArrayList<TasksSetUpTemplate>> listOfTasksInLists) {
        this.listOfTasksInLists = listOfTasksInLists;
    }

    public ArrayList<ArrayList<TasksSetUpTemplate>> getListOfCompletedTasksInLists() {
        return listOfCompletedTasksInLists;
    }

    public ArrayList<ArrayList<TasksSetUpTemplate>> getListOfAllTasksInLists() {
        return listOfAllTasksInLists;
    }


}
