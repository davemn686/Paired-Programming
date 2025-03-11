package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.Account;
import model.AccountCollection;
import model.Patron;
import model.PatronCollection;

public class PatronCollectionView extends View{
    protected TableView<PatronTableModel> tableOfPatrons;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;

    public PatronCollectionView(IModel librarian) {
        super(librarian, "PatronCollectionView");

        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {

        ObservableList<PatronTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            PatronCollection patronCollection = (PatronCollection)myModel.getState("PatronList");

            Vector entryList = (Vector)patronCollection.getState("Patrons");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Patron nextPatron = (Patron)entries.nextElement();
                Vector<String> view = nextPatron.getEntryListView();

                // add this list entry to the list
                PatronTableModel nextTableRowData = new PatronTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfPatrons.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Brockport Library Patron Collection ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }


    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF PATRONS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfPatrons = new TableView<PatronTableModel>();
        tableOfPatrons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn patronIDColumn = new TableColumn("Patron ID");
        patronIDColumn.setMinWidth(100);
        patronIDColumn.setCellValueFactory(new PropertyValueFactory<Patron, String>("patronID"));

        TableColumn patronNameColumn = new TableColumn("Patron Name") ;
        patronNameColumn.setMinWidth(100);
        patronNameColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronName"));

        TableColumn patronAddressColumn = new TableColumn("Patron Address") ;
        patronAddressColumn.setMinWidth(100);
        patronAddressColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronAddress"));

        TableColumn patronCityColumn = new TableColumn("Patron City") ;
        patronCityColumn.setMinWidth(100);
        patronCityColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronCity"));

        TableColumn patronStateCodeColumn = new TableColumn("Patron State Code") ;
        patronStateCodeColumn.setMinWidth(100);
        patronStateCodeColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronStateCode"));

        TableColumn patronZipColumn = new TableColumn("Patron Zip") ;
        patronZipColumn.setMinWidth(100);
        patronZipColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronZip"));

        TableColumn patronEmailColumn = new TableColumn("Patron Email") ;
        patronEmailColumn.setMinWidth(100);
        patronEmailColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronEmail"));

        TableColumn patronDOBColumn = new TableColumn("Patron DOB") ;
        patronDOBColumn.setMinWidth(100);
        patronDOBColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronDOB"));



        tableOfPatrons.getColumns().addAll(patronIDColumn, patronNameColumn, patronAddressColumn, patronCityColumn, patronStateCodeColumn, patronZipColumn, patronEmailColumn, patronDOBColumn);

        tableOfPatrons.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    processPatronsSelected();
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfPatrons);




        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processBackButton();
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(tableOfPatrons);
        vbox.getChildren().add(btnContainer);

        return vbox;

    }

    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
    }

    //--------------------------------------------------------------------------
    protected void processPatronsSelected()
    {
        PatronTableModel selectedItem = tableOfPatrons.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedPatronName = selectedItem.getPatronName();

            myModel.stateChangeRequest("PatronSelected", selectedPatronName);
        }
    }

    private void processBackButton(){
        myModel.stateChangeRequest("SearchPatron", null);
    }

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}