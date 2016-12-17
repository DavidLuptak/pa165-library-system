package cz.muni.fi.pa165.library.dto;

import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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
    private List<Long> bookIds = new ArrayList<>();

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime loanDate;

    public LoanNewDTO() {
    }

    public LoanNewDTO(Long userId, List<Long> bookIds, LocalDateTime loanDate) {
        this.userId = userId;
        this.bookIds = bookIds;
        this.loanDate = loanDate;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof LoanNewDTO)) return false;

        LoanNewDTO that = (LoanNewDTO) o;

        if (!getLoanDate().equals(that.getLoanDate())) return false;
        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return getBookIds().equals(that.getBookIds());

    }

    @Override
    public int hashCode() {
        int result = getLoanDate().hashCode();
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + getBookIds().hashCode();
        return result;
    }
}
