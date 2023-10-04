/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Staff;
import exception.StaffNotFoundException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import session.StaffSessionLocal;

/**
 *
 * @author stonley
 */
@Named(value = "staffManaged")
@ViewScoped
public class StaffManaged implements Serializable {

    @EJB(name = "StaffSessionLocal")
    private StaffSessionLocal staffSessionLocal;

    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public StaffManaged() {
    }

    public void addStaff(ActionEvent evt) {

        Staff s = new Staff();
        s.setFirstName(this.getFirstName());
        s.setLastName(this.getLastName());
        s.setUserName(this.getUserName());
        s.setPassword(this.getPassword());

        staffSessionLocal.createStaff(s);
    }
    
    public void deleteStaff(ActionEvent evt) {
        try {
            staffSessionLocal.deleteStaff(this.userName);
        } catch (StaffNotFoundException e) {
            
        }
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
