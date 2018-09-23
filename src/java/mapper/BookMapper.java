package mapper;

import constants.DatabaseConst;

/**
 * Mapper class for books table
 */
public class BookMapper {
    
    private static final String filePath = DatabaseConst.BOOK_PATH;
    private static final String title = DatabaseConst.BOOK_TITLE;
    private static final String publisher = DatabaseConst.BOOK_PUBLISHER;
    private static final String author = DatabaseConst.BOOK_AUTHOR;
    private static final String owner = DatabaseConst.BOOK_OWNER;
    private static final String cat = DatabaseConst.BOOK_CATEGORY;
    private static final String tableName = DatabaseConst.TABLE_NAME_BOOK;

    public static String getFilePath() {
        return filePath;
    }

    public static String getTitle() {
        return title;
    }

    public static String getPublisher() {
        return publisher;
    }

    public static String getAuthor() {
        return author;
    }

    public static String getOwner() {
        return owner;
    }

    public static String getCat() {
        return cat;
    }

    public static String getTableName() {
        return tableName;
    }
}
