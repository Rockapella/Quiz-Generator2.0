import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AddMenu extends Main implements EventHandler<ActionEvent> {

    Stage primaryStage;
    Button back;
    Button submit;

    public AddMenu(Stage primaryStage) {
      this.primaryStage = primaryStage;
      submit = new Button("SUBMIT");
      back = new Button("BACK");
    }
    
    public BorderPane initialize() {
      Label label = new Label("Add Menu");
      Label numQuestions = new Label("N Questions available");
      numQuestions.setFont(Font.font("Arial", FontWeight.BOLD, 16));
      label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

      BorderPane root = new BorderPane();

      Label topicLabel = new Label("Enter Topic:");
      TextField topic = new TextField("Enter Topic here");
      Label questionLabel = new Label("Enter Question:");
      TextField question = new TextField("Enter Question here");
      Label answerLabel = new Label("Enter Answer:");
      TextField answer = new TextField("Enter Answer here");
      Label or = new Label("OR");
      or.setFont(Font.font("Arial", FontWeight.BOLD, 16));
      Label load = new Label("Load from JSON:");
      TextField jsonLoad = new TextField("Enter JSON File name");
      Label blank = new Label("");
      Label blank2 = new Label("");
      
      back.setOnAction(this);
      submit.setOnAction(this);

      VBox centerVBox = new VBox(topicLabel, topic, questionLabel, question, answerLabel, answer,
          blank, or, blank2, load, jsonLoad);
      centerVBox.setPadding(new Insets(80,0,50,50));
      HBox bottomHBox = new HBox(back,submit);
      bottomHBox.setPadding(new Insets(0,0,30,335));
      
      root.setCenter(centerVBox);
      root.setLeft(label);
      root.setRight(numQuestions);
      root.setBottom(bottomHBox);

      return root;
    }
  
  /**
   * Invoked when a specific event of the type for which this handler is
   * registered happens.
   *
   * @param event the event which occurred
   */
  public void handle(ActionEvent event) {

    // Build main scene
    MainMenu mainMenu = new MainMenu(primaryStage);
    Scene mainScene = new Scene(mainMenu.initialize(),500,500);

    if (event.getSource() == back)
      primaryStage.setScene(mainScene);

    else if (event.getSource() == submit)
      primaryStage.setScene(mainScene);
      // TODO make this formally save or add the questions
  }
}

