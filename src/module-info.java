module ReadyPlan {
    requires javafx.fxml;
    requires javafx.controls;
    opens ReadyPlan;
    exports ReadyPlan;
    opens ReadyPlan.controller to javafx.fxml;
}