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
     */
    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        log.debug("Configuring security - registering authentication provider");

        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * Configure secured URLs inside out application
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/loan/return/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/loan/create/**").access("hasAnyRole('ROLE_USER')")
                .antMatchers("/book/create/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/book/edit/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/book/delete/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/book/addCopy/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/category/create/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/category/edit/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/category/delete/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/book/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/category/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/loan/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/user/detail").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/user/**").access("hasAnyRole('ROLE_ADMIN')")
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
