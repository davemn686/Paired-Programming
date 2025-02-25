package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;

import event.Event;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;


public class Librarian implements IView , IModel {
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    private AccountHolder myAccountHolder;

    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";



    public Librarian() {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        myRegistry = new ModelRegistry("Librarian");

        if(myRegistry == null){
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }


        setDependencies();

        createAndShowLibrarianView();

    }

    private void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("loginErrorMessage", loginErrorMessage);

        myRegistry.setDependencies(dependencies);
    }


    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param	key	Name of database column (field) for which the client wants the value
     *
     * @return	Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        return "";
    }

    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber) {

    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber) {

    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);

        if(key.equals("Done")){
            createAndShowLibrarianView();
        }else if(key.equals("InsertBook")){
            createNewBook();
        }
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    private void createAndShowLibrarianView(){
        Scene currentScene = (Scene)myViews.get("LibrarianView");

        if(currentScene == null) {
            View newView = ViewFactory.createView("LibrarianView", this);
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createNewBook(){
        Book book = new Book();

        Scene currentScene = (Scene)myViews.get("BookView");


        if(currentScene == null) {
            View newView = ViewFactory.createView("BookView", this);
            currentScene = new Scene(newView);
            myViews.put("BookView", currentScene);
        }

        swapToView(currentScene);

    }

    //-----------------------------------------------------------------------------
    public void swapToView(Scene newScene)
    {


        if (newScene == null)
        {
            System.out.println("Teller.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();


        //Place in center
        WindowPosition.placeCenter(myStage);

    }
}
