package validation.view;

import constants.DatabaseConst;
import gateway.LogInGateway;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 * Class representing user operations and data base connection Log in, log out,
 * connect/disconnect to database, create user, delete user, etc
 */
@ManagedBean
@ApplicationScoped
public class LoginView {

    // User data
    public String username;
    private String password;
    private boolean admin;
    public static String curentUser;

    // Selected user and all users list
    public List<String> allUsersList;
    private String selectedUser;

    // Data base connection object
    public static Connection con;

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getCurentUser() {
        return curentUser;
    }

    public void setCurentUser(String curentUser) {
        LoginView.curentUser = curentUser;
    }

    public List<String> getAllUsersList() {
        return allUsersList;
    }

    public void setAllUsersList(List<String> allUsersList) {
        this.allUsersList = allUsersList;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    // Check user password and if the user is admin 
    // Send coresponding redirect
    public String check() {
        try {
            LogInGateway login = new LogInGateway(username, password, admin);
            if (login.checkPassword(LoginView.con)) {
                // Log in successfull
                LoginView.curentUser = username;
                username = "";
                if (this.admin) {
                    allUsersList = login.getUsers(con);
                    LoginView.sendRedirect(login.isAdmin());
                } else {
                    LoginView.sendRedirect(false);
                }
                return "";
            } else {
                connectionErrorHandling(login);
                return "";
            }
        } catch (SQLException | IOException ex) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check database server!!!", ""));
            return "";
        }
    }

    // If it is admin we will redirect the user to the administration panel
    // Else we will redirect the user to library view
    public static void sendRedirect(boolean admin) throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String contextPath = context.getRequestContextPath();
        if (admin) {
            context.redirect(contextPath + "/faces/adminArea.xhtml?faces-redirect=true");
        } else {
            context.redirect(contextPath + "/faces/index.xhtml?faces-redirect=true");
        }
    }

    // log out command
    public static void logout() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String contextPath = context.getRequestContextPath();
        context.redirect(contextPath + "/faces/login.xhtml?faces-redirect=true");
    }

    // Create user, show message 
    public String create() {
        try {
            LogInGateway login = new LogInGateway(username, password, admin);
            if (login.createNewUser(LoginView.con)) {
                // User is created
                FacesContext context = FacesContext.getCurrentInstance();
                if (getAdmin()) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Administrator created successfully!!!", ""));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User created successfully!!!", ""));
                }

                return "";
            } else {
                connectionErrorHandling(login);
                return "";
            }
        } catch (SQLException e) {
            // Error
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check database server!!!", ""));
            return "";
        }
    }

    private void connectionErrorHandling(LogInGateway login) {
        // Error casses
        FacesContext context = FacesContext.getCurrentInstance();
        switch (login.reason) {
            case "invalidPassword":
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid password!!!", ""));
                break;
            case "invalidUser":
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid user name!!!", ""));
                break;
            default:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check database server!!!", ""));
                break;
        }
    }

    // Create user from admin panel, close user management
    public String createFromAdmin() {
        try {
            LogInGateway login = new LogInGateway(username, password, admin);
            if (!username.isEmpty()) {
                login.createNewUser(LoginView.con);
            allUsersList.add(username);
            username = "";
            }
            RequestContext.getCurrentInstance().addCallbackParam("saved", true); // close dialog
            return "";
        } catch (SQLException e) {
            // Error
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!!!", ""));
            return "";
        }
    }

    // Delete user
    public String delete() {
        try {
            if (selectedUser != null) {
                LogInGateway login = new LogInGateway(selectedUser);
                login.deleteUser(LoginView.con);
                allUsersList.remove(selectedUser);
                username = "  ";
            }
            RequestContext.getCurrentInstance().addCallbackParam("saved", true);
            return "";
        } catch (SQLException e) {
            // Error
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!!!", ""));
            return "";
        }
    }

    // Data base connection command
    public boolean connect() {
        if (LoginView.con == null) {
            try {
                Class.forName(DatabaseConst.DRIVER).newInstance();
                LoginView.con = DriverManager.getConnection(DatabaseConst.JDBC_URL, DatabaseConst.DB_USER_NAME, DatabaseConst.DB_PASSWORD);
                /* Note use' / 'and not' \'  The url above will be different in your system*/
                return LoginView.con != null; // Connected
                // Cannot conect
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException err) {
                return false;
            }
        } else {
            return true;
        }
    }

    // Database disconnect command
    public static boolean disconnect() {
        if (LoginView.con != null) {
            try {
                LoginView.con.close();
                LoginView.con = null;
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }
}
