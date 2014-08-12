package com.sirme.basicsecurity;

import javax.annotation.Resource;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.sirme.basicsecurity.business.IUsersService;
import com.sirme.basicsecurity.business.impl.UsersService;
import com.sirme.beans.UsersBean;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

public class BSAuthenticationProvider extends DaoAuthenticationProvider {

	@Resource(name = SpringSecurityConstants.USER_SERVICE)
	protected IUsersService usersService;
	
	public static final String DEFAULT_USERNAME = "USER";
	
	public String encodePassword(String pwd) {
		return getPasswordEncoder().encodePassword(pwd, null);
	}
	
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		
		String principal = (String) auth.getPrincipal();
        String password = (String) auth.getCredentials();
        
        //Transformación de password a MD5
        String passwordMD5 = getPasswordEncoder().encodePassword(password, null);        

		UserDetails details = getUserDetailsService().loadUserByUsername(principal);
        if (principal == null || principal.trim().length() == 0) {
        	throw new BadCredentialsException("Usuario obligatorio", null);
        } else if (password == null || password.trim().length() == 0) {
        	throw new BadCredentialsException("Password obligatoria", null);
        } else if (details == null || !details.getPassword().equals(passwordMD5)) {
			throw new BadCredentialsException("Usuario y/o password no válidos", null);
		} else if (!details.isEnabled()) {
			throw new DisabledException("La cuenta ha sido inhabilitada. Contacte con el administrador del sistema.");
		} else if (!details.isAccountNonLocked()) {
			throw new LockedException("La cuenta ha sido bloqueada. Contacte con el administrador del sistema.");
		} else if (!details.isAccountNonExpired()) {
			throw new AccountExpiredException("La cuenta ha caducado. Contacte con el administrador del sistema.");
		} else if (!details.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException("La password ha caducado. Contacte con el administrador del sistema.");
		}
        
        try {
			usersService.updateDateAccess( auth.getPrincipal().toString() );
		} catch (ValidationException | TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 	    return new UsernamePasswordAuthenticationToken(details, passwordMD5, details.getAuthorities());
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
