package com.sirme.basicsecurity;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.business.IUsersService;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Resource( name=SpringSecurityConstants.USER_SERVICE )
	IUsersService usersService;

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		return usersService.get( userName );
	}

}
