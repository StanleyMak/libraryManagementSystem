/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Book;
import exception.BookNotFoundException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import session.BookSessionLocal;

/**
 *
 * @author stonley
 */
@Named(value = "bookManaged")
@ViewScoped
public class BookManaged implements Serializable {

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;

    private String author;
    private String isbn;
    private String title;

    public BookManaged() {
    }

    public void addBook(ActionEvent evt) {

        Book b = new Book();
        b.setAuthor(this.getAuthor());
        b.setIsbn(this.getIsbn());
        b.setTitle(this.getTitle());

        bookSessionLocal.createBook(b);
    }
    
    public void deleteBook(ActionEvent evt) {
        try {
            bookSessionLocal.delete(this.isbn);
        } catch (BookNotFoundException bookNotFoundException) {
        }
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
