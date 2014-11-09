package com.alcedomoreno.sirme.web.aut;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alcedomoreno.sirme.business.services.UsersService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Resource( name=ServiceConstants.USER_SERVICE )
	UsersService usersService;

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		return usersService.get( userName );
	}

}
