package cz.muni.fi.pa165.library.web.config;

import cz.muni.fi.pa165.library.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.UserFacade;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bedrich Said
 */
@Component
public class LibraryAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private UserFacade userFacade;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDTO user = null;

        try {
            user = userFacade.findByEmail(email);
        } catch (NoEntityFoundException | IllegalArgumentException e) {
            return null;
        }

        UserAuthenticateDTO authData = new UserAuthenticateDTO(user.getId(), password);

        if (userFacade.authenticate(authData)) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            if (user.isAdmin()) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new UsernamePasswordAuthenticationToken(email, password, grantedAuthorities);
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authClass) {
        return authClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
