package cz.muni.fi.pa165.library.dto;

import cz.muni.fi.pa165.library.enums.BookState;

/**
 * @author Bedrich Said
 */
public class BookDTO {
    private Long id;
    private String name;
    private String authorName;
    private String isbn;

    public BookDTO() {
    }

    public BookDTO(String name, String authorName, String isbn) {
        this.name = name;
        this.authorName = authorName;
        this.isbn = isbn;
    }

    //private List<LoanDTO> loans;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /*public List<LoanDTO> getLoans() {
        return loans;
    }*/

    /*public void setLoans(List<LoanDTO> loans) {
        this.loans = loans;
    }*/

    //todo: generate equals and hash code
}
