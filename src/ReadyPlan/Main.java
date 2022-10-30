package ReadyPlan;

import ReadyPlan.model.Overview.OverviewSetUp;
import ReadyPlan.model.Reminders.RemindersPopUpDaily;
import ReadyPlan.model.Statistics.StatisticsTaskSetUp;
import ReadyPlan.model.Tasks.TasksSetUpDaily;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main extends Application {

    private static Stage primaryStage;
    private ProgressBar loadProgress;
    private Label progressText;
    private Label readyPlanLbl;
    private VBox splashVBox;
    private static final int SPLASH_WIDTH = 300;
    private static final int SPLASH_HEIGHT = 200;

    @Override
    public void init() throws Exception {
        ImageView splash = new ImageView(new Image(Main.class.getResource("view/240112.png").toURI().toString()));
        splash.setPreserveRatio(true);
        splash.setFitWidth(250);
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH-25);
        progressText = new Label("Load progress...");
        progressText.setPadding(new Insets(0,0,0,10));
        progressText.setStyle("-fx-font-family: 'Lucida Sans';-fx-font-size: 12;");
        progressText.setPadding(new Insets(0,0,0,10));
        readyPlanLbl = new Label("Ready!Plan");
        readyPlanLbl.setStyle("-fx-font-family: 'Lucida Sans';-fx-font-size: 20px;");
        readyPlanLbl.setPadding(new Insets(0,0,0,10));
        splashVBox = new VBox();
        splashVBox.setAlignment(Pos.CENTER);
        splashVBox.getChildren().addAll(splash,readyPlanLbl,loadProgress,progressText);
        splashVBox.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception{

        /*
        ArrayList<Object> arrlist = new ArrayList<>();
        arrlist.add(new TasksSetUpDaily("tasktitle","description","09/09/2020","DAILY","false"));
        arrlist.add(new RemindersPopUpDaily("reminderTitle","description","15/09/2020","15/09/2020","DAILY"));
        OverviewSetUp testObj = new OverviewSetUp("Monday",arrlist);
        Scene scene = new Scene(testObj.setUpOverview(380,200));
        primaryStage.setScene(scene);
        //primaryStage.setTitle("CS3233 OOPII Lab 1: Digital Clock");
        primaryStage.show();

         */
        final Task<ObservableList<String>> readyPlanTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> dummyString = FXCollections.emptyObservableList();
                updateMessage("Loading Progress...");
                for (int i = 0; i < 101; i++) {
                    Thread.sleep(50);
                    updateProgress(i , 100);
                    updateMessage("Loading Progress..."+ i+"%");
                }
                Thread.sleep(50);
                updateMessage("Loading complete.");
                return dummyString;
            }
        };

        showSplash(
                initStage,
                readyPlanTask,
                () -> {
                    try {
                        showMainStage();
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
        );

        new Thread(readyPlanTask).start();

    }

    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashVBox);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashVBox);
        initStage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT);
        initStage.show();
    }

    public interface InitCompletionHandler {
        public void complete();
    }
    
    private void showMainStage() throws IOException, URISyntaxException {
        Path srcName = Path.of(System.getProperty("user.dir")+"/src");
        if (srcName.toFile().exists()&&srcName.toFile().isDirectory()){
        }else{
            String[] filesToInitialize = {"src","src/ReadyPlan","src/ReadyPlan/fileResources","src/ReadyPlan/fileResources/notes",
                    "src/ReadyPlan/fileResources/notes_content","src/ReadyPlan/fileResources/reminders","src/ReadyPlan/fileResources/security",
                    "src/ReadyPlan/fileResources/tasks","src/ReadyPlan/fileResources/tasks_content"};
            for (int i = 0;i<filesToInitialize.length;i++){
                File file = new File(System.getProperty("user.dir")+"/"+filesToInitialize[i]);
                file.mkdir();
            }
        }
        Main.primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("view/fxml/landingPage.fxml"));
        //primaryStage.getIcons().add(new Image(("file:src/ReadyPlan/view/240112.png")));
        primaryStage.getIcons().add(new Image(Main.class.getResource("view/240112.png").toURI().toString()));
        primaryStage.setTitle("Ready!Plan");
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        scene.getStylesheets().add(Main.class.getResource("view/css/designLanding.css").toExternalForm());
        root.autosize();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Object changePage(String fxmlFile, String cssFile) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource(fxmlFile));
        primaryStage.getScene().setRoot(root.load());
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().getStylesheets().add(Main.class.getResource(cssFile).toExternalForm());
        return root.getController();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
