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


/** The class containing the AccountCollection for the ATM application */
//==============================================================

public class PatronCollection extends EntityBase implements IView
{
    private static final String myTableName = "Patron";


    private Vector<Patron> patronlist;

    public PatronCollection(){
        super(myTableName);
        patronlist = new Vector<>();
    }


    //----------------------------------------------------------------------------------
    private void addPatron(Patron p)
    {
        //accounts.add(a);
        int index = findIndexToAdd(p);
        patronlist.insertElementAt(p,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Patron p)
    {
        //users.add(u);
        int low=0;
        int high = patronlist.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Patron midSession = patronlist.elementAt(middle);

            int result = Patron.compare(p,midSession);

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
        if (key.equals("PatronList"))
            return patronlist;
        else
        if (key.equals("PatronList"))
            return this;
        return null;
    }

    //----------------------------------------------------------
    public Patron retrieve(String patronId)
    {
        Patron retValue = null;
        for (int cnt = 0; cnt < patronlist.size(); cnt++)
        {
            Patron nextAcct = patronlist.elementAt(cnt);
            String nextAccNum = (String)nextAcct.getState("patronId");
            if (nextAccNum.equals(patronId) == true)
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

    public void findPatronsOlderThanDate(String date) {
        // Clear the existing patron list
        patronlist.clear();

        String query = "SELECT * FROM " + myTableName +
                " WHERE dateOfBirth < '" + date + "'";

        try {
            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

            if (allDataRetrieved != null) {
                for (Properties props : allDataRetrieved) {
                    Patron patron = new Patron(props);
                    addPatron(patron);  // Uses the existing addPatron method to maintain sorted order
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void findPatronsYoungerThanDate(String date) {
        patronlist.clear();

        String query = "SELECT * FROM " + myTableName +
                " WHERE dateOfBirth > '" + date + "'";

        try {
            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

            if (allDataRetrieved != null) {
                for (Properties props : allDataRetrieved) {
                    Patron patron = new Patron(props);
                    addPatron(patron);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void findPatronsWithNameLike(String name) {
        patronlist.clear();

        String query = "SELECT * FROM " + myTableName +
                " WHERE name LIKE '%" + name + "%'";

        try {
            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

            if (allDataRetrieved != null) {
                for (Properties props : allDataRetrieved) {
                    Patron patron = new Patron(props);
                    addPatron(patron);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void findPatronsAtZipCode(String zip) {
        patronlist.clear();

        String query = "SELECT * FROM " + myTableName +
                " WHERE zip LIKE '%" + zip + "%'";

        try {
            Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

            if (allDataRetrieved != null) {
                for (Properties props : allDataRetrieved) {
                    Patron patron = new Patron(props);
                    addPatron(patron);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }



}
