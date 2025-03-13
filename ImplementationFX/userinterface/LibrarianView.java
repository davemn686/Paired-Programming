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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

public class LibrarianView  extends View{

    private Button insertBookButton;
    private Button insertPatronButton;
    private Button searchBookButton;
    private Button searchPatronButton;
    private Button doneButton;
    
    private MessageView statusLog;
    
    public LibrarianView(IModel librarian){
        super(librarian, "LibrarianView");

        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContents());
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("LoginError", this);
    }   

    // Create the label (Text) for the title of the screen
	//-------------------------------------------------------------
	private Node createTitle()
	{
		
		Text titleText = new Text("       Brockport Bank ATM          ");
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

        // Create a VBox to hold all buttons
        VBox buttonBox = new VBox(10);  // 10 is the spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);

        insertBookButton = new Button("Insert Book");
        insertBookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        // Set a minimum width for consistent button sizes
        insertBookButton.setPrefWidth(150);

        insertPatronButton = new Button("Insert Patron");
        insertPatronButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { processAction(e); }
        });
        insertPatronButton.setPrefWidth(150);

        searchBookButton = new Button("Search Book");
        searchBookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        searchBookButton.setPrefWidth(150);

        searchPatronButton = new Button("Search Patron");
        searchPatronButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        searchPatronButton.setPrefWidth(150);

        doneButton = new Button("Done");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        doneButton.setPrefWidth(150);

        // Add all buttons to the VBox
        buttonBox.getChildren().addAll(
            insertBookButton,
            insertPatronButton,
            searchBookButton,
            searchPatronButton,
            doneButton
        );

        // Add the VBox to the grid
        grid.add(buttonBox, 0, 0);

        return grid;
    }

    private MessageView createStatusLog(String initialMessage){
        statusLog = new MessageView(initialMessage);
        return statusLog;
    }  

    public void populateFields(){
        // No fields to populate for this view
    }

    public void processAction(Event evt){
        // Handle button clicks
        if (evt.getSource() == insertBookButton){
            myModel.stateChangeRequest("InsertBook", null);
        } else if (evt.getSource() == insertPatronButton){
            myModel.stateChangeRequest("InsertPatron", null);
        } else if (evt.getSource() == searchBookButton){
            myModel.stateChangeRequest("SearchBook", null);
        } else if (evt.getSource() == searchPatronButton){
            myModel.stateChangeRequest("SearchPatron", null);
        } else if (evt.getSource() == doneButton){
            myModel.stateChangeRequest("Close", null);
        }
    }

    private void insertBook(){myModel.stateChangeRequest("InsertBook", null); }
    private void insertPatron(){myModel.stateChangeRequest("InsertPatron", null); }

    public void updateState(String key, Object value){
        if (key.equals("InsertBookError") == true){
            displayErrorMessage((String)value);
        } else if (key.equals("InsertPatronError") == true){
            displayErrorMessage((String)value);
        } else if (key.equals("SearchBookError") == true){
            displayErrorMessage((String)value);
        } else if (key.equals("SearchPatronError") == true){
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
