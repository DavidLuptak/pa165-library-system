package cz.muni.fi.pa165.library.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.inject.Inject;

/**
 *
 * @author Bedrich Said
 */
@Configuration
@EnableWebSecurity
public class WebApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebApplicationSecurityConfiguration.class);

    @Inject
    private LibraryAuthenticationProvider authenticationProvider;

    /**
     * Initialize authentication in our application - use custom authentication provider
     * @param auth AuthenticationManagerBuilder
     * @throws java.lang.Exception
     */
    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        log.debug("Configuring security - registering authentication provider");

        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * Configure secured URLs inside out application
     * @param http which HTTP security to be configured
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/loan/return/**").hasAnyRole("ADMIN")
                .antMatchers("/book/create/**").hasAnyRole("ADMIN")
                .antMatchers("/book/edit/**").hasAnyRole("ADMIN")
                .antMatchers("/book/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/book/addCopy/**").hasAnyRole("ADMIN")
                .antMatchers("/category/create/**").hasAnyRole("ADMIN")
                .antMatchers("/category/edit/**").hasAnyRole("ADMIN")
                .antMatchers("/category/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/book/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/category/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/loan/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/detail").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/index?redirected=true").loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/index?error=invalidLoginAttempt")
                .usernameParameter("user").passwordParameter("pass")
                .and()
                .logout().logoutSuccessUrl("/")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
		.exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
                .csrf().disable();

    }

}
