/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Member;
import exception.MemberNotFoundException;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author stonley
 */
@Local
public interface MemberSessionLocal {

    public void createMember(Member m);

    public Member getMemberById(Long mId) throws MemberNotFoundException;

    public Member getMemberByIn(String mIn) throws MemberNotFoundException;

    public void updateMember(Member m) throws NoResultException, MemberNotFoundException;

    public void deleteMember(String mIn) throws MemberNotFoundException;

    public List<Member> searchMembers();


    
}
