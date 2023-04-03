package com.enotes.config;

import java.util.Arrays;
import java.util.Collection;

import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enotes.entity.UserDtls;

public class CustomUserDtls implements UserDetails {
	
	private UserDtls userDetails;

	public CustomUserDtls(UserDtls userDetails) {
		super();
		this.userDetails = userDetails;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userDetails.getRole());
		
		return Arrays.asList(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userDetails.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userDetails.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
