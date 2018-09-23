package validation.view;

import booksService.Book;
import booksService.BookService;
import gateway.BookGateway;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 * Class representing book operation and user operation 
 * Add book, delete book, add user, delete user, etc
 */
@ManagedBean(name = "dataScrollerView")
@ViewScoped
public class DataScrollerView implements Serializable {

    // Used for serialozation
    private static final long serialVersionUID = 1L;
    
    // Books lists
    private List<Book> itBooks;
    private List<Book> sportBooks;
    private List<Book> otherBooks;
    private List<Book> books;
    
    // Add/remove book variables
    private Book selectedBook = new Book();
    public Book newBook = new Book();
    
    // In order to show the preview
    // We have to block the uploading file thread until save is clicked
    private boolean disableThePreview = false;
    
    @ManagedProperty("#{bookService}")
    private BookService service;
    private final BookGateway gate = new BookGateway();
    
    @PostConstruct
    public void init() {
        try {
            itBooks = new ArrayList<>();
            sportBooks = new ArrayList<>();
            otherBooks = new ArrayList<>();
            books = service.createBooks(10);
            
            // Lambda expresion
            books.stream().forEach((book) -> {
                if (book.getCategory().equalsIgnoreCase("itbooks")) {
                    itBooks.add(book);
                } else if (book.getCategory().equalsIgnoreCase("sport")) {
                    sportBooks.add(book);
                } else {
                    otherBooks.add(book);
                }
            });
        } catch (SQLException e) {
            // Do nothing for now
        }
    }
    
    public List<Book> getItBooks() {
        return itBooks;
    }

    public void setItBooks(List<Book> itBooks) {
        this.itBooks = itBooks;
    }

    public List<Book> getSportBooks() {
        return sportBooks;
    }

    public void setSportBooks(List<Book> sportBooks) {
        this.sportBooks = sportBooks;
    }

    public List<Book> getOtherBooks() {
        return otherBooks;
    }

    public void setOtherBooks(List<Book> otherBooks) {
        this.otherBooks = otherBooks;
    }

    public void setNewBook(Book newBook) {
        this.newBook = newBook;
    }

    public Book getNewBook() {
        return newBook;
    }
    
    public List<Book> getBooks() {
        return books;
    }

    public void setService(BookService service) {
        this.service = service;
    }
    
    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public void saveAndClose() {
        try {
            disableThePreview = true;
            gate.setCondition(selectedBook);
            gate.deleteBook(LoginView.con);
            formatSelectedBook();
            gate.addBook(LoginView.con, selectedBook);
            
            RequestContext.getCurrentInstance().addCallbackParam("saved", true);
            LoginView.sendRedirect(true); // Refresh Page
        } catch (SQLException | IOException e) {
            RequestContext.getCurrentInstance().addCallbackParam("saved", true);
        }
    }
    
    private void formatSelectedBook () {
        if (selectedBook.getAuthor().isEmpty()) {
            selectedBook.setAuthor("Not set");
        }
        if (selectedBook.getCategory().isEmpty()) {
            selectedBook.setCategory("Not set");
        }
        if (selectedBook.getTitle().isEmpty()) {
            selectedBook.setTitle("Not set");
        }
        if (selectedBook.getPublisher().isEmpty()) {
            selectedBook.setPublisher("Not set");
        }
        if (selectedBook.filePath.isEmpty()) {
            selectedBook.setFilePath("web/Resources/book.png");
        }
    }
    
    public void deleteAndClose() {
        try {
            disableThePreview = true;
            gate.setCondition(selectedBook);
            gate.deleteBook(LoginView.con);
            
            RequestContext.getCurrentInstance().addCallbackParam("saved", true);
            LoginView.sendRedirect(true); // Refresh Page
        } catch (SQLException | IOException e) {
            RequestContext.getCurrentInstance().addCallbackParam("saved", true);
        }
    }
    
    public void rentReturnBook() {
        try {
            boolean returnBook = false;
            if (selectedBook.getOwner().equals(LoginView.curentUser)) {
                selectedBook.setOwner("");
                returnBook = true;
            }
            gate.setCondition(selectedBook);
            gate.deleteBook(LoginView.con);
            if (!returnBook) {
                selectedBook.setOwner(LoginView.curentUser);
                selectedBook.setColor("red");
            } else {
                selectedBook.setColor("");
            }
            gate.addBook(LoginView.con, selectedBook);
            
            RequestContext.getCurrentInstance().addCallbackParam("saved", true);
            LoginView.sendRedirect(false); // Refresh Page
        } catch (SQLException | IOException e) {
            RequestContext.getCurrentInstance().addCallbackParam("saved", true);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            disableThePreview = false;
            InputStream is = event.getFile().getInputstream();
            URL location = DataScrollerView.class.getProtectionDomain().getCodeSource().getLocation();
            String savePath = processSaveFilePath(URLDecoder.decode(location.getFile(), "UTF-8"));
            copyFile(savePath + UUID.randomUUID() + ".png", is);
            while(true) {
                if (disableThePreview) {
                    break;
                }
            }
        } catch (Exception e) {
            // Ignoring for now
        }
    }
    
    private String processSaveFilePath(String filepath) {
        StringBuilder temp = new StringBuilder(filepath.substring(1, filepath.lastIndexOf("web/")+4));
        temp.append("Resources/");
        return temp.toString();
    }
    
    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (OutputStream out = new FileOutputStream(file)) {
                int read;
                byte[] bytes = new byte[1024];
                
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                
                in.close();
                out.flush();
            }
            
            this.selectedBook.setFilePath(fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void logOut() {
        LoginView.disconnect();
        try {
            LoginView.logout();
        } catch (Exception e) {
            // Error handling, Do nothing for now
        }
    }
    
}
