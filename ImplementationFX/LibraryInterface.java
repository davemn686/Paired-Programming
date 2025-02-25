import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;
import java.io.FileOutputStream;
import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// project imports
import event.Event;
import event.EventLog;
import common.PropertyFile;

import model.Librarian;
import model.Teller;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;





public class LibraryInterface extends Application{
    private Librarian myLibrarian;
    private Stage mainStage;

    public void start(Stage primaryStage){
        System.out.println("Librarian Version 3.00");
        System.out.println("Copyright 2004/2015 David Lonski");

        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "SUNY Librarian Version 3.00");
        mainStage = MainStageContainer.getInstance();

        // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(new EventHandler <javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
        });

        try
        {
            myLibrarian = new Librarian();
        }
        catch(Exception exc)
        {
            System.err.println("ATM.ATM - could not create Librarian!");
            new Event(Event.getLeafLevelClassName(this), "Librarian.<init>", "Unable to create Librarian object", Event.ERROR);
            exc.printStackTrace();
        }


        WindowPosition.placeCenter(mainStage);

        mainStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
        


}
