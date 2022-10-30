package ReadyPlan.model.Security;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Security {
    private ArrayList<SecurityUser> usersDB;
    private String filename;
    private static SecurityUser currentUser;

    public Security(String filename){
        this.filename = filename;
        this.usersDB = new ArrayList<SecurityUser>();
        loadUsersDB(filename);
    }

    public void loadUsersDB(String filename){
        File file = new File(filename);
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()){
                String temp = input.nextLine();
                String[] userTemp = temp.split("[,]");
                System.out.println(Arrays.toString(userTemp));
                SecurityUser tempUser;
                if (userTemp.length==3){
                    tempUser = new SecurityUser(userTemp[0],userTemp[1],userTemp[2],"");
                }else{
                    tempUser = new SecurityUser(userTemp[0],userTemp[1],userTemp[2],userTemp[3]);
                }
                usersDB.add(tempUser);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * add deep copy of user and update text file
     * @param user
     */
    public void addNewUser(SecurityUser user){
        SecurityUser deepCopyUser = new SecurityUser(user.getName(),user.getPassword(),user.getEmail(),user.getDescription());
        usersDB.add(deepCopyUser);
        setCurrentUser(deepCopyUser);
        createFilesUser(deepCopyUser);
        File file = new File(filename);
        try {
            PrintWriter output = new PrintWriter(new FileOutputStream(filename,true));
            output.println(deepCopyUser);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if valid login -- correct name and password
     * logins for users, sets that user input as current user
     * @param name
     * @param password
     * @return
     */
    public boolean validLogin(String name, String password){
        for (int i = 0;i<usersDB.size();i++){
            if (usersDB.get(i).getName().equals(name)){
                if (usersDB.get(i).getPassword().equals(password)){
                    currentUser = usersDB.get(i);
                    return true;
                }else{
                    System.out.println("Invalid password!");
                    return false;
                }
            }
        }
        System.out.println("Invalid name!");
        return false;
    }

    public void createFilesUser(SecurityUser user){
        File notesTitles = new File("src/ReadyPlan/fileResources/notes/NotesTitles"+Security.getCurrentUserNameFormat()+".txt");
        try {
            notesTitles.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File remindersData = new File("src/ReadyPlan/fileResources/reminders/RemindersData"+Security.getCurrentUserNameFormat()+".txt");
        try {
            remindersData.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File tasksList = new File("src/ReadyPlan/fileResources/tasks/TasksLists"+Security.getCurrentUserNameFormat()+".txt");
        try {
            tasksList.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getCurrentUserNameFormat() {
        String[] tempName = currentUser.getName().split("[ ]");
        String nameFormat = "";
        for(int i = 0; i<tempName.length;i++){
            nameFormat+="_"+tempName[i];
        }
        return nameFormat;
    }

    public static SecurityUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(SecurityUser currentUser) {
        Security.currentUser = currentUser;
    }

    public ArrayList<SecurityUser> getUsersDB() {
        return usersDB;
    }


}
