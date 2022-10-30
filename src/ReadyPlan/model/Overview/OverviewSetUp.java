package ReadyPlan.model.Overview;

import ReadyPlan.Main;
import ReadyPlan.controller.ControllerReminders;
import ReadyPlan.controller.ControllerTasks;
import ReadyPlan.model.Reminders.RemindersPopUp;
import ReadyPlan.model.Tasks.TasksSetUpTemplate;
import ReadyPlan.model.User;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.ObjectExpression;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Callable;

public class OverviewSetUp {
    private String dayOfWeek;
    private ArrayList<Object> listForDay;

    public OverviewSetUp(String dayOfWeek, ArrayList<Object> listForDay){
        this.dayOfWeek = dayOfWeek;
        if (checkValidListForDay(listForDay)){
            this.listForDay = listForDay;
        }else{ System.out.println("Invalid list"); }
        //else should not be called
    }

    public OverviewSetUp(OverviewSetUp overviewSetUp){
        this(overviewSetUp.getDayOfWeek(),overviewSetUp.getListForDay());
    }

    //parameters to account for the two different sizes of overview set ups
    public VBox setUpOverview(double width, double height){
        VBox overview = new VBox();
        overview.setStyle("-fx-border-radius: 15px;-fx-font-family: 'Lucida Sans';" +
                "-fx-font-size: 15px;-fx-background-radius: 15px; -fx-background-color: #ffe3c2;" +
                "-fx-font-fill: #212121; -fx-effect: dropshadow(gaussian,#c9c9c9,2,2,2,2);");
        overview.setPrefSize(width,height);
        Label dayOfWeekLbl = new Label(dayOfWeek);
        dayOfWeekLbl.setAlignment(Pos.CENTER);
        dayOfWeekLbl.setMinSize(width,30);
        dayOfWeekLbl.setStyle("-fx-background-color: #ffe3c2;-fx-background-radius: 10px 10px 0px 0px;-fx-font-size: 18px;");
        VBox overviewData = new VBox();
        overviewData.setSpacing(5);
        ScrollPane overviewScrollPane = new ScrollPane(overviewData);
        overviewScrollPane.setMinSize(width,height-30);
        for (int i = 0;i<listForDay.size();i++){
            HBox oneTaskOrReminder = new HBox();
            oneTaskOrReminder.setSpacing(5);
            oneTaskOrReminder.setPadding(new Insets(5,0,0,10));
            if (listForDay.get(i) instanceof RemindersPopUp){
                Button typeTaskReminder = new Button("Reminder");
                typeTaskReminder.setMinWidth(100);
                typeTaskReminder.setStyle("-fx-border-color: #ffd6ff; -fx-background-radius: 10px; " +
                        "-fx-border-radius: 10px; -fx-border-width: 3px; -fx-background-color: #f5f5f5");
                Label taskReminderLbl = new Label(((RemindersPopUp) listForDay.get(i)).getTitle());
                taskReminderLbl.setPadding(new Insets(8,0,0,0));
                oneTaskOrReminder.getChildren().addAll(typeTaskReminder,taskReminderLbl);
                typeTaskReminder.setOnMouseClicked(e->{
                    try {
                        ((ControllerReminders) Main.changePage("/ReadyPlan/view/fxml/remindersPage.fxml","/ReadyPlan/view/css/designReminders.css")).initializeUserData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                oneTaskOrReminder.setOnMouseClicked(e->{
                    try {
                        ((ControllerReminders) Main.changePage("/ReadyPlan/view/fxml/remindersPage.fxml","/ReadyPlan/view/css/designReminders.css")).initializeUserData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }else if (listForDay.get(i) instanceof TasksSetUpTemplate){
                Button typeTaskReminder = new Button("Task");
                typeTaskReminder.setMinWidth(100);
                typeTaskReminder.setStyle("-fx-border-color: #dcd6ff; -fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px; -fx-border-width: 3px; -fx-background-color: #f5f5f5");
                String taskListTitle = findTaskListTitle((TasksSetUpTemplate) listForDay.get(i));
                Label taskReminderLbl = new Label(taskListTitle+" : "+((TasksSetUpTemplate) listForDay.get(i)).getTaskTitle());
                taskReminderLbl.setPadding(new Insets(8,0,0,0));
                oneTaskOrReminder.getChildren().addAll(typeTaskReminder,taskReminderLbl);
                typeTaskReminder.setOnMouseClicked(e->{
                    try {
                        ((ControllerTasks)Main.changePage("/ReadyPlan/view/fxml/tasksPage.fxml","/ReadyPlan/view/css/designTasks.css")).initializeUserData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                oneTaskOrReminder.setOnMouseClicked(e->{
                    try {
                        ((ControllerTasks)Main.changePage("/ReadyPlan/view/fxml/tasksPage.fxml","/ReadyPlan/view/css/designTasks.css")).initializeUserData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            oneTaskOrReminder.setOnMouseEntered(e->{
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),oneTaskOrReminder);
                scaleTransition.setToX(1.05);
                scaleTransition.setToY(1.05);
                scaleTransition.setCycleCount(1);
                scaleTransition.setAutoReverse(true);
                scaleTransition.play();
            });

            oneTaskOrReminder.setOnMouseExited(e->{
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),oneTaskOrReminder);
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);
                scaleTransition.setCycleCount(1);
                scaleTransition.setAutoReverse(true);
                scaleTransition.play();
            });

            overviewData.getChildren().add(oneTaskOrReminder);
        }
        overview.getChildren().addAll(dayOfWeekLbl,overviewScrollPane);
        return overview;
    }

    private String findTaskListTitle(TasksSetUpTemplate tasksSetUpTemplate){
        String taskTitle = "";
        for (int i = 0;i< User.getTasksUser().getListOfTasksInLists().size();i++){
            if (User.getTasksUser().getListOfTasksInLists().get(i).contains(tasksSetUpTemplate)){
                taskTitle = User.getTasksUser().getListOfTasks().get(i);
                break;
            }
        }
        return taskTitle;
    }

    private boolean checkValidListForDay(ArrayList<?> listForDay) {
        for (int i = 0;i<listForDay.size();i++){
            if ((listForDay.get(i) instanceof RemindersPopUp)||(listForDay.get(i) instanceof TasksSetUpTemplate)){
            }else{ return false;}
        }
        return true;
    }

    public String getDayOfWeek() { return dayOfWeek; }

    public ArrayList<Object> getListForDay() { return listForDay; }

    @Override
    public String toString() {
        return "OverviewSetUp{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", listForDay=" + listForDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverviewSetUp that = (OverviewSetUp) o;
        return dayOfWeek.equals(that.dayOfWeek) &&
                listForDay.equals(that.listForDay);
    }

}
