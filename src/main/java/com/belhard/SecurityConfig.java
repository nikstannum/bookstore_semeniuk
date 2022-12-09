package com.belhard;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
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
						.sessionFixation().changeSessionId().maximumSessions(1)
						.expiredSessionStrategy(sessionInformationExpiredStrategy()) // FIXME
						.sessionRegistry(sessionRegistry()) // FIXME
						.expiredUrl("/users/logout") // FIXME
						.and().and()

						.authorizeRequests().mvcMatchers("/css/**", "/js/**", "/images/**", "/", "/api/**").permitAll()
						.mvcMatchers(HttpMethod.POST, "/users/login").permitAll()

						// books
						.mvcMatchers(HttpMethod.GET, "/books/**").permitAll().mvcMatchers(HttpMethod.POST, "/books/**")
						.hasAuthority("MANAGER").mvcMatchers(HttpMethod.PUT, "/books/**").hasAuthority("MANAGER")
						.mvcMatchers(HttpMethod.DELETE, "/books/**").hasAuthority("MANAGER")

						// users
						.mvcMatchers(HttpMethod.GET, "/users/create_user_form", "/users/update", "/users/login_form")
						.permitAll()
						.mvcMatchers(HttpMethod.POST, "/users/create_user", "/users/update_user", "/users/login",
										"/users/logout")
						.permitAll()

						.mvcMatchers(HttpMethod.GET, "/users/**").authenticated()
						.mvcMatchers(HttpMethod.POST, "/users/**").authenticated()
						.mvcMatchers(HttpMethod.PUT, "/users/**").authenticated()
						.mvcMatchers(HttpMethod.DELETE, "/users/**").authenticated()

						// orders
						.mvcMatchers(HttpMethod.GET, "/orders/cart").permitAll()
						.mvcMatchers(HttpMethod.POST, "/orders/checkout").permitAll()

						.mvcMatchers(HttpMethod.GET, "/orders/**").hasAnyAuthority("MANAGER", "ADMIN")
						.mvcMatchers(HttpMethod.POST, "/orders/**").hasAnyAuthority("MANAGER", "ADMIN")
						.mvcMatchers(HttpMethod.PUT, "/orders/**").hasAnyAuthority("MANAGER", "ADMIN")
						.mvcMatchers(HttpMethod.DELETE, "/orders/**").hasAnyAuthority("MANAGER", "ADMIN")

						.anyRequest().permitAll()
//						.anyRequest().denyAll()
						.and()

						// login conf
						.formLogin().loginPage("/login_form").loginProcessingUrl("/users/login")
						.usernameParameter("email").successHandler(null).defaultSuccessUrl("/", true)
						.failureUrl("/login_form?error").permitAll().and()

						// logout conf
						.logout().logoutUrl("/users/logout").clearAuthentication(true).invalidateHttpSession(true)
						.deleteCookies("JSESSIONID").logoutSuccessUrl("/login_form?logout").permitAll().and()

//						.csrf().disable()
						.build();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() { // TODO something unknown
		return new HttpSessionEventPublisher();
	}

	@Bean
	public SessionRegistry sessionRegistry() { // TODO something unknown
		SessionRegistryImpl registry = new SessionRegistryImpl();
		return registry;
	}

	@Bean
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() { // TODO something unknown
		SimpleRedirectSessionInformationExpiredStrategy strategy = new SimpleRedirectSessionInformationExpiredStrategy(
						"/users/logout");
		return strategy;
	}

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
		manager.setUsersByUsernameQuery(
						"SELECT u.email, u.password, u.deleted = false as enabled FROM users u WHERE u.email = ?");
		manager.setAuthoritiesByUsernameQuery(
						"SELECT u.email, r.name FROM users u, role r WHERE u.role_id = r.role_id and u.email = ?");
		return manager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
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
