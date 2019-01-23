package com.javasampleapproach.mysql.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity


public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	// password encoder reference implemented in WebMvcConfig.java
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Data source implemented out of the box by Spring Boot.
	// provided the database information in the application.properties file
	@Autowired
	private DataSource dataSource;

	// Reference to user and role queries stored in application.properties file
	
	@Value("${spring.queries.customers-query}")
	private String customersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	// AuthenticationManagerBuilder provides a mechanism to get a user based on the
	// password encoder, data source, customer query and role query.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(customersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	
	// define the antMatchers to provide access based on the role(s)
	// the parameters for the login process
	// the success login page
	// the failure login page, and the logout page.

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http
		.requestMatchers()
		.antMatchers("/login", "/",  "/customer/**", "/registration", "/admin/**")
		.and()
		.authorizeRequests()
		.antMatchers("/admin/**").hasAuthority("ADMIN").antMatchers("/customer/**").hasAuthority("CUSTOMER")
		.antMatchers("/login", "/registration", "/").permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic().and().csrf().disable()
		
		.formLogin()  //login configuration
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/customer/home")	
		.and().logout()    //logout configuration
		.logoutUrl("/logout") 
		.logoutSuccessUrl("/login")
		.and().exceptionHandling() //exception handling configuration
		.accessDeniedPage("/error");
		
		

		/*http.formLogin().loginPage("/login")
		.permitAll()
		.defaultSuccessUrl("/home").and()
		.logout().logoutUrl("/logout").logoutSuccessUrl("/login").and()
		//.defaultSuccessUrl("/home").and()
		.requestMatchers()
		.antMatchers("/login", "/",  "/customer/**", "/registration", "/admin/**")
		.and()
		.authorizeRequests()
		.antMatchers("/admin/**").hasAuthority("ADMIN").antMatchers("/customer/**").hasAuthority("CUSTOMER")
		.antMatchers("/login", "/registration", "/").permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic().and().csrf().disable();
		*/
		
		
//		http.authorizeRequests()
//		.antMatchers("/registration", "/login").permitAll().antMatchers("/welcome").permitAll()
//		.antMatchers("/admin/**").hasAuthority("ADMIN").antMatchers("/customer/**").hasAuthority("CUSTOMER")
//		.anyRequest().authenticated().and()
//		.httpBasic().and().csrf().disable()
//		.formLogin().loginPage("/login").permitAll().and()
//		.logout().logoutUrl("/logout").logoutSuccessUrl("/login").and()
//		;
//		
		
	}

	

	// because implemented Spring Security need to let Spring knows that our
	// resources folder can be served skipping the antMatchers defined.

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}