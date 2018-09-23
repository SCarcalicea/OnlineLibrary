package gateway;

import booksService.Book;
import constants.SQLConst;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mapper.BookMapper;

/**
 * Data base gateway for book administration
 */
public class BookGateway {
    
    // Insert, select and delete members
    private final String InsertIntoSQL = SQLConst.INSERT_INTO + BookMapper.getTableName() + SQLConst.VALUES;
    private final String SelectAllSQL = SQLConst.SELECT + " * " + SQLConst.FROM + BookMapper.getTableName();
    private static String DeleteEntrySQL = SQLConst.DELETE + SQLConst.FROM + BookMapper.getTableName() + SQLConst.WHERE + getCondition();
    
    // Not used, instead of an update we are executing delete and add procedures
    private static String UpdateFieldSQL = SQLConst.UPDATE + BookMapper.getTableName() + SQLConst.SET + getColumnsToBeUpdated() + SQLConst.WHERE + getCondition();
    
    // Static members used for the update and delete procedures
    private static String columnsToBeUpdated = "";
    private static String condition = "";
    
    // SQL Statement
    private Statement stmt = null;
    
    public BookGateway() {
        // Default constructor
    }
    
    // Constructor used for the delete and update procedures
    public BookGateway(String columnsToBeUpdated, String condition) {
        BookGateway.columnsToBeUpdated = columnsToBeUpdated;
        BookGateway.condition = condition;
    }
    
    // Not used, instead of an update we are executing delete and add procedures
    public static String getColumnsToBeUpdated() {
        return columnsToBeUpdated;
    }
    
    // Not used, instead of an update we are executing delete and add procedures
    public static void setColumnsToBeUpdated(String aColumnsToBeUpdated) {
        columnsToBeUpdated = aColumnsToBeUpdated;
        UpdateFieldSQL = SQLConst.UPDATE + BookMapper.getTableName() + SQLConst.SET + getColumnsToBeUpdated() + SQLConst.WHERE + getCondition();
    }

    public static String getCondition() {
        return condition;
    }

    public void setCondition(Book book) {
        BookGateway.condition = BookMapper.getTableName() + "." + BookMapper.getTitle() + "=\"" + book.getTitle() + "\"";
        UpdateFieldSQL = SQLConst.UPDATE + BookMapper.getTableName() + SQLConst.SET + getColumnsToBeUpdated() + SQLConst.WHERE + condition;
        DeleteEntrySQL = SQLConst.DELETE + SQLConst.FROM + BookMapper.getTableName() + SQLConst.WHERE + condition;
    }
    
    public List<Book> getAllBooks(Connection con) throws SQLException {
        stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(SelectAllSQL);
        List<Book> returnList = new ArrayList<>();
        while (result.next()) {
            Book book = new Book();
            book.setFilePath(result.getString(1));
            book.setTitle(result.getString(2));
            book.setPublisher(result.getString(3));
            book.setAuthor(result.getString(4));
            book.setOwner(result.getString(5));
            book.setCategory(result.getString(6));
            book.setColor(result.getString(7));
            returnList.add(book);
        }
        return returnList;
    }
    
    public boolean addBook(Connection con, Book book) throws SQLException{
        stmt = con.createStatement();
        int rows = stmt.executeUpdate(InsertIntoSQL + book.toString());
        if (rows == 1) {
            return true;
        } else {
            return false;   
        }
    }
    
    public boolean deleteBook(Connection con) throws SQLException{
        stmt = con.createStatement();
        int rows = stmt.executeUpdate(DeleteEntrySQL);
        return rows == 1;
    }
    
    // Not used, instead of an update we are executing delete and add procedures
    public boolean updateBook(Connection con) throws SQLException{
        stmt = con.createStatement();
        int rows = stmt.executeUpdate(UpdateFieldSQL);
        return rows == 1;
    }
}
