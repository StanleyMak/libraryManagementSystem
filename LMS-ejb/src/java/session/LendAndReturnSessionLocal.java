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
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author stonley
 */
@Local
public interface LendAndReturnSessionLocal {

    public void lendBook(LendAndReturn l, Long bId, Long mId) throws BookNotFoundException, MemberNotFoundException;

    public LendAndReturn getLendAndReturnById(Long lId) throws LendingNotFoundException;

    public void returnBook(LendAndReturn l);

    public List<LendAndReturn> getAllLoans();

    public void cancelReturn(Long lId) throws LendingNotFoundException;

    public BigDecimal calcFineAmount(LendAndReturn l, Date temp);


    
    
}
