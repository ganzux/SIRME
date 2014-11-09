package com.alcedomoreno.sirme.web.aut;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alcedomoreno.sirme.business.data.Role;
import com.alcedomoreno.sirme.web.ApplicationBean;
import com.alcedomoreno.sirme.web.util.BeanNameUtil;

public class BSUserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	Logger log = Logger.getLogger(BSUserAuthenticationSuccessHandler.class);
	
	private String defaultTargetUrl;
	private String contextRoot;
	
	public void setDefaultTargetUrl(String url) {
		this.defaultTargetUrl = url;
	}
	
	public String getDefaultTargetUrl() {
		return this.defaultTargetUrl;
	}
	
	public String getContextRoot(HttpServletRequest req) {
		if (this.contextRoot == null) {
			this.contextRoot = req.getContextPath();
		}
		
		return this.contextRoot;
	}
	
	public void setContextRoot(String url) {
		this.contextRoot = url;
	}
	
	private String getFullPath(HttpServletRequest req, String url) {
		return new StringBuffer().append(getContextRoot(req)).append(url).toString();
	}
	
	public BSUserAuthenticationSuccessHandler() {
		log.info("INFO|BSUserAuthenticationSuccessHandler.constructor()|Initializing success handler");
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException,ServletException {

	    log.info("INFO|BSUserAuthenticationSuccessHandler.onAuthenticationSuccess()|Sucessful login : [" + auth.getName() + "]");
	    String redirection = defaultTargetUrl;
	    boolean isRedirect = false;

	    /** Redireccionamos a la p�gina que nos indique el primer PROFILE que encontremos.
	     * OJO: En la lista de GrantedAuthority van a venir tanto los perfiles como sus permisos asociados. Debemos diferenciarlos.**/
	    if (auth.getAuthorities().size() > 0) {
	    	Iterator<GrantedAuthority> itr = auth.getAuthorities().iterator();
	    	while (isRedirect == false){
	    		GrantedAuthority ga = null;
		    	if(itr.hasNext())
		    		ga = itr.next();
		    	else
		    		isRedirect = true;
		    	
				if(ga instanceof Role){
					redirection = ((Role) ga).getURLSuccessLogin();
					isRedirect = true;
				}
	    	}
	    }
	    
	    try{
		    // Metemos el dato en el mapa de conexiones
		    getApplicationBean( req ).doRegisterOKLogin( auth.getPrincipal() );
	    } catch ( Exception e ){
	    	
	    }

	    redirection = getFullPath(req, redirection);
	    log.info("INFO|BSUserAuthenticationSuccessHandler.onAuthenticationSuccess()|Redirect to : [" + redirection + "]");
	    res.sendRedirect( redirection );
	}
	
	private ApplicationBean getApplicationBean(HttpServletRequest sessionEvent){
        HttpSession session = sessionEvent.getSession();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext( session.getServletContext() );
        ApplicationBean applicationBean = (ApplicationBean) ctx.getBean( BeanNameUtil.APP_BEAN );
        return applicationBean;
	}

}