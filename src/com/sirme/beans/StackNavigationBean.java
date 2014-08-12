package com.sirme.beans;

import java.io.Serializable;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sirme.util.BeanNameUtil;
import com.sirme.util.MyLogger;

@Component( BeanNameUtil.STACK_BEAN )
@Scope("session")
public class StackNavigationBean implements Serializable {
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( StackNavigationBean.class );
	private static final String CLASS_NAME = "StackNavigationBean";
	
	private Stack<String> navigationStack;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public StackNavigationBean(){				
		MyLogger.info(log, CLASS_NAME, "StackNavigationBean", "New Instance");
		initStack();
	}
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                   Control sobre la Pila                   //
	///////////////////////////////////////////////////////////////
	public String popPage(){
		try{
			return navigationStack.pop();
		} catch( Exception ew){
			return null;
		}
	}
	public void pushPage(String page){
		navigationStack.push( page );
	}
	public void initStack(){
		navigationStack = new Stack<String>();
	}
	public Stack<String> getNavigationStack() {
		return navigationStack;
	}
	public boolean isEmpty(){
		return navigationStack.isEmpty();
	}
	public boolean getEmpty(){
		return isEmpty();
	}
	public void setPushPage(String page) {
		pushPage( page );
	}
	public String getPopPage(){
		return popPage();
	}
	public String getNextPage(){
		String next = getPopPage();
		setPushPage( next );
		return next;
	}
	
	///////////////////////////////////////////////////////////////
	//				 Fin del Control sobre la Pila               //
	///////////////////////////////////////////////////////////////


	

}