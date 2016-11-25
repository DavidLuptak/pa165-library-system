package cz.muni.fi.pa165.library.dto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public class LoanNewDTO {

    @NotNull
    private Long userId;

    @NotNull
    private List<Long> bookCopyIds = new ArrayList<>();

    @NotNull
    private Date loanDate;

    public LoanNewDTO() {
    }

    public LoanNewDTO(Long userId, List<Long> bookCopyIds, Date loanDate) {
        this.userId = userId;
        this.bookCopyIds = bookCopyIds;
        this.loanDate = loanDate;
    }

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

    public List<Long> getBookCopyIds() {
        return bookCopyIds;
    }

    public void setBookCopyIds(List<Long> bookCopyIds) {
        this.bookCopyIds = bookCopyIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof LoanNewDTO)) return false;

        LoanNewDTO that = (LoanNewDTO) o;

        if (!getLoanDate().equals(that.getLoanDate())) return false;
        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return getBookCopyIds().equals(that.getBookCopyIds());

    }

    @Override
    public int hashCode() {
        int result = getLoanDate().hashCode();
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + getBookCopyIds().hashCode();
        return result;
    }
}
