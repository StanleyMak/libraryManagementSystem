/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotFoundException;
import exception.LendingNotFoundException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author stonley
 */
@Stateless
public class LendAndReturnSession implements LendAndReturnSessionLocal {

    @EJB(name = "MemberSessionLocal")
    private MemberSessionLocal memberSessionLocal;

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");

    @Override
    public void lendBook(LendAndReturn l, Long bId, Long mId) throws BookNotFoundException, MemberNotFoundException {

        //bidirectional association
        Book b = null;
        try {
            b = bookSessionLocal.getBookById(bId);
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException(e.getMessage());
        }

        Member m = null;
        try {
            m = memberSessionLocal.getMemberById(mId);
        } catch (MemberNotFoundException e) {
            throw new MemberNotFoundException(e.getMessage());
        }

        l.setBookId(b.getId());
        l.setBook(b);
        b.getLendAndReturns().add(l);
        l.setMemberId(m.getId());
        l.setMember(m);
        m.getLendAndReturns().add(l);

        em.persist(l);

    }

    @Override
    public LendAndReturn getLendAndReturnById(Long lId) throws LendingNotFoundException {
        try {
            return (LendAndReturn) em.find(LendAndReturn.class, lId);
        } catch (Exception e) {
            throw new LendingNotFoundException("Lending " + lId + " not found");
        }
    }

    @Override
    public void returnBook(LendAndReturn l) {
        em.merge(l);
    }

    @Override
    public void cancelReturn(Long lId) throws LendingNotFoundException {
        try {
            LendAndReturn l = getLendAndReturnById(lId);
            l.setReturnDate(null);
            l.setFineAmount(BigDecimal.ZERO);
            em.merge(l);
        } catch (LendingNotFoundException lendingNotFoundException) {
            throw new LendingNotFoundException("Lending " + lId + " not found");

        }
    }

    @Override
    public List<LendAndReturn> getAllLoans() {
        return em.createQuery("SELECT l FROM LendAndReturn l").getResultList();
    }

    @Override
    public BigDecimal calcFineAmount(LendAndReturn l, Date temp) {
        BigDecimal fineAmt = new BigDecimal(0.0);

        long milli = Math.abs(temp.getTime() - l.getLendDate().getTime());
        long diff = TimeUnit.DAYS.convert(milli, TimeUnit.MILLISECONDS) - 14;

        fineAmt = new BigDecimal(0);

        if (diff > 0) {
            fineAmt = new BigDecimal(diff * 0.5);
        }
//        l.setFineAmount(fineAmt);
//        em.merge(l);
        return fineAmt;
    }

}
