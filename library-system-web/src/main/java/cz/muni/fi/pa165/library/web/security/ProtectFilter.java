package cz.muni.fi.pa165.library.web.security;

import cz.muni.fi.pa165.library.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.exception.NoEntityFoundException;
import cz.muni.fi.pa165.library.facade.UserFacade;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Bedrich Said
 */
@WebFilter(urlPatterns = {"/login/validate"})
public class ProtectFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String logname = req.getParameter("username");
        String password = req.getParameter("password");

        UserFacade userFacade = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext()).getBean(UserFacade.class);
        UserDTO matchingUser;
        try {
            matchingUser = userFacade.findByEmail(logname);
        } catch(NoEntityFoundException | IllegalArgumentException e) {
            response401(response);
            return;
        }
        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO();
        userAuthenticateDTO.setUserId(matchingUser.getId());
        userAuthenticateDTO.setPassword(password);
        if (!userFacade.isAdmin(matchingUser)) {
            response401(response);
            return;
        }
        if (!userFacade.authenticate(userAuthenticateDTO)) {
            response401(response);
            return;
        }
        request.setAttribute("authenticatedUser", matchingUser);
        chain.doFilter(request, response);
    }
    
    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> Go away ...</body></html>");
    }
    
}
