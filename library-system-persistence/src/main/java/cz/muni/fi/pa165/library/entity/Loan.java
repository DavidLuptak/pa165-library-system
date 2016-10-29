package cz.muni.fi.pa165.library.entity;

import cz.muni.fi.pa165.library.enums.BookState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 *
 * @author Bedrich Said
 */
@Entity
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    
    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date loanDate;
    
    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date returnDate;
    
    public Date getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    @Column(nullable = false)
    private boolean returned = false;

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
	
    @Column
    private BookState returnBookState;
    
    public BookState getReturnBookState() {
        return this.returnBookState;
    }
    
    public void setReturnBookState(BookState returnBookState) {
        this.returnBookState = returnBookState;
    }

    @NotNull
    @OneToMany(mappedBy = "loan")
    private List<BookCopy> bookCopies = new ArrayList<>();

    public List<BookCopy> getBookCopy() {
        return Collections.unmodifiableList(bookCopies);
    }

    public void addBookCopy(BookCopy bookCopy){
        this.bookCopies.add(bookCopy);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.user);
        hash = 53 * hash + Objects.hashCode(this.loanDate);
        hash = 53 * hash + Objects.hashCode(this.returnDate);
        hash = 53 * hash + (this.returned ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Loan other = (Loan) obj;
        if (this.returned != other.returned) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.loanDate, other.loanDate)) {
            return false;
        }
        if (!Objects.equals(this.returnDate, other.returnDate)) {
            return false;
        }
        return true;
    }
}
