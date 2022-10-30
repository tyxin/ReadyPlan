package ReadyPlan.model.Security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SecurityUser {
    private String name;
    private String password;
    private String email;
    private String description;

    public SecurityUser(String name, String password, String email, String description){
        if (checkValidName(name)&&checkValidEmail(email)&&checkNoComma(description,password)){
            this.name = name;
            this.password = password;
            this.email = email;
            this.description = description;
        }
    }

    public SecurityUser(String name, String password, String email){
        this(name,password,email,"");
    }

    //checks whether name is all letters and spaces
    public static boolean checkValidName(String name){
        /*
        for (int i = 0;i<name.length();i++){
            char temp = name.charAt(i);
            if (Character.isLetter(temp)||temp==' '){
                continue;
            }else{return false;}
        }
        return true;
         */
        String regex = "[a-zA-Z ]+";
        return name.matches(regex);
    }

    //makes sure email only has one @ and has the domain nushigh.edu.sg
    public static boolean checkValidEmail(String email){
        /*
        String[] temp = email.split("[@]");
        if (temp.length!=2){
            return false;
        }else{
            if (temp[1].equals("nushigh.edu.sg")){
                return true;
            }else{return false;}
        }
         */
        String regex = "h[1-9]\\d*@nushigh.edu.sg";
        return email.matches(regex);
    }

    public static boolean checkNoComma(String description, String password){
        String regex = "([.[^,]])*";
        return description.matches(regex)&&password.matches(regex);
    }

    //no setter for name, cannot edit
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    //update password to text file
    public void updatePassword(String password) {
        this.password = password;
        File file = new File("src/ReadyPlan/fileResources/security/userDataSecurity.txt");
        String writeBack = "";
        try {
            Scanner input = new Scanner(file);
            String tempUserData = "";
            while(input.hasNextLine()){
                tempUserData = input.nextLine();
                String[] temp = tempUserData.split("[,]");
                if (temp[0].equals(name)){
                    writeBack+=temp[0]+","+this.password+","+temp[2]+","+temp[3]+"\n";
                }else{
                    writeBack+=tempUserData+"\n";
                }
            }
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/security/userDataSecurity.txt");
            output.print(writeBack);
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    //update email to text file
    public void updateEmail(String email) {
        this.email = email;
        File file = new File("src/ReadyPlan/fileResources/security/userDataSecurity.txt");
        String writeBack = "";
        try {
            Scanner input = new Scanner(file);
            String tempUserData = "";
            while(input.hasNextLine()){
                tempUserData = input.nextLine();
                String[] temp = tempUserData.split("[,]");
                if (temp[0].equals(name)){
                    writeBack+=temp[0]+","+temp[1]+","+this.email+","+temp[3]+"\n";
                }else{
                    writeBack+=tempUserData+"\n";
                }
            }
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        //write back data without file deleted name
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/security/userDataSecurity.txt");
            output.print(writeBack);
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    //update description to text file
    public void updateDescription(String description) {
        this.description = description;
        File file = new File("src/ReadyPlan/fileResources/security/userDataSecurity.txt");
        String writeBack = "";
        try {
            Scanner input = new Scanner(file);
            String tempUserData = "";
            while(input.hasNextLine()){
                tempUserData = input.nextLine();
                String[] temp = tempUserData.split("[,]");
                if (temp[0].equals(name)){
                    writeBack+=temp[0]+","+temp[1]+","+temp[2]+","+this.description+"\n";
                }else{
                    writeBack+=tempUserData+"\n";
                }
            }
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        //write back data without file deleted name
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/security/userDataSecurity.txt");
            output.print(writeBack);
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }



    @Override
    public String toString() {
        return name+","+password+","+email+","+description;
    }
}
