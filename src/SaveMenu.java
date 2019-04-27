import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * SaveMenu Class constructs the GUI for the SaveMenu to display how to save current questions
 * to a json file.
 * 
 * @author ATeam-99
 *
 */
public class SaveMenu extends Main {

  private Stage primaryStage; // stage being displayed on
  private BorderPane root; // BorderPane being constructed
  private Button back; // back button
  private Button submit; // submit button
  private TextField fileName; // TextField to record saved file name
  private Alert alert; // alert to help user save properly


  /**
   * SaveMenu Constructor that declares the field variables and sets the background color
   * 
   * @param primaryStage - stage being displayed on
   */
  public SaveMenu(Stage primaryStage) {
    this.primaryStage = primaryStage;
    back = new Button("BACK");
    submit = new Button("SUBMIT");
    fileName = new TextField();
    root = new BorderPane();
    root.setStyle("-fx-background-color: #c0c0c5");
  }

  /**
   * Initializes a BorderPane of the SaveMenu screen
   * 
   * @return root - BorderPane of the SaveMenu screen
   */
  public BorderPane initialize(){

    setTopPanel();
    setCenterPanel();
    setBottomPanel();
    
    return root;
  }
  
  /**
   * Constructs the top panel in the BorderPane
   */
  private void setTopPanel() {
    // Labels
    Label label = new Label("Save Menu");
    String qLabel = "" + getQuestion().getSize();
    if(getQuestion().getSize() == 1) {
      qLabel += " question available";
    } else {
      qLabel += " questions available";
    }
    Label qAvailableLabel = new Label(qLabel);

    // Style
    qAvailableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    
    // Top Panel
    HBox topPanel = new HBox(label, qAvailableLabel);
    topPanel.setPadding(new Insets(10,50,10,50));
    topPanel.setSpacing(100);
    topPanel.setAlignment(Pos.CENTER);
    topPanel.setStyle("-fx-background-color: #9fb983");
    
    root.setTop(topPanel);
  }
  
  /**
   * Constructs the center panel in the BorderPane
   */
  private void setCenterPanel() {
    
    Label jsonLabel = new Label("Save as JSON:");
    
    // Center Panel
    HBox centerPanel = new HBox(jsonLabel,fileName);
    centerPanel.setPadding(new Insets(150,100,50,200));
    centerPanel.setSpacing(10);
    
    root.setCenter(centerPanel);
  }
  
  /**
   * Constructs the bottom panel in the BorderPane
   */
  private void setBottomPanel() {
    // Style
    back.setPrefSize(100,50);
    submit.setPrefSize(100,50);
    back.setOnMouseEntered(e -> back.setStyle("-fx-font-size: 14pt;"));
    back.setOnMouseExited(e -> back.setStyle("-fx-font-size: 12pt;"));
    submit.setOnMouseEntered(e -> submit.setStyle("-fx-font-size: 14pt;"));
    submit.setOnMouseExited(e -> submit.setStyle("-fx-font-size: 12pt;"));
    
    // Listeners
    back.setOnAction(event -> {
      primaryStage.setScene(Main.getMainScene());
    });
    submit.setOnAction(event -> {
      String file = fileName.getText();
      if(file.equals("")) {
        alert = new Alert(Alert.AlertType.ERROR, "Enter valid file name.");
        alert.setHeaderText("Error:");
        alert.showAndWait().filter(response -> response == ButtonType.OK);
        // clear response
        fileName.clear();
      } else {
        super.getQuestion().saveToJSON(file);
        alert = new Alert(Alert.AlertType.INFORMATION, "Saved to " + file + ".json");
        alert.setHeaderText("Save successful!");
        alert.showAndWait().filter(response -> response == ButtonType.OK);
        fileName.clear();
      }
      primaryStage.setScene(Main.getSaveScene());
    });
    
    // Bottom Panel
    HBox bottomPanel = new HBox(back,submit);
    bottomPanel.setPadding(new Insets(0,100,65,200));
    bottomPanel.setSpacing(100);
    
    root.setBottom(bottomPanel);
  }
}
