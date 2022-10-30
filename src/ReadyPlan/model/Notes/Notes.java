package ReadyPlan.model.Notes;

import ReadyPlan.model.Security.Security;
import ReadyPlan.model.User;
import javafx.scene.control.Label;
import ReadyPlan.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.util.ArrayList;
import java.util.Scanner;


//for notes, there are public notes that can be shared across the organisation
//users can also have private users that they only have access to
public class Notes {
    private ArrayList<NotesSetUp> notesDB;
    private String filename;

    public Notes(String filename){
        this.filename = filename;
        this.notesDB = new ArrayList<NotesSetUp>();
        loadNotesDB(filename);
    }

    public void loadNotesDB(String filename){
        File file = new File(filename);
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()){
                String temp = input.nextLine();
                File fileNote = new File("src/ReadyPlan/fileResources/notes_content/"+ Security.getCurrentUserNameFormat()+"_"+temp+".txt");
                Scanner inputNote = new Scanner(fileNote);
                String noteContent = "";
                while(inputNote.hasNextLine()){
                    noteContent+=inputNote.nextLine()+"\n";
                }
                NotesSetUp currentNote = new NotesSetUp(temp,noteContent);
                notesDB.add(currentNote);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NotesSetUp> getNotesDB() {
        return notesDB;
    }

    public void deleteNote(NotesSetUp notesSetUp) {
        User.getNotesUser().getNotesDB().remove(notesSetUp);
        //delete corresponding note_content file
        File file = new File("src/ReadyPlan/fileResources/notes_content/" + Security.getCurrentUserNameFormat() +"_"
                + notesSetUp.getNoteTitle()+ ".txt");
        file.delete();
        //write back data without deleted note
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/notes/NotesTitles"+ Security.getCurrentUserNameFormat() + ".txt");
            for (int i = 0;i<User.getNotesUser().getNotesDB().size();i++){
                output.println(User.getNotesUser().getNotesDB().get(i).getNoteTitle());
            }
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void addNote(NotesSetUp notesSetUp){
       User.getNotesUser().getNotesDB().add(notesSetUp);
        File file = new File("src/ReadyPlan/fileResources/notes_content/" + Security.getCurrentUserNameFormat() +"_" + notesSetUp.getNoteTitle()+ ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //write back data with added note
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/notes/NotesTitles"+ Security.getCurrentUserNameFormat() + ".txt");
            for (int i = 0;i<User.getNotesUser().getNotesDB().size();i++){
                output.println(User.getNotesUser().getNotesDB().get(i).getNoteTitle());
            }
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
