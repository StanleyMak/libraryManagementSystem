/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Member;
import exception.MemberNotFoundException;
import java.util.List;
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
public class MemberSession implements MemberSessionLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    @Override
    public void createMember(Member m) {
        em.persist(m);
    }

    @Override
    public Member getMemberById(Long mId) throws MemberNotFoundException {
        try {
            return (Member) em.find(Member.class, mId);
        } catch (Exception e) {
            throw new MemberNotFoundException();
        }
    }

    @Override
    public Member getMemberByIn(String mIn) throws MemberNotFoundException {
        try {
            Query query = em.createQuery("SELECT m FROM Member m WHERE m.identityNo = ?1").setParameter(1, mIn);
            Member m = (Member) query.getSingleResult();
            return m;
        } catch (Exception e) {
            throw new MemberNotFoundException();
        }
    }
    
    @Override
    public void updateMember(Member m) throws NoResultException, MemberNotFoundException {
        Member oldM = getMemberById(m.getId());

        oldM.setFirstName(m.getFirstName());
        oldM.setLastName(m.getLastName());
        oldM.setGender(m.getGender());
        oldM.setAge(m.getAge());
        oldM.setIdentityNo(m.getIdentityNo());
        oldM.setPhone(m.getPhone());
        oldM.setAddress(m.getAddress());
        
    }
    
    @Override
    public void deleteMember(String mIn) throws MemberNotFoundException {
        try {
            Member m = getMemberByIn(mIn);
            em.remove(m);
        } catch (MemberNotFoundException memberNotFoundException) {
            throw new MemberNotFoundException("Member " + mIn + " not found");
        }
    }
    
    @Override
    public List<Member> searchMembers() {
        return em.createQuery("SELECT m FROM Member m").getResultList();
    }

}
