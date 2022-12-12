package com.belhard.aop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.belhard.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Component
@Aspect
@RequiredArgsConstructor
public class AuthoritiesChanger {

	private final SessionRegistry sessionRegistry;
	private final UserDetailsService userDetailsService;

	@AfterReturning("@annotation(UpdateAuthorities)")
	private void changeAuthorities(JoinPoint jp) {
		UserDto updatedUser = (UserDto) jp.getArgs()[0];
		UserDetails userDetails = userDetailsService.loadUserByUsername(updatedUser.getEmail());
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
		authorities = new ArrayList<>();
		SimpleGrantedAuthority newAuthority = new SimpleGrantedAuthority(updatedUser.getUserRoleDto().toString());
		authorities.add(newAuthority);
		List<Object> principals = sessionRegistry.getAllPrincipals();
		for (Object principal : principals) {
			UserDetails userFromList = (UserDetails) principal;
			String emailFromList = userFromList.getUsername();
			String emailUpdatedUser = updatedUser.getEmail();
			if (emailFromList.equalsIgnoreCase(emailUpdatedUser)) {
				List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
				if (sessionInformations != null) {
					for (SessionInformation info : sessionInformations) {
						info.expireNow();
					}
				}
			}
		}
	}
}
