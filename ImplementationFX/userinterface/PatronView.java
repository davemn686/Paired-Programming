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

public class PatronView extends View {

    private TextField NameTextField;
    private TextField AddressTextField;
    private TextField CityTextField;
    private TextField ZipTextField;
    private TextField EmailTextField;
    private TextField DOBTextField;

    private ComboBox<String> StatusComboBox;
    private ComboBox<String> StateCodeComboBox;

    private Button SubmitButton;
    private Button DoneButton;

    private MessageView statusLog;

    public PatronView(IModel librarian){
        super(librarian, "PatronView");

        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContents());
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("InsertPatronError", this);
    }

    private Node createTitle(){
        Text titleText = new Text("       Brockport Library Patron Entry          ");
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

        NameTextField = new TextField();
        NameTextField.setPrefWidth(200);
        Text nameLabel = new Text("Name: ");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setFill(Color.DARKGREEN);

        AddressTextField = new TextField();
        AddressTextField.setPrefWidth(200);
        Text addressLabel = new Text("Address: ");
        addressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        addressLabel.setFill(Color.DARKGREEN);

        CityTextField = new TextField();
        CityTextField.setPrefWidth(200);
        Text cityLabel = new Text("City: ");
        cityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        cityLabel.setFill(Color.DARKGREEN);

        ZipTextField = new TextField();
        ZipTextField.setPrefWidth(200);
        Text zipLabel = new Text("Zip: ");
        zipLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        zipLabel.setFill(Color.DARKGREEN);

        EmailTextField = new TextField();
        EmailTextField.setPrefWidth(200);
        Text emailLabel = new Text("Email: ");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        emailLabel.setFill(Color.DARKGREEN);

        DOBTextField = new TextField();
        DOBTextField.setPrefWidth(200);
        Text dateLabel = new Text("Date Of Birth: ");
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        dateLabel.setFill(Color.DARKGREEN);


        StatusComboBox = new ComboBox<>();
        StatusComboBox.setPrefWidth(200);
        Text statusLabel = new Text("Status: ");
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        statusLabel.setFill(Color.DARKGREEN);

        StateCodeComboBox = new ComboBox<>();
        StateCodeComboBox.setPrefWidth(200);
        Text stateCodeLabel = new Text("State Code: ");
        stateCodeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        stateCodeLabel.setFill(Color.DARKGREEN);


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
                nameLabel,
                NameTextField,
                addressLabel,
                AddressTextField,
                cityLabel,
                CityTextField,
                zipLabel,
                ZipTextField,
                emailLabel,
                EmailTextField,
                dateLabel,
                DOBTextField,
                statusLabel,
                StatusComboBox,
                stateCodeLabel,
                StateCodeComboBox,
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
        NameTextField.setText("");
        AddressTextField.setText("");
        CityTextField.setText("");
        ZipTextField.setText("");
        EmailTextField.setText("");
        DOBTextField.setText("");
        //combobox has two values, Active and Inactive
        StatusComboBox.getItems().add("Active");
        StatusComboBox.getItems().add("Inactive");
        StatusComboBox.setValue("Active");
        StateCodeComboBox.getItems().add("AL");
        StateCodeComboBox.getItems().add("AK");
        StateCodeComboBox.getItems().add("AZ");
        StateCodeComboBox.getItems().add("AR");
        StateCodeComboBox.getItems().add("AS");
        StateCodeComboBox.getItems().add("CA");
        StateCodeComboBox.getItems().add("CO");
        StateCodeComboBox.getItems().add("CT");
        StateCodeComboBox.getItems().add("DE");
        StateCodeComboBox.getItems().add("DC");
        StateCodeComboBox.getItems().add("FL");
        StateCodeComboBox.getItems().add("GA");
        StateCodeComboBox.getItems().add("GU");
        StateCodeComboBox.getItems().add("HI");
        StateCodeComboBox.getItems().add("ID");
        StateCodeComboBox.getItems().add("IL");
        StateCodeComboBox.getItems().add("IN");
        StateCodeComboBox.getItems().add("IA");
        StateCodeComboBox.getItems().add("KS");
        StateCodeComboBox.getItems().add("KY");
        StateCodeComboBox.getItems().add("LA");
        StateCodeComboBox.getItems().add("ME");
        StateCodeComboBox.getItems().add("MD");
        StateCodeComboBox.getItems().add("MA");
        StateCodeComboBox.getItems().add("MI");
        StateCodeComboBox.getItems().add("MN");
        StateCodeComboBox.getItems().add("MO");
        StateCodeComboBox.getItems().add("MT");
        StateCodeComboBox.getItems().add("NE");
        StateCodeComboBox.getItems().add("NV");
        StateCodeComboBox.getItems().add("NH");
        StateCodeComboBox.getItems().add("NY");
        StateCodeComboBox.getItems().add("NC");
        StateCodeComboBox.getItems().add("ND");
        StateCodeComboBox.getItems().add("OH");
        StateCodeComboBox.getItems().add("OK");
        StateCodeComboBox.getItems().add("OR");
        StateCodeComboBox.getItems().add("PA");
        StateCodeComboBox.getItems().add("PR");
        StateCodeComboBox.getItems().add("RI");
        StateCodeComboBox.getItems().add("SC");
        StateCodeComboBox.getItems().add("SD");
        StateCodeComboBox.getItems().add("TN");
        StateCodeComboBox.getItems().add("TX");
        StateCodeComboBox.getItems().add("UT");
        StateCodeComboBox.getItems().add("VT");
        StateCodeComboBox.getItems().add("VA");
        StateCodeComboBox.getItems().add("VI");
        StateCodeComboBox.getItems().add("WA");
        StateCodeComboBox.getItems().add("WV");
        StateCodeComboBox.getItems().add("WI");
        StateCodeComboBox.getItems().add("WY");
        StateCodeComboBox.setValue("AL");



    }

    public void processAction(Event evt){
        clearErrorMessage();
        if (evt.getSource() == SubmitButton){
            processActionsInsertPatron();
        } else if (evt.getSource() == DoneButton){
            returnToHome();
        }
    }

    private void processActionsInsertPatron(){
        String nameText = NameTextField.getText();
        String addressText = AddressTextField.getText();
        String cityText = CityTextField.getText();
        String zipText = ZipTextField.getText();
        String emailText = EmailTextField.getText();
        String dobText = DOBTextField.getText();
        String statusText = StatusComboBox.getValue();

        if (nameText.isEmpty() || addressText.isEmpty() || cityText.isEmpty() || zipText.isEmpty() || emailText.isEmpty() || dobText.isEmpty() || statusText.isEmpty()){
            displayErrorMessage("Please fill in all fields");
            return;
        }

        Properties prop = new Properties();

        prop.setProperty("name", nameText);
        prop.setProperty("address", addressText);
        prop.setProperty("city", cityText);
        prop.setProperty("zip", zipText);
        prop.setProperty("email", emailText);
        prop.setProperty("dob", dobText);
        prop.setProperty("status", statusText);

        myModel.stateChangeRequest("InsertAPatron", prop);


    }

    private void returnToHome(){
        myModel.stateChangeRequest("Done", null);
    }


    public void updateState(String key, Object value){
        if (key.equals("InsertPatronError") == true){
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
