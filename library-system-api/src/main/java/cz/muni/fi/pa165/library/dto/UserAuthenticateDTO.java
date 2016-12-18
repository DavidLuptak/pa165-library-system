package cz.muni.fi.pa165.library.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Martin
 * @version 13.11.2016
 */
public class UserAuthenticateDTO {
    @NotNull
    private Long userId;
    @NotNull
    private String password;

    public UserAuthenticateDTO() {
        this.userId = null;
        this.password = null;
    }
    
    public UserAuthenticateDTO(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
