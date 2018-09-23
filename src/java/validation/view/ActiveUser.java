package validation.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
/**
 * This class is not used in this project
 */

@ManagedBean
public class ActiveUser {
    
    public void activeUser(String userName) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        userName , "You are logged in!!!"));
    }
    
     public void idle(String userName) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        userName , "You are logged in!!!"));
    }
}
