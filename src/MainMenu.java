import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * MainMenu Class constructs the GUI for the MainMenu to display how to add/load questions, save
 * current questions, select a topic and number of questions, as well as to start the quiz.
 * 
 * @author ATeam-99
 *
 */
public class MainMenu extends Main {

  private Stage primaryStage; // stage being displayed on
  private BorderPane root; // BorderPane being constructed
  private Button add; // add button
  private Button save; // save button
  private Button start; // start quiz button
  private ComboBox<String> questionBox; // combobox displaying amount of questions
  private Alert alert; // alert displayed when improperly starting quiz
  private ListView<String> selected;
  private int maxNumber;
  private MenuButton choices;
  private List<CheckMenuItem> topics;
  private TextField numberOfQuestions;
  /**
   * MainMenu Constructor that declares the field variables
   * 
   * @param primaryStage - stage being displayed on
   */
  public MainMenu(Stage primaryStage) {
    this.primaryStage = primaryStage;
    root = new BorderPane();
    add = new Button("Add/Load Questions");
    save = new Button("Save Questions");
    start = new Button("START QUIZ");
    selected = new ListView<>();
    topics = new ArrayList<>();
  }

  /**
   * Initalize MainMenu
   * @return root
   */
  public BorderPane initialize() {

    setBackground();
    setTopPanel();
    setLeftPanel();
    setRightPanel();
    setBottomPanel();
    
    return root;
  }
  
  /**
   * Sets the background to an image
   */
  private void setBackground() {
    BackgroundSize bSize =
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
    Image image1 = new Image("uwCrest_Dope.png");
    Background background = new Background(new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
    
    root.setBackground(background);
  }
  
  /**
   * Constructs the top panel in the BorderPane
   */
  private void setTopPanel() {
    // Labels
    Label label = new Label("Main Menu");
    Label numQuestions = new Label(getQuestion().getSize() + " questions available");

    // Style
    numQuestions.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

    // Top Panel
    HBox topPanel = new HBox(label, numQuestions);
    topPanel.setPadding(new Insets(10, 50, 10, 50));
    topPanel.setSpacing(100);
    topPanel.setAlignment(Pos.CENTER);
    topPanel.setStyle("-fx-background-color: #9fb983");
    
    root.setTop(topPanel);
  }
  
  /**
   * Constructs the left panel in the BorderPane
   */
  private void setLeftPanel() {
    // Style
    add.setPrefWidth(180);
    save.setPrefWidth(180);
    
    // Listeners
    save.setOnAction(event -> primaryStage.setScene(Main.getSaveScene()));
    add.setOnAction(event -> primaryStage.setScene(Main.getAddScene()));
    
    // Left Panel
    VBox leftVBox = new VBox(add, save);
    leftVBox.setAlignment(Pos.TOP_RIGHT);
    leftVBox.setPadding(new Insets(20, 0, 0, 20));
    
    root.setLeft(leftVBox);
  }
  
  /**
   * Constructs the right panel in the BorderPane
   */
  private void setRightPanel() {

    // Topics ComboBox
    choices = new MenuButton ( "Set Topics" ) ;
    choices.setPrefWidth(180);

    // List of topics
    topics.clear(); // clear list before adding more topics to avoid repeats

    for (String s : Main.getQuestion().getTopics()){
      topics.add(new CheckMenuItem(s));
    }
    
    // Clear selected so that chosen topic questions aren't still there when you go back and
    // load more questions
    selected.getItems().clear();
    
    choices.getItems().addAll(topics);

    for  (CheckMenuItem item : topics)  {
      item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
        if (newValue)
          selected.getItems().add(item.getText());
        else
          selected.getItems().remove(item.getText());
      });
    }

    // Number of Questions TextField
    numberOfQuestions = new TextField();
    numberOfQuestions.setPromptText("Set # Questions");

    // Right Panel
    VBox rightVBox = new VBox(choices, numberOfQuestions);
    rightVBox.setAlignment(Pos.TOP_RIGHT);
    rightVBox.setPadding(new Insets(20, 20, 0, 0));

    root.setRight(rightVBox);
  }
  
  /**
   * Constructs the bottom panel in the BorderPane
   */
  private void setBottomPanel() {
    // Style
    start.setPrefSize(200, 50);
    start.setOnMouseEntered(e -> start.setStyle("-fx-font-size: 14pt;"));
    start.setOnMouseExited(e -> start.setStyle("-fx-font-size: 12pt;"));

    // Listeners
    start.setOnAction(event -> {

      // Error Message
      if(selected.getItems().isEmpty() || numberOfQuestions.getText().isEmpty()) {
        alert = new Alert(Alert.AlertType.ERROR, "Select a topic and number of questions");
        alert.setHeaderText("Error.");
        alert.showAndWait().filter(response -> response == ButtonType.OK);
        primaryStage.setScene(Main.getMainScene());
      }

      // Continue onto quiz
      else {

        // to store our final questions to be asked
        ArrayList<Question.QuestionNode> questions = new ArrayList<>();

        Random rand = new Random();

        // Pick Random Questions
        for (int i = 0 ; i < Integer.parseInt(numberOfQuestions.getText()) ; i++){
          String topic = selected.getItems().get(rand.nextInt(selected.getItems().size()));
          int questionNum = rand.nextInt(Main.getQuestion().getQuestions(topic).size());
          questions.add(Main.getQuestion().getQuestions(topic).get(questionNum));
        }

        Main.setupQuizScene(questions);
        Main.setupStatisticsScene(Integer.parseInt(numberOfQuestions.getText()));

        // Reset all topics and #'s
        for (CheckMenuItem item : topics){
          item.setSelected(false);
        }

        numberOfQuestions.clear();

        primaryStage.setScene(Main.getQuizScene());
      }
    });
    
    // Bottom Panel
    HBox bottomHBox = new HBox(start);
    bottomHBox.setPadding(new Insets(0, 0, 65, 0));
    bottomHBox.setAlignment(Pos.CENTER);
    
    root.setBottom(bottomHBox);
  }
}
