package com.sirme.util;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sirme.beans.ApplicationBean;

public class SessionListener implements HttpSessionListener,Serializable{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 8555146257757087681L;
	private Logger log = LoggerFactory.getLogger( SessionListener.class );
	private static final String CLASS_NAME = "SessionListener";

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////
	public void sessionCreated(HttpSessionEvent arg0) {
		MyLogger.info(log, CLASS_NAME, "sessionCreated", arg0.getSession().getId());
		getApplicationBean(arg0).doRegisterUser( arg0.getSession() );
	}
 
	public void sessionDestroyed(HttpSessionEvent arg0) {
		MyLogger.info(log, CLASS_NAME, "sessionDestroyed", arg0.getSession().getId());
		getApplicationBean(arg0).doUnregisterUser( arg0.getSession() );
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////

	private ApplicationBean getApplicationBean(HttpSessionEvent sessionEvent){
        HttpSession session = sessionEvent.getSession();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext( session.getServletContext() );
        ApplicationBean applicationBean = (ApplicationBean) ctx.getBean( BeanNameUtil.APP_BEAN );
        return applicationBean;
	}

	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////

}