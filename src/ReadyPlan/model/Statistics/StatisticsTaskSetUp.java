package ReadyPlan.model.Statistics;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class StatisticsTaskSetUp {
    private String taskListTitle;
    //number of completed tasks
    private int completedTasks;
    //total number of tasks in particular task list
    private int noOfTasks;

    public StatisticsTaskSetUp(String taskListTitle, int completedTasks, int noOfTasks){
        this.taskListTitle = taskListTitle;
        this.completedTasks = completedTasks;
        this.noOfTasks = noOfTasks;
    }

    public StatisticsTaskSetUp(StatisticsTaskSetUp statTaskSetUp){
        this(statTaskSetUp.getTaskListTitle(),statTaskSetUp.getCompletedTasks(),statTaskSetUp.getNoOfTasks());
    }

    public VBox setUpTaskStat(int width, int height){
        VBox taskStat = new VBox();
        taskStat.setStyle("-fx-border-radius: 15px;-fx-font-family: 'Lucida Sans';" +
                "-fx-font-size: 15px;-fx-background-radius: 15px; -fx-background-color: #fffffc;" +
                "-fx-font-fill: #212121; -fx-effect: dropshadow(gaussian,#c9c9c9,2,2,2,2);");
        taskStat.setPrefSize(width,height);
        taskStat.setSpacing(5);
        Label taskListTitleLbl = new Label(taskListTitle);
        taskListTitleLbl.setAlignment(Pos.CENTER);
        taskListTitleLbl.setMinSize(width,30);
        taskListTitleLbl.setMaxSize(height,30);
        taskListTitleLbl.setStyle("-fx-background-color: #ffe3c2;-fx-background-radius: 10px 10px 0px 0px;");
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        double percentCompleted = completedTasks/(double)noOfTasks*100;
        pieChartData.add(new PieChart.Data("Completed",percentCompleted));
        pieChartData.add(new PieChart.Data("Not Completed",100-percentCompleted));
        PieChart taskPieChart = new PieChart(pieChartData);
        taskPieChart.setMaxSize(50,50);
        taskPieChart.setPadding(new Insets(0,5,0,0));
        Label taskListCompletedLbl = new Label("Completed: "+completedTasks);
        Label taskListUnCompletedLbl = new Label("Not Completed: "+(noOfTasks-completedTasks));
        Label taskListTotalTaskLbl = new Label("Total: "+noOfTasks);
        taskListCompletedLbl.setPadding(new Insets(0,0,0,10));
        taskListUnCompletedLbl.setPadding(new Insets(0,0,0,10));
        taskListTotalTaskLbl.setPadding(new Insets(0,0,5,10));

        taskStat.getChildren().addAll(taskListTitleLbl,taskPieChart,taskListCompletedLbl,
                taskListUnCompletedLbl,taskListTotalTaskLbl);

        taskStat.setOnMouseEntered(e->{
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),taskStat);
            scaleTransition.setToX(1.05);
            scaleTransition.setToY(1.05);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();
        });

        taskStat.setOnMouseExited(e->{
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),taskStat);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();
        });
        return taskStat;
    }

    public String getTaskListTitle() { return taskListTitle; }

    public int getCompletedTasks() { return completedTasks; }

    public int getNoOfTasks() { return noOfTasks; }

    @Override
    public String toString() {
        return "StatisticsTaskSetUp{" +
                "taskListTitle='" + taskListTitle + '\'' +
                ", completedTasks=" + completedTasks +
                ", noOfTasks=" + noOfTasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsTaskSetUp taskSetUp = (StatisticsTaskSetUp) o;
        return completedTasks == taskSetUp.completedTasks &&
                noOfTasks == taskSetUp.noOfTasks &&
                taskListTitle.equals(taskSetUp.taskListTitle);
    }

}
