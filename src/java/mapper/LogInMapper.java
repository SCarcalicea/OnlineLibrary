package mapper;

import constants.DatabaseConst;

/**
 * Mapper class for users table
 */
public class LogInMapper {
    
    private static final String userName = DatabaseConst.USERS_TABLE_COLUMN;
    private static final String password = DatabaseConst.PASS_TABLE_COLUMN;
    private static final String tableName = DatabaseConst.TABLE_NAME;
    private static final String admin = DatabaseConst.ADMIN_TABLE_COLUMN;
    
    public static String getUser(){
        return userName;
    }
    
    public static String getPassword(){
        return password;
    }
    
    public static String getTableName() {
        return tableName;
    }

    public static String getAdmin() {
        return admin;
    }
}
