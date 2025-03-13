package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================

public class BookTableModel {


    private final SimpleStringProperty bookId;
    private final SimpleStringProperty bookTitle;
    private final SimpleStringProperty bookAuthor;
    private final SimpleStringProperty bookYear;
    private final SimpleStringProperty status;

    public BookTableModel(Vector<String> bookData){
        bookId = new SimpleStringProperty(bookData.get(0));
        bookTitle = new SimpleStringProperty(bookData.get(1));
        bookAuthor = new SimpleStringProperty(bookData.get(2));
        bookYear = new SimpleStringProperty(bookData.get(3));
        status = new SimpleStringProperty(bookData.get(4));
    }

    public SimpleStringProperty bookIdProperty(){return bookId;}
    public SimpleStringProperty bookTitleProperty() { return bookTitle; }
    public SimpleStringProperty bookAuthorProperty() { return bookAuthor; }
    public SimpleStringProperty bookYearProperty() { return bookYear; }
    public SimpleStringProperty statusProperty() { return status; }

    public String getBookId() { return bookId.get();}
    public String getBookTitle() { return bookTitle.get(); }
    public String getBookAuthor() { return bookAuthor.get(); }
    public String getBookYear() { return bookYear.get(); }
    public String getStatus() { return status.get(); }

    public void setBookId (String bookId) { this.bookId.set(bookId);}
    public void setBookTitle(String bookTitle) { this.bookTitle.set(bookTitle); }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor.set(bookAuthor); }
    public void setBookYear(String bookYear) { this.bookYear.set(bookYear); }
    public void setStatus(String status) { this.status.set(status); }
}
