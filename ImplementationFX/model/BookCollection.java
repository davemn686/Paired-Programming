// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the BookCollection for the Library application */
//==============================================================

public class BookCollection extends EntityBase implements IView
{
    private static final String myTableName = "Book";


    private Vector<Book> booklist;

    public BookCollection(){
        super(myTableName);
        booklist = new Vector<>();
    }


    //----------------------------------------------------------------------------------
    private void addBook(Book b)
    {
        //accounts.add(a);
        int index = findIndexToAdd(b);
        booklist.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Book b)
    {
        //users.add(u);
        int low=0;
        int high = booklist.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Book midSession = booklist.elementAt(middle);

            int result = Book.compare(b,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    public Object getState(String key) {
        if (key.equals("BookList")) {
            return booklist;
        }
        else if (key.equals("UpdateStatusMessage")) {
            return "Found " + booklist.size() + " books";  // Add status message
        }
        return null;
    }

    //----------------------------------------------------------
    public Book retrieve(String bookId)
    {
        Book retValue = null;
        for (int cnt = 0; cnt < booklist.size(); cnt++)
        {
            Book nextAcct = booklist.elementAt(cnt);
            String nextAccNum = (String)nextAcct.getState("bookId");
            if (nextAccNum.equals(bookId) == true)
            {
                retValue = nextAcct;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    public void stateChangeRequest(String key, Object value) {
        myRegistry.updateSubscribers(key, this);
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public void findBooksOlderThanDate(String year) {
        booklist.clear();
        
        String query = "SELECT * FROM " + myTableName + 
                      " WHERE pubYear < '" + year + "'";
        
        try {
            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
            
            if (allDataRetrieved != null) {
                for (Properties props : allDataRetrieved) {
                    Book book = new Book(props);
                    addBook(book);
                }
                
                // Print the results
                if (booklist.size() > 0) {
                    System.out.println("\nFound books:");
                    for (Book book : booklist) {
                        book.display();
                    }
                }else{
                    System.out.println("No books found before year: " + year);
                } 
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void findBooksNewerThanDate(String year) {
        booklist.clear();
        
        String query = "SELECT * FROM " + myTableName + 
                      " WHERE pubYear > '" + year + "'";
        
        try {
            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
            
            if (allDataRetrieved != null) {
                for (Properties props : allDataRetrieved) {
                    Book book = new Book(props);
                    addBook(book);
                }
                // Print the results
                if (booklist.size() > 0) {
                    System.out.println("\nFound books:");
                    for (Book book : booklist) {
                        book.display();
                    }
                }else{
                    System.out.println("No books found after year: " + year);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void findBooksWithTitleLike(String title) {
        booklist.clear();
        
        String query = "SELECT * FROM " + myTableName + 
                      " WHERE bookTitle LIKE '%" + title + "%'";
        
        try {
            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
            
            if (allDataRetrieved != null) {
                for (Properties props : allDataRetrieved) {
                    Book book = new Book(props);
                    addBook(book);
                }
                // Print the results
                if (booklist.size() > 0) {
                    System.out.println("\nFound books:");
                    for (Book book : booklist) {
                        book.display();
                    }
                } else {
                    System.out.println("No books found matching title: " + title);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception while searching for books: " + e);
        }
    }

    public void findBooksWithAuthorLike(String author) {
        booklist.clear();
        
        String query = "SELECT * FROM " + myTableName + 
                      " WHERE author LIKE '%" + author + "%'";
        
        try {
            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
            
            if (allDataRetrieved != null) {
                for (Properties props : allDataRetrieved) {
                    Book book = new Book(props);
                    addBook(book);
                }
            }
            // Print the results
            if (booklist.size() > 0) {
                System.out.println("\nFound books:");
                for (Book book : booklist) {
                    book.display();
                }
            } else {
                System.out.println("No books found matching author: " + author);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }



}
