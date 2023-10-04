/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import exception.InvalidLoginException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.StaffSessionLocal;

/**
 *
 * @author stonley
 */
@Named(value = "authenticationManaged")
@SessionScoped
public class AuthenticationManaged implements Serializable {

    @EJB(name = "StaffSessionLocal")
    private StaffSessionLocal staffSessionLocal;

    private String userName = "";
    private String password = "";
    private Long staffId = -1L;

    public AuthenticationManaged() {
    }

    public String login() {

        try {
            this.staffId = staffSessionLocal.login(userName, password);
            return "/secret/secret.xhtml?faces-redirect=true";
        } catch (InvalidLoginException ex) {
            this.userName = "";
            this.password = "";
            this.staffId = -1L;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", ex.getMessage()));
            return "login.xhtml";
        }

    }

//    public String logout() {
//        this.userName = "";
//        this.password = "";
//        this.staffId = -1L;
//        return "../login.xhtml?faces-redirect=true";
//    }
    public void logout() {
        this.userName = "";
        this.password = "";
        this.staffId = -1L;
    }

    /**
     * @return the staffId
     */
    public Long getStaffId() {
        return staffId;
    }

    /**
     * @param staffId the staffId to set
     */
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
