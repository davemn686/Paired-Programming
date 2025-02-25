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

public class BookView extends View {

    private TextField AuthorTextField;
    private TextField TitleTextField;
    private TextField PublicationDateTextField;

    private ComboBox<String> StatusComboBox;

    private Button SubmitButton;
    private Button DoneButton;

    private MessageView statusLog;

    public BookView(IModel librarian){
        super(librarian, "BookView");

        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContents());
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);
        
        populateFields();

        myModel.subscribe("InsertBookError", this);
    }

    private Node createTitle(){
        Text titleText = new Text("       Brockport Library Book Entry          ");
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

        AuthorTextField = new TextField();
        AuthorTextField.setPrefWidth(200);
        Text authorLabel = new Text("Author: ");
        authorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        authorLabel.setFill(Color.DARKGREEN);

        TitleTextField = new TextField();
        TitleTextField.setPrefWidth(200);
        Text titleLabel = new Text("Title: ");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setFill(Color.DARKGREEN);


        PublicationDateTextField = new TextField();
        PublicationDateTextField.setPrefWidth(200);
        Text publicationDateLabel = new Text("Publication Date: ");
        publicationDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        publicationDateLabel.setFill(Color.DARKGREEN);


        StatusComboBox = new ComboBox<>();
        StatusComboBox.setPrefWidth(200);
        Text statusLabel = new Text("Status: ");
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        statusLabel.setFill(Color.DARKGREEN);


        SubmitButton = new Button("Submit");
        SubmitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        SubmitButton.setPrefWidth(150);

        DoneButton = new Button("Done");
        DoneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);   
            }
        });
        DoneButton.setPrefWidth(150);

        HBox buttonRow = new HBox(10);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().addAll(SubmitButton, DoneButton);

        buttonBox.getChildren().addAll(
            authorLabel,
            AuthorTextField,    
            titleLabel,
            TitleTextField,
            publicationDateLabel,
            PublicationDateTextField,
            statusLabel,
            StatusComboBox,
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
        AuthorTextField.setText("");
        TitleTextField.setText("");
        PublicationDateTextField.setText("");
        //combobox has two values, Active and Inactive
        StatusComboBox.getItems().add("Active");
        StatusComboBox.getItems().add("Inactive");
        StatusComboBox.setValue("Active");
    }   

    public void processAction(Event evt){
        if (evt.getSource() == SubmitButton){

        } else if (evt.getSource() == DoneButton){
            returnToHome();
        }
    }

    private void processActionsInsertBook(){
        String authorText = AuthorTextField.getText();

    }

    private void returnToHome(){
        myModel.stateChangeRequest("Done", null);
    }


    public void updateState(String key, Object value){
        if (key.equals("InsertBookError") == true){
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
