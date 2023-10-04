/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Staff;
import exception.InvalidLoginException;
import exception.StaffNotFoundException;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author stonley
 */
@Local
public interface StaffSessionLocal {

    public void createStaff(Staff s);

    public Boolean logout(String u);

    public Staff getStaffByUsername(String u) throws StaffNotFoundException;

    public Long login(String u, String p) throws InvalidLoginException;

    public void updateStaff(Staff s) throws NoResultException, StaffNotFoundException;

    public Staff getStaffById(Long sId) throws StaffNotFoundException;

    public void deleteStaff(String u) throws StaffNotFoundException;

    
}
