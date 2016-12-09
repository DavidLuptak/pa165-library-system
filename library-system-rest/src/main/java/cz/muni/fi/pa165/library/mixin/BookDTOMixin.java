package cz.muni.fi.pa165.library.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@JsonIgnoreProperties({"bookCopies", "categories"})
public abstract class BookDTOMixin {
}
