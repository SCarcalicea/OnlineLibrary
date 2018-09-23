package booksService;

import gateway.BookGateway;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import validation.view.LoginView;
 
@ManagedBean(name = "bookService")
@ApplicationScoped
public class BookService implements Serializable{
     
    // Needed for serialozation
    private static final long serialVersionUID = 1L;
    
    // Database comunication
    private BookGateway bookGate = new BookGateway("", "");
     
    // Get all books from data base
    public List<Book> createBooks(int size) throws SQLException {
        return bookGate.getAllBooks(LoginView.con);
    }
}