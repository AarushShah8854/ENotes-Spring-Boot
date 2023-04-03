package com.enotes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.enotes.entity.UserDtls;
import com.enotes.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDtls userDtls = userRepository.findByEmail(username);
		
		if(userDtls!=null) {
			CustomUserDtls customUserDtls = new CustomUserDtls(userDtls);
			return customUserDtls;
		} else {
			throw new UsernameNotFoundException("User doesn't exist");
		}
	}

}
