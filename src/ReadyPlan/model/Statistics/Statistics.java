package ReadyPlan.model.Statistics;

import ReadyPlan.model.User;

import java.util.ArrayList;

public class Statistics {

    private ArrayList<StatisticsTaskSetUp> statisticsList;

    //to be called only after tasks
    public Statistics(){
        this.statisticsList = new ArrayList<StatisticsTaskSetUp>();
        loadStatisticsList();
    }

    //to be loaded every time change to tab cause tasks might be updated
    public void loadStatisticsList() {
        statisticsList.clear();
        for (int i = 0;i< User.getTasksUser().getListOfTasks().size();i++){
            int noCompletedTasks = User.getTasksUser().getListOfCompletedTasksInLists().get(i).size();
            int noTotalTasks = User.getTasksUser().getListOfAllTasksInLists().get(i).size();
            statisticsList.add(new StatisticsTaskSetUp("Task List: "+User.getTasksUser().getListOfTasks().get(i),noCompletedTasks,noTotalTasks));
        }
        System.out.println(statisticsList);
    }

    public ArrayList<StatisticsTaskSetUp> getStatisticsList() { return statisticsList; }
}
