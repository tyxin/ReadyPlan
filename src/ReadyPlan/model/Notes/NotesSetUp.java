package ReadyPlan.model.Notes;

import ReadyPlan.model.Security.Security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;

public class NotesSetUp {
    private String noteTitle;
    private String noteContent;
    //possible using date, abstract

    /**
     * note titles cannot be renamed, content can be updated
     * notes are stored in separate files named title.txt under notes folder
     * @param noteTitle
     * @param noteContent
     */
    public NotesSetUp(String noteTitle, String noteContent){
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public NotesSetUp(String noteTitle){
        this(noteTitle,"");
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    /**
     * update text files also
     * @param noteContent
     */
    public void updateNoteContent(String noteContent) {
        this.noteContent = noteContent;
        try {
            PrintWriter output = new PrintWriter("src/ReadyPlan/fileResources/notes_content/"+ Security.getCurrentUserNameFormat()+"_"+noteTitle+".txt");
            //overwrites current content
            output.print(noteContent);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "NotesSetUp{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotesSetUp that = (NotesSetUp) o;
        return Objects.equals(noteTitle, that.noteTitle) &&
                Objects.equals(noteContent, that.noteContent);
    }

}
