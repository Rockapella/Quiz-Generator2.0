import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SaveMenu extends Main implements EventHandler<ActionEvent> {

  private Stage primaryStage;
  private Button back;
  private Button submit;

  // Constructor
  public SaveMenu(Stage primaryStage) {
    this.primaryStage = primaryStage;
    back = new Button("BACK");
    submit = new Button("SUBMIT");
  }

  public BorderPane initialize(){

    Label label = new Label("Save Menu");
    Label numQuestions = new Label("X Questions available");

    // Styling
    numQuestions.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

    // Pane to hold everything
    BorderPane root = new BorderPane();

    Label jsonLabel = new Label("Save as JSON:");
    TextField jsonName = new TextField("Enter JSON File here");

    // Listeners
    back.setOnAction(this);
    submit.setOnAction(this);

    Image one = new Image("150x150blank.png");
    Image two = new Image("100x300blank.png");
    ImageView img = new ImageView(one);
    ImageView img1 = new ImageView(two);
    ImageView img2 = new ImageView(two);

    VBox leftVBox = new VBox(label, img1, back);
    VBox rightVBox = new VBox(numQuestions, img2, submit);
    VBox centerVBox = new VBox(img, jsonLabel, jsonName);
    root.setCenter(centerVBox);
    root.setLeft(leftVBox);
    root.setRight(rightVBox);

    return root;
  }


  /**
   * Invoked when a specific event of the type for which this handler is
   * registered happens.
   *
   * @param event the event which occurred
   */
  @Override
  public void handle(ActionEvent event) {

    MainMenu mainMenu = new MainMenu(primaryStage);
    Scene mainScene = new Scene(mainMenu.initialize(),500,500);

    if (event.getSource() == back){
      primaryStage.setScene(mainScene);
    }
    else if(event.getSource() == submit) {
      primaryStage.setScene(mainScene);
      //TODO actually save the data some time
    }
  }
}
