package sieger.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//import sieger.filter.IdFilter;
import sieger.filter.JwtFilter;

/**
 * The configuration for security of the server app.
 * @author Irvan Sian Syah Putra
 *
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	/**
	 * The Filter that is used to catch the JWT Token in header.
	 */
	@Autowired
	private JwtFilter authFilter;

	/**
	 * HTTP Security configuration.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        	.authorizeRequests().antMatchers("/").permitAll().and()
        	.sessionManagement().sessionCreationPolicy(
        			SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.addFilterBefore(authFilter, BasicAuthenticationFilter.class);
    }
	
	/**
	 * This configuration is used to prevent Spring Boot auto configuration.
	 */
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
}
