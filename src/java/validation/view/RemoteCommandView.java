package validation.view;

import javax.faces.bean.ManagedBean;

/**
 * This class is not used in this project
 */
@ManagedBean
public class RemoteCommandView {
    
    public String sendRedirect() {
        return "/login.xhtml?faces-redirect=true";
    }
}
