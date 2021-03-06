package cz.muni.fi.pa165.library.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@JsonIgnoreProperties({"passwordHash", "loans"})
public abstract class UserDTOMixin {
}
