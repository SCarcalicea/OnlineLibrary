package gateway;

import constants.SQLConst;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mapper.LogInMapper;

/**
 * Data base gateway for users administration
 */
public class LogInGateway {
    
    // Create, Delete, Get all users and check password members
    private final String CreateSQLStatement = SQLConst.INSERT_INTO + LogInMapper.getTableName() + SQLConst.VALUES;
    private final String CheckSQLStatement = SQLConst.SELECT + LogInMapper.getPassword() + ", " + LogInMapper.getAdmin()  + SQLConst.FROM + LogInMapper.getTableName() + SQLConst.WHERE + LogInMapper.getUser() + "=";
    private final String DeleteSQLStatement = SQLConst.DELETE + SQLConst.FROM + LogInMapper.getTableName() + SQLConst.WHERE + LogInMapper.getTableName() + "."  + LogInMapper.getUser() + "=\"";
    private final String GetAllUsersSQLStmt = SQLConst.SELECT + LogInMapper.getUser()  + SQLConst.FROM + LogInMapper.getTableName() + SQLConst.WHERE + " 1";
    
    // User data
    private String user = "";
    private String password = "";
    private boolean admin = false;
    
    //SQL statement
    private Statement stmt = null;
    
    //Reason
    public String reason = "";
    
    // Constructor with user data
    public LogInGateway(String user, String password, boolean isAdmin) {
        this.user = user;
        this.password = password;
        this.admin = isAdmin;
    }
    
    // Constructor used for delete user
    public LogInGateway(String selection) {
        this.user = selection;
    }
     
    public boolean createNewUser(Connection con) throws SQLException {
        reason = "";
        if (con == null) {
            reason = "databaseDown";
            return false;
        }
        stmt = con.createStatement();
        int rows = stmt.executeUpdate(CreateSQLStatement + "('" + user + "', '" + password +  "', " + admin + ")");
        if (rows != 1) {
            reason = "databaseDown";
        }
        return rows == 1;
    }
    
    public boolean deleteUser(Connection con) throws SQLException{
        stmt = con.createStatement();
        int rows = stmt.executeUpdate(DeleteSQLStatement + user + "\"");
        return rows == 1;
    }
    
    public boolean checkPassword(Connection con) throws SQLException {
        reason = "";
        if (con == null) {
            reason = "databaseDown";
            return false;
        }
        stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(CheckSQLStatement + "'" + user + "'");
        if (result.next()) {
            String dbPass = result.getString(1).trim();
            this.setAdmin(result.getBoolean(2));
            boolean checkPassword = dbPass.equalsIgnoreCase(password);
            if (!checkPassword) {
                reason = "invalidPassword";
            }
            return checkPassword;
        } else {
            reason = "invalidUser";
            return false;
        }
    }
    
    // Get all user names from data base
    public List<String> getUsers(Connection con) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(GetAllUsersSQLStmt);
        while (resultSet.next()) {
            String temp = resultSet.getString(1).trim();
            result.add(temp);
        } 
        return result;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
}
