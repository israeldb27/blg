package com.busqueumlugar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	
	 
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
			authenticationMgr.inMemoryAuthentication()
				.withUser("israel")
				.password("a")
				.authorities("ROLE_USER");
		}
		 
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/main").access("hasRole('ROLE_USER')")
				.anyRequest().authenticated()							
				.and()
					.formLogin().loginPage("/home")
					.defaultSuccessUrl("/main")
					.failureUrl("/loginPage?error")
					.usernameParameter("login").passwordParameter("password")				
				.and()
					.logout().logoutSuccessUrl("/loginPage?logout"); 
			
		}
	 
	 

	 
	    @Bean
	    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
	        return new AuthenticationTrustResolverImpl();
	    }

}
