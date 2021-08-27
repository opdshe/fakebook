package com.fakebook.dongheon.security;

import com.fakebook.dongheon.security.handler.LogOutSuccessHandler;
import com.fakebook.dongheon.security.handler.LoginFailureHandler;
import com.fakebook.dongheon.security.handler.LoginSuccessHandler;
import com.fakebook.dongheon.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomUserDetailsService customUserDetailsService;
	private final LoginFailureHandler loginFailureHandler;
	private final LogOutSuccessHandler logOutSuccessHandler;
	private final LoginSuccessHandler loginSuccessHandler;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.and()
				.formLogin()
				.loginPage("/")
				.loginProcessingUrl("/login")
				.successHandler(loginSuccessHandler)
				.usernameParameter("userId")
				.defaultSuccessUrl("/feed")
				.failureUrl("/")
				.failureHandler(loginFailureHandler)
				.and()
				.logout()
				.logoutSuccessHandler(logOutSuccessHandler);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder());
	}
}
