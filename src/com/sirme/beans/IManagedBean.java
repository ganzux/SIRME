package com.sirme.beans;

public interface IManagedBean {

	public String doInit();
	public String prepareAction();
	public String save();
	public String update();
	public String delete();
	public String back();
	public String cancel();

}
