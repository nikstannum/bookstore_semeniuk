package com.belhard;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
						
						.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
						.invalidSessionUrl("/")
						.sessionFixation()
						.changeSessionId()
						.and()
						
						
						
						
						.authorizeRequests()
						
						
						
						.mvcMatchers(HttpMethod.GET, "/books/create_book_form").hasAuthority("MANAGER")
						.mvcMatchers(HttpMethod.GET, "/css/**", "/js/**", "/images/**", "/", "/books/**", "/api/books",
										"/users/login_form", "/users/create_user_form", "/orders/cart", "/users/logout",
										"/api/cart/**")
						.permitAll()
						.mvcMatchers(HttpMethod.POST, "/api/messages", "/users/create_user", "/users/login")
						.permitAll()
						.mvcMatchers(HttpMethod.POST, "/orders/checkout").permitAll()
						.mvcMatchers(HttpMethod.DELETE, "/api/books").hasAuthority("MANAGER")
						.mvcMatchers(HttpMethod.POST, "/api/books").hasAuthority("MANAGER")
						.mvcMatchers(HttpMethod.PUT, "/api/books").hasAuthority("MANAGER")
						.mvcMatchers(HttpMethod.GET, "/users/**", "/orders/**").permitAll()
						.anyRequest().denyAll()
						.and()
						
						// login conf
						.formLogin().loginPage("/users/login_form")
						.defaultSuccessUrl("/")
						.failureUrl("/users/login_form?error") // FIXME to do redirect to login_form?error instead of /error
						.permitAll()
						.and()
						
						// logout conf
						.logout()
						.logoutUrl("/users/logout")
						.clearAuthentication(true)
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessUrl("/users/login?logout")
						.permitAll()
						.and()

//						.csrf().disable()
						
						

										
						.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user1 = User.withDefaultPasswordEncoder().username("useruser@user").password("user").authorities("USER").build();
//		UserDetails user2 = User.withDefaultPasswordEncoder().username("managermanager@manager").password("manager").authorities("USER", "MANAGER").build();
//		return new InMemoryUserDetailsManager(user1, user2);
//	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
		manager.setUsersByUsernameQuery("SELECT u.email, u.password, u.deleted as enabled FROM users u WHERE u.email = ?");
		manager.setAuthoritiesByUsernameQuery("SELECT u.email, r.name FROM users u, role r WHERE u.role_id = r.role_id and u.email = ?");
		return manager;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:8080"));
		corsConfiguration.addAllowedMethod(HttpMethod.GET);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
		
		
	}
	
	
	
}
