/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotFoundException;
import exception.LendingNotFoundException;
import exception.MemberNotFoundException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import session.BookSessionLocal;
import session.LendAndReturnSessionLocal;
import session.MemberSessionLocal;

/**
 *
 * @author stonley
 */
@Named(value = "lendAndReturnManaged")
@ViewScoped
public class LendAndReturnManaged implements Serializable {

    @EJB(name = "MemberSessionLocal")
    private MemberSessionLocal memberSessionLocal;

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;

    @EJB(name = "LendAndReturnSessionLocal")
    private LendAndReturnSessionLocal lendAndReturnSessionLocal;

    private Long lendId;
    private Long memberId;
    private Long bookId;
    private Date lendDate;
    private Date returnDate;
    private BigDecimal fineAmount;

    private Book book;
    private Member member;

    private List<LendAndReturn> loans;

    public LendAndReturnManaged() {
    }

    @PostConstruct
    public void init() {
        this.loans = lendAndReturnSessionLocal.getAllLoans();
    }

    public String lendBook() {
        LendAndReturn l = new LendAndReturn();
        //l.setLendDate(new Date());
        l.setLendDate(this.lendDate);
        l.setReturnDate(null);
        l.setFineAmount(new BigDecimal(0));

        try {

            Book b = bookSessionLocal.getBookById(bookId);
//            l.setBook(b);
//            l.setBookId(bookId);
            for (LendAndReturn lend : b.getLendAndReturns()) {
                if (lend.getReturnDate() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Book is currently being lent out"));
                    return "lendBook.xhtml";
                }
            }

            Member m = memberSessionLocal.getMemberById(memberId);
//            l.setMember(member);
//            l.setMemberId(memberId);

            lendAndReturnSessionLocal.lendBook(l, bookId, memberId);
            return "secret.xhtml?faces-redirect=true";

        } catch (MemberNotFoundException | BookNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
            return "lendBook.xhtml";
        }

    }

    public String returnBook() {

        try {
            LendAndReturn l = lendAndReturnSessionLocal.getLendAndReturnById(lendId);

            l.setReturnDate(new Date());
            BigDecimal fineAmt = lendAndReturnSessionLocal.calcFineAmount(l, new Date());
            l.setFineAmount(this.fineAmount);
            lendAndReturnSessionLocal.returnBook(l);
            return "secret.xhtml?faces-redirect=true";

        } catch (LendingNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
            return "returnBook.xhtml";
        }

    }

    public void viewFineAmount(ActionEvent evt) {
        try {
            LendAndReturn l = lendAndReturnSessionLocal.getLendAndReturnById(lendId);
            BigDecimal fineAmt = lendAndReturnSessionLocal.calcFineAmount(l, new Date());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Please pay your fine of $" + fineAmt, ""));

        } catch (LendingNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
        }
    }


    public void cancelReturn(ActionEvent evt) {
        try {
            lendAndReturnSessionLocal.cancelReturn(lendId);
        } catch (LendingNotFoundException lendingNotFoundException) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to fine lending"));
        }
    }

    /**
     * @return the lendId
     */
    public Long getLendId() {
        return lendId;
    }

    /**
     * @param lendId the lendId to set
     */
    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    /**
     * @return the memberId
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the bookId
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * @return the lendDate
     */
    public Date getLendDate() {
        return lendDate;
    }

    /**
     * @param lendDate the lendDate to set
     */
    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the fineAmount
     */
    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    /**
     * @param fineAmount the fineAmount to set
     */
    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * @return the loans
     */
    public List<LendAndReturn> getLoans() {
        return loans;
    }

    /**
     * @param loans the loans to set
     */
    public void setLoans(List<LendAndReturn> loans) {
        this.loans = loans;
    }

}
