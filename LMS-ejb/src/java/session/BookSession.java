/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import exception.BookNotFoundException;
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
public class BookSession implements BookSessionLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    @Override
    public void createBook(Book b) {
        em.persist(b);
    }

    @Override
    public Book getBookById(Long bId) throws BookNotFoundException {
        try {
            return (Book) em.find(Book.class, bId);
        } catch (Exception e) {
            throw new BookNotFoundException("Book " + bId + " not found");
        }
    }

    @Override
    public Book getBookByTitle(String t) throws BookNotFoundException {
        try {
            Query query = em.createQuery("SELECT b FROM Book b WHERE b.title = ?1").setParameter(1, t);
            Book b = (Book) query.getSingleResult();
            return b;
        } catch (Exception e) {
            throw new BookNotFoundException("Book " + t + " not found");
        }
    }

    @Override
    public void updateBook(Book b) throws NoResultException, BookNotFoundException {
        Book oldB = getBookById(b.getId());

        oldB.setTitle(b.getTitle());
        oldB.setIsbn(b.getIsbn());
        oldB.setAuthor(b.getAuthor());

    }
    
    @Override
    public Book getBookByIsbn(String isbn) throws BookNotFoundException {
        try {
            Query query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = ?1").setParameter(1, isbn);
            Book b = (Book) query.getSingleResult();
            return b;
        } catch (Exception e) {
            throw new BookNotFoundException("Book " + isbn + " not found");
        }
    }
    
    @Override
    public void delete(String isbn) throws BookNotFoundException {
        try {
            Book b = getBookByIsbn(isbn);
            em.remove(b);
        } catch (BookNotFoundException bookNotFoundException) {
            throw new BookNotFoundException("Book " + isbn + " not found");
        }
    }

}
