package cz.muni.fi.pa165.library.dto;

/**
 * @author Dávid Lupták
 * @version 18.12.2016
 */
public class BookUpdateDTO extends BookNewDTO {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookUpdateDTO)) return false;

        BookUpdateDTO book = (BookUpdateDTO) o;

        return super.equals(book) && (getId() != null && getId().equals(book.getId()));
    }

    @Override
    public int hashCode() {
        return super.hashCode() + ((getId() != null) ? getId().hashCode() : 0);
    }
}
