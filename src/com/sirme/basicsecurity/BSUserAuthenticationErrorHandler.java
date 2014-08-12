package com.sirme.basicsecurity;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.sirme.beans.ApplicationBean;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.MyLogger;

public class BSUserAuthenticationErrorHandler implements AuthenticationFailureHandler {

	Logger log = Logger.getLogger(BSUserAuthenticationErrorHandler.class);
	private static org.slf4j.Logger log2 = org.slf4j.LoggerFactory.getLogger( BSUserAuthenticationErrorHandler.class );
	
	private String defaultTargetUrl;
	private String contextRoot;
	
	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;
	
	public void setDefaultTargetUrl(String url) {
		defaultTargetUrl = url;
	}
	
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}
	
	public String getContextRoot(HttpServletRequest req) {
		if (contextRoot == null)
			contextRoot = req.getContextPath();
		return contextRoot;
	}
	
	public void setContextRoot(String url) {
		this.contextRoot = url;
	}
	
	private String getFullPath(HttpServletRequest req, String url) {
		return new StringBuffer().append(getContextRoot(req)).append(url).toString();
	}
	
	public BSUserAuthenticationErrorHandler() {
		log.info("INFO|BSUserAuthenticationErrorHandler.constructor()|Initializing success handler");
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
		HttpServletResponse response, AuthenticationException ae)
		throws IOException, ServletException {

	    log.info("INFO|BSUserAuthenticationErrorHandler.onAuthenticationFailure()|Failed login : [" + ae.getMessage() + "]");
	    //ResourceBundle bundle = ResourceBundle.getBundle("messages");
	    String redirection = defaultTargetUrl;
	    
	    //String error = bundle.getString("error")+": " + ae.getMessage();
	    String error = "Error: " + ae.getMessage();
	    redirection = getFullPath(request, redirection);
	    
	    MyLogger.error(log2, "BSUserAuthenticationErrorHandler", "onAuthenticationFailure", request.getRemoteAddr(), ae.getAuthentication().getPrincipal(), ae.getAuthentication().getCredentials() );
	    
	    applicationBean.addFail();

	    log.info("INFO|BSUserAuthenticationErrorHandler.onAuthenticationSuccess()|Redirect to : [" + redirection + "]");
	    request.getSession().setAttribute("error", error);
	    response.sendRedirect( redirection );
	    
	}

}