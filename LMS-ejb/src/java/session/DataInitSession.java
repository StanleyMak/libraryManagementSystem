/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import entity.Staff;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author stonley
 */
@Singleton
@LocalBean
@Startup
public class DataInitSession {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;
    
    @PostConstruct
    public void postConstruct() {
        
        if (em.find(Staff.class, 1l) == null) {
            initialiseStaffData();
        }
        
        if (em.find(Book.class, 1l) == null) {
            initialiseBookData();
        }
        
        if (em.find(Member.class, 1l) == null) {
            initialiseMemberData();
        }

    }
    
    private void initialiseStaffData() {
        
        Staff s;
        s = new Staff("Eric", "Some", "eric", "password");
        em.persist(s);
        em.flush();
        s = new Staff("Sarah", "Brightman", "sarah", "password");
        em.persist(s);
        em.flush();
               
    }
    
    private void initialiseBookData() {
        
        Book b;
        b = new Book("Anna Karenina", "0451528611", "Leo Tolstoy");
        em.persist(b);
        em.flush();
        b = new Book("Madame Bovary", "979-8649042031", "Gustave Flaubert");
        em.persist(b);
        em.flush();
        b = new Book("Hamlet", "1980625026", "William Shakespeare");
        em.persist(b);
        em.flush();
        b = new Book("The Hobbit", "9780007458424", "J R R Tolkien");
        em.persist(b);
        em.flush();
        b = new Book("Great Expectations", "1521853592", "Charles Dickens");
        em.persist(b);
        em.flush();
        b = new Book("Pride and Prejudice", "979-8653642272", "Jane Austen");
        em.persist(b);
        em.flush();
        b = new Book("Wuthering Heights", "3961300224", "Emily Bronte");
        em.persist(b);
        em.flush();
               
    }
    
    private void initialiseMemberData() {

        Member m;
        m = new Member("Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East, Ave 3");
        em.persist(m);
        em.flush();
        m = new Member("Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Computing Dr");
        em.persist(m);
        em.flush();
               
    }
    
}
