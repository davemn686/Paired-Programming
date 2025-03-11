package userinterface;

import java.text.NumberFormat;
import java.util.Properties;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import impresario.IModel;

public class SearchPatronView extends View {

    private TextField searchTextField;
    private Button searchButton;
    private Button doneButton;

    private MessageView statusLog;

    public SearchPatronView(IModel librarian){
        super(librarian, "SearchPatronView");

        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContents());
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("SearchPatronError", this);
    }

    private Node createTitle(){
        Text titleText = new Text("       Brockport Library Patron Search          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    }

    private GridPane createFormContents(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        searchTextField = new TextField();
        searchTextField.setPrefWidth(200);
        Text searchLabel = new Text("Search: ");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        searchLabel.setFill(Color.DARKGREEN);

        searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        searchButton.setPrefWidth(150);

        doneButton = new Button("Done");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        doneButton.setPrefWidth(150);

        HBox buttonRow = new HBox(10);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().addAll(searchButton, doneButton);

        buttonBox.getChildren().addAll(
                searchLabel,
                searchTextField,
                buttonRow
        );

        grid.add(buttonBox, 0, 0);

        return grid;
    }

    private MessageView createStatusLog(String initialMessage){
        statusLog = new MessageView(initialMessage);
        return statusLog;
    }

    public void populateFields(){
        searchTextField.setText("");
    }

    public void processAction(Event evt){
        clearErrorMessage();
        if (evt.getSource() == searchButton){
            processActionsSearchPatron();
        } else if (evt.getSource() == doneButton){
            returnToHome();
        }
    }

    private void processActionsSearchPatron(){
        String searchText = searchTextField.getText();
        if(searchText.isEmpty()){
            displayErrorMessage("Please enter a search term");
            return;
        }

        myModel.stateChangeRequest("FindPatronAtZipCode", searchText);

    }

    private void returnToHome(){
        myModel.stateChangeRequest("Done", null);
    }

    public void updateState(String key, Object value){
        if (key.equals("SearchPatronError") == true){
            displayErrorMessage((String)value);
        }
    }

    public void displayErrorMessage(String message){
        statusLog.displayMessage(message);
    }

    public void clearErrorMessage(){
        statusLog.clearErrorMessage();
    }
}