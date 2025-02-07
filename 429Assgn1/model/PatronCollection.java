package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


public class PatronCollection {
    private Vector<Patron> patrons;

    public PatronCollection() {
        patrons = new Vector(); // new Vector<Patron>();
    }
}
