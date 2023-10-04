/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Staff;
import exception.InvalidLoginException;
import exception.StaffNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author stonley
 */
@Stateless
public class StaffSession implements StaffSessionLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    @Override
    public void createStaff(Staff s) {
        em.persist(s);
    }

    @Override
    public Staff getStaffById(Long sId) throws StaffNotFoundException {
        try {
            return (Staff) em.find(Staff.class, sId);
        } catch (Exception e) {
            throw new StaffNotFoundException();
        }
    }

    @Override
    public Staff getStaffByUsername(String u) throws StaffNotFoundException {
        try {
            Query query = em.createQuery("SELECT s FROM Staff s WHERE s.userName = ?1")
                    .setParameter(1, u);
            Staff s = (Staff) query.getSingleResult();
            return s;
        } catch (Exception e) {
            throw new StaffNotFoundException();
        }
    }

    @Override
    public Long login(String u, String p) throws InvalidLoginException {
        try {
            Staff s = getStaffByUsername(u);
            if (s.getPassword().equals(p)) {
                return s.getId();
            } else {
                throw new InvalidLoginException();
            }
        } catch (Exception e) {
            throw new InvalidLoginException();
        }
    }

    @Override
    public Boolean logout(String u) {
        return true;
    }

    @Override
    public void updateStaff(Staff s) throws NoResultException, StaffNotFoundException {
        Staff oldS = getStaffById(s.getId());

        oldS.setFirstName(s.getFirstName());
        oldS.setLastName(s.getLastName());
        oldS.setUserName(s.getUserName());
        oldS.setPassword(s.getPassword());
    }

    @Override
    public void deleteStaff(String u) throws StaffNotFoundException {
        try {
            Staff s = getStaffByUsername(u);
            em.remove(s);
        } catch (StaffNotFoundException staffNotFoundException) {
            throw new StaffNotFoundException("Staff " + u + " not found");
        }
    }

}
