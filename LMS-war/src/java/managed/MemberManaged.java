/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Member;
import exception.MemberNotFoundException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import session.MemberSessionLocal;

/**
 *
 * @author stonley
 */
@Named(value = "memberManaged")
@ViewScoped
public class MemberManaged implements Serializable {

    @EJB(name = "MemberSessionLocal")
    private MemberSessionLocal memberSessionLocal;

    private String firstName;
    private String lastName;
    private Character gender;
    private Integer age;
    private String identityNo;
    private String phone;
    private String address;
    
    private List<Member> members;
    
    public MemberManaged() {
    }
    
    @PostConstruct
    public void init() {
        setMembers(memberSessionLocal.searchMembers());
    }
    
    public void addMember(ActionEvent evt) {
        
        Member m = new Member();
        m.setFirstName(this.firstName);
        m.setLastName(this.lastName);
        m.setGender(this.gender);
        m.setAge(this.age);
        m.setIdentityNo(this.identityNo);
        m.setPhone(this.phone);
        m.setAddress(this.address);
        
        memberSessionLocal.createMember(m);
    }
    
    public void deleteMember(ActionEvent evt) {
        try {
            memberSessionLocal.deleteMember(this.identityNo);
        } catch (MemberNotFoundException memberNotFoundException) {
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
     * @return the gender
     */
    public Character getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Character gender) {
        this.gender = gender;
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return the identityNo
     */
    public String getIdentityNo() {
        return identityNo;
    }

    /**
     * @param identityNo the identityNo to set
     */
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the members
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(List<Member> members) {
        this.members = members;
    }
    
}
