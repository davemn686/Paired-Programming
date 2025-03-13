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

    private String insertSuccessful;
    private String searchSuccessful;
    private String searchFailure;


    private BookCollection bookCollection;
    private PatronCollection patronCollection;



    public Librarian() {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        myRegistry = new ModelRegistry("Librarian");
        bookCollection = new BookCollection();

        if(myRegistry == null){
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }


        setDependencies();

        createAndShowLibrarianView();

    }


    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber){
        myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {myRegistry.unSubscribe(key, subscriber);
    }

    private void setDependencies() {
        dependencies = new Properties();

        insertSuccessful = "Successfully inserted book";
        searchSuccessful = "Successfully searched book";
        searchFailure = "No books found matching the given input";

        dependencies.setProperty("insertSuccessful", insertSuccessful);
        dependencies.setProperty("searchSuccessful", searchSuccessful);
        dependencies.setProperty("searchFailure", searchFailure);

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
        return switch (key) {
            case "BookList" -> bookCollection;
            case "PatronList" -> patronCollection;
            case "insertBookSuccess" -> insertSuccessful;
            case "searchBookSuccess" -> searchSuccessful;
            case "insertPatronSuccess" -> insertSuccessful;
            case "searchBookFailure" -> searchFailure;
            case "searchPatronFailure" -> searchFailure;
            default -> "";
        };
    }



    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);

        if(key.equals("Done")) {
            createAndShowLibrarianView();
        }else if(key.equals("DoneAndDelete")) {
            bookCollection = new BookCollection();
            patronCollection = new PatronCollection();
            createAndShowLibrarianView();



        // Book
        }else if(key.equals("InsertBook")){
            createNewBook();



        }else if(key.equals("InsertABook")){
            // This is to insert a new book
            Book book  = new Book();
            book.processNewBook((Properties)value);
            book.save();

            // Add success message
            insertSuccessful = "Successfully inserted book";
            myRegistry.updateSubscribers("insertBookSuccess", this);

        
        
        }else if(key.equals("SearchBook")){
            createAndShowSearchBookView();



        }else if(key.equals("FindBookWithTitleLike")){
            bookCollection = new BookCollection();
            bookCollection.findBooksWithTitleLike((String)value);
            bookCollection.sortInAscendingOrderBasedOnAuthorsName();

            if(bookCollection.emptyList()) {
                searchFailure = "No books found matching the given input";
                
                dependencies.setProperty("searchFailure", searchFailure);
                myRegistry.setDependencies(dependencies);


                myRegistry.updateSubscribers("searchBookFailure", this);

            }else{
                createAndShowBookCollectionView();
            }
        }else if(key.equals("BookSelected")){
            
            Book book = bookCollection.retrieve((String)value);
            if(book == null){
                searchFailure = "No book found matching the given input";
                dependencies.setProperty("searchFailure", searchFailure);
                myRegistry.setDependencies(dependencies);

                myRegistry.updateSubscribers("searchBookFailure", this);

            }else{
                searchSuccessful = book.toString();
                dependencies.setProperty("searchSuccessful", searchSuccessful);
                myRegistry.setDependencies(dependencies);

                myRegistry.updateSubscribers("searchBookSuccess", this);

            }


        // Patron
        }else if(key.equals("InsertPatron")){
            createNewPatron();
        }else if(key.equals("InsertAPatron")){
            Patron patron = new Patron();
            patron.processNewPatron((Properties)value);
            patron.save();

            insertSuccessful = "Successfully inserted patron";
            myRegistry.updateSubscribers("insertPatronSuccess", this);

        }else if (key.equals("SearchPatron")){
            createAndShowSearchPatronView();
        }else if(key.equals("FindPatronAtZipCode")){
            patronCollection = new PatronCollection();
            patronCollection.findPatronsAtZipCode((String)value);
            patronCollection.sortInAscendingOrderBasedOnName();

            if(patronCollection.emptyList()){
                searchFailure = "No patrons found matching the given input zip code";
                dependencies.setProperty("searchFailure", searchFailure);
                myRegistry.setDependencies(dependencies);

                myRegistry.updateSubscribers("searchPatronFailure", this);
            }else{
                createAndShowPatronCollectionView();
            }

        }else if(key.equals("Close")){
            myStage.close();
        }
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    private void createAndShowBookCollectionView(){

        myViews.remove("BookCollectionView");

        Scene currentScene = (Scene)myViews.get("BookCollectionView");

        if(currentScene == null) {
            View newView = ViewFactory.createView("BookCollectionView", this);
            currentScene = new Scene(newView);
            myViews.put("BookCollectionView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowPatronCollectionView(){

        myViews.remove("PatronCollectionView");

        Scene currentScene = (Scene)myViews.get("PatronCollectionView");

        if(currentScene == null) {
            View newView = ViewFactory.createView("PatronCollectionView", this);
            currentScene = new Scene(newView);
            myViews.put("PatronCollectionView", currentScene);
        }

        swapToView(currentScene);

    }


    private void createAndShowSearchBookView(){
        Scene currentScene = (Scene)myViews.get("SearchBookView");

        if(currentScene == null) {
            View newView = ViewFactory.createView("SearchBookView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchBookView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowSearchPatronView(){
        Scene currentScene = (Scene)myViews.get("SearchPatronView");

        if(currentScene == null) {
            View newView = ViewFactory.createView("SearchPatronView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchPatronView", currentScene);
        }

        swapToView(currentScene);

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

    private void createNewPatron(){
        Patron patron = new Patron();

        Scene currentScene = (Scene)myViews.get("PatronView");


        if(currentScene == null) {
            View newView = ViewFactory.createView("PatronView", this);
            currentScene = new Scene(newView);
            myViews.put("PatronView", currentScene);
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


