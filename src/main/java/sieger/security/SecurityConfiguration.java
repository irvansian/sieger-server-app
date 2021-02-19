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

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtFilter authFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        	.authorizeRequests().antMatchers("/").permitAll().and()
        	.sessionManagement().sessionCreationPolicy(
        			SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.addFilterBefore(authFilter, BasicAuthenticationFilter.class);
    }
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    // ALTHOUGH THIS SEEMS LIKE USELESS CODE,
	    // IT'S REQUIRED TO PREVENT SPRING BOOT AUTO-CONFIGURATION
	    return super.authenticationManagerBean();
	}
}
