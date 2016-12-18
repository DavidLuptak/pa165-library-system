package cz.muni.fi.pa165.library.dto;

/**
 * @author Lenka (433591)
 * @version 18.12.2016
 */
public class CategoryUpdateDTO extends CategoryNewDTO {

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
        if (!(o instanceof CategoryUpdateDTO)) return false;

        CategoryUpdateDTO category = (CategoryUpdateDTO) o;

        return super.equals(category) && (getId() != null && getId().equals(category.getId()));
    }

    @Override
    public int hashCode() {
        return super.hashCode() + ((getId() != null) ? getId().hashCode() : 0);
    }
}
