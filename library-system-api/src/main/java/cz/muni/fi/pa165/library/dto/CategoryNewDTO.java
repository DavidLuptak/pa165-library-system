package cz.muni.fi.pa165.library.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
public class CategoryNewDTO {

    @NotBlank
    private String name;

    public CategoryNewDTO() {
    }

    public CategoryNewDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryNewDTO)) return false;

        CategoryNewDTO category = (CategoryNewDTO) o;

        return getName() != null ? getName().equals(category.getName()) : category.getName() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }
}
