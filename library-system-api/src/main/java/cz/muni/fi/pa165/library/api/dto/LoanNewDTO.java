package cz.muni.fi.pa165.library.api.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public class LoanNewDTO {

    @NotNull
    private Date loanDate;

    @NotNull
    private Long userId;

    @NotNull
    private Long bookCopyId;

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(Long bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof LoanNewDTO)) return false;

        LoanNewDTO that = (LoanNewDTO) o;

        if (!getLoanDate().equals(that.getLoanDate())) return false;
        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return getBookCopyId().equals(that.getBookCopyId());

    }

    @Override
    public int hashCode() {
        int result = getLoanDate().hashCode();
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + getBookCopyId().hashCode();
        return result;
    }
}
