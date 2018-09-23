package constants;

/**
 * This class holds all of the constants needed in 
 * OnlineLibrary project
 */
public class DatabaseConst {
    
    // Database name and columns names (Table users)
    public static final String TABLE_NAME = " users ";
    public static final String USERS_TABLE_COLUMN = "id";
    public static final String PASS_TABLE_COLUMN = "pass";
    public static final String ADMIN_TABLE_COLUMN = "admin";
    
    // Database name and columns names (Table books)
    public static final String TABLE_NAME_BOOK = " books ";
    public static final String BOOK_PATH = "filepath";
    public static final String BOOK_TITLE = "title";
    public static final String BOOK_PUBLISHER = "editura";
    public static final String BOOK_AUTHOR = "autor";
    public static final String BOOK_OWNER = "owner";
    public static final String BOOK_CATEGORY = "category";
    public static final String BOOK_COLOR = "color";
    
    // Data base connection details
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost/onlinelibrary";
    public static final String DB_USER_NAME = "root";
    public static final String DB_PASSWORD = "";
}
