import model.Book;
import database.JDBCBroker;
import java.util.*;
import java.sql.Connection;

public class test {
    public static void main(String[] args) {
        JDBCBroker jdbc = JDBCBroker.getInstance();
        System.out.println("Driver: " + JDBCBroker.theDriver);
        jdbc.getConnection();
        System.out.println("Connection: " + jdbc.getConnection());
        insertBook();
    }

    static void insertBook(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the name of the book: ");
        String name = sc.nextLine();
        System.out.println("Enter the author of the book: ");
        String author = sc.nextLine();
        System.out.println("Enter the title of the book: ");
        String title = sc.nextLine();
        System.out.println("Enter the year of the book: ");
        String year = sc.nextLine();
        Properties prop = new Properties();
        prop.setProperty("name", name);
        prop.setProperty("author", author);
        prop.setProperty("title", title);
        prop.setProperty("year", year);
        
        Book book = new Book(prop);
        book.update();

        System.out.println(book.getState("UpdateStatusMessage"));


    }






}
