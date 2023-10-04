/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import exception.BookNotFoundException;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author stonley
 */
@Local
public interface BookSessionLocal {

    public void createBook(Book b);

    public Book getBookById(Long bId) throws BookNotFoundException;

    public Book getBookByTitle(String t) throws BookNotFoundException;

    public void updateBook(Book b) throws NoResultException, BookNotFoundException;

    public Book getBookByIsbn(String isbn) throws BookNotFoundException;

    public void delete(String isbn) throws BookNotFoundException;



    
}
