import model.Book;
import database.JDBCBroker;
import java.util.*;
import model.Patron;
import java.sql.Connection;
import model.BookCollection;

public class test {
    
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Initialize database connection
            JDBCBroker jdbc = JDBCBroker.getInstance();
            System.out.println("Driver: " + JDBCBroker.theDriver);
            jdbc.getConnection();
            System.out.println("Connection: " + jdbc.getConnection());
            
            // Start the main menu loop
            processUserChoices();
            
        } catch (Exception e) {
            System.out.println("Fatal Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    private static void processUserChoices() {
        while (true) {
            try {
                System.out.println("\n=== Library Management System ===");
                System.out.println("1. Insert a book");
                System.out.println("2. Insert a patron");
                System.out.println("3. Find book");
                System.out.println("4. Find book before year");
                System.out.println("5. Exit");
                System.out.print("Enter your choice (1-5): ");
                
                // Check if there's input available
                if (sc.hasNextLine()) {
                    String input = sc.nextLine().trim();
                    
                    // Validate input is a number
                    if (!input.matches("\\d+")) {
                        System.out.println("Error: Please enter a number between 1 and 5");
                        continue;
                    }
                    
                    int choice = Integer.parseInt(input);
                    
                    switch(choice) {
                        case 1:
                            insertBook();
                            break;
                        case 2:
                            insertPatron();
                            break;
                        case 3:
                            findBookWithTitle();
                            break;
                        case 4:
                            findBookBeforeYear();
                            break;
                        case 5:
                            System.out.println("Thank you for using the Library Management System!");
                            return;
                        default:
                            System.out.println("Error: Please enter a number between 1 and 5");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing input: " + e.getMessage());
                sc.nextLine(); // Clear the scanner buffer
            }
        }
    }

    static void insertBook() {
        try {
            System.out.println("\n=== Insert New Book ===");
            
            System.out.print("Enter the title of the book: ");
            String title = getValidInput("title");
            
            System.out.print("Enter the author of the book: ");
            String author = getValidInput("author");
            
            System.out.print("Enter the publication year (YYYY): ");
            String year = getValidYear();
            
            Properties props = new Properties();
            props.setProperty("bookTitle", title);
            props.setProperty("author", author);
            props.setProperty("pubYear", year);
            props.setProperty("status", "Active");
            
            Book book = new Book(props);
            book.save();
            
            System.out.println(book.getState("UpdateStatusMessage"));
            
        } catch (Exception e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    static void insertPatron() {
        try {
            System.out.println("\n=== Insert New Patron ===");
            
            System.out.print("Enter the name of the patron: ");
            String name = getValidInput("name");
            
            System.out.print("Enter the address: ");
            String address = getValidInput("address");
            
            System.out.print("Enter the city: ");
            String city = getValidInput("city");
            
            System.out.print("Enter the state code (2 letters): ");
            String stateCode = getValidStateCode();
            
            System.out.print("Enter the zip code (5 digits): ");
            String zip = getValidZipCode();
            
            System.out.print("Enter the email: ");
            String email = getValidEmail();
            
            System.out.print("Enter the date of birth (YYYY-MM-DD): ");
            String dateOfBirth = getValidDate();
            
            Properties props = new Properties();
            props.setProperty("name", name);
            props.setProperty("address", address);
            props.setProperty("city", city);
            props.setProperty("stateCode", stateCode);
            props.setProperty("zip", zip);
            props.setProperty("email", email);
            props.setProperty("dateOfBirth", dateOfBirth);
            props.setProperty("status", "Active");
            
            Patron patron = new Patron(props);
            patron.save();
            
            System.out.println(patron.getState("UpdateStatusMessage"));
            
        } catch (Exception e) {
            System.out.println("Error inserting patron: " + e.getMessage());
        }
    }

    static void findBookWithTitle() {
        try {
            System.out.println("\n=== Find Book ===");
            System.out.print("Enter the title to search for: ");
            String title = getValidInput("title");
            
            BookCollection bookCollection = new BookCollection();
            bookCollection.findBooksWithTitleLike(title);
            
        } catch (Exception e) {
            System.out.println("Error searching for book: " + e.getMessage());
        }
    }



    static void findBookBeforeYear(){
        try {
            System.out.println("\n=== Find Book Before Year ===");
            System.out.print("Enter the year to search for: ");
            String year = getValidYear();
            
            BookCollection bookCollection = new BookCollection();
            bookCollection.findBooksOlderThanDate(year);
        } catch (Exception e) {
            System.out.println("Error searching for book: " + e.getMessage());
        }
    }

    // Helper methods for input validation
    private static String getValidInput(String fieldName) {
        while (true) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.print("Error: " + fieldName + " cannot be empty. Please try again: ");
                continue;
            }
            return input;
        }
    }

    private static String getValidYear() {
        while (true) {
            String year = sc.nextLine().trim();
            if (year.matches("\\d{4}") && 
                Integer.parseInt(year) >= 1000 && 
                Integer.parseInt(year) <= Calendar.getInstance().get(Calendar.YEAR)) {
                return year;
            }
            System.out.print("Error: Please enter a valid year (YYYY): ");
        }
    }

    private static String getValidStateCode() {
        while (true) {
            String state = sc.nextLine().trim().toUpperCase();
            if (state.matches("[A-Z]{2}")) {
                return state;
            }
            System.out.print("Error: Please enter a valid state code (2 letters): ");
        }
    }

    private static String getValidZipCode() {
        while (true) {
            String zip = sc.nextLine().trim();
            if (zip.matches("\\d{5}")) {
                return zip;
            }
            System.out.print("Error: Please enter a valid 5-digit zip code: ");
        }
    }

    private static String getValidEmail() {
        while (true) {
            String email = sc.nextLine().trim();
            if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return email;
            }
            System.out.print("Error: Please enter a valid email address: ");
        }
    }

    private static String getValidDate() {
        while (true) {
            String date = sc.nextLine().trim();
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    // Validate the date
                    String[] parts = date.split("-");
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);
                    
                    if (year >= 1900 && year <= Calendar.getInstance().get(Calendar.YEAR) &&
                        month >= 1 && month <= 12 &&
                        day >= 1 && day <= 31) {
                        return date;
                    }
                } catch (Exception e) {
                    // Fall through to error message
                }
            }
            System.out.print("Error: Please enter a valid date (YYYY-MM-DD): ");
        }
    }
}
