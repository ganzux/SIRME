package com.alcedomoreno.sirme.web;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alcedomoreno.sirme.business.data.User;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alcedomoreno.sirme.business.services.UsersService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.SuperUser;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

public abstract class ManagedBean implements Serializable,IManagedBean{

	private static final long serialVersionUID = 1L;

	@Resource(name = ServiceConstants.USER_SERVICE )
	private UsersService usersService;

	private String theme = "blitzer";
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}

	private String pageOption;
	public String getPageOption() {
		return pageOption;
	}
	public void setPageOption(String pageOption) {
		this.pageOption = pageOption;
	}


	///////////////////////////////////////////////////////////////
	//                          Usuarios                         //
	///////////////////////////////////////////////////////////////
	private HttpSession session;
	private String ipAddress;
	private Long createTime;
	private com.alcedomoreno.sirme.business.data.User user;
	
	@PostConstruct
	public void init(){
		try{
			
			initMonths();
			
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = usersService.getByCodeUserLoadAllData( u.getUsername() );
			
			this.session = session;
			this.ipAddress = request.getRemoteAddr();
			this.createTime = new Date().getTime();
		} catch ( Exception e) {
			//e.printStackTrace();
		}
	}
	
	public HttpSession getSession() {
		return session;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public com.alcedomoreno.sirme.business.data.User getUser() {
		if ( user==null )
			init();
		return user;
	}
	public void setUser(com.alcedomoreno.sirme.business.data.User user) {
		this.user = user;
	}
	public boolean isSuperUser(){
		return SuperUser.isSU( getUser().getIdUser() );
	}
	///////////////////////////////////////////////////////////////
	//				      Fin de los Usuarios                    //
	///////////////////////////////////////////////////////////////

	private Date myDate;
	public Date getMyDate() {
		return myDate;
	}
	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}

	private Map<String, String> months;
	private SelectItem[] monthOptions;
	
	private void initMonths(){
		monthOptions = new SelectItem[ 13 ];
		
		months = new LinkedHashMap<String,String>();
		months.put("Enero","1");
		months.put("Febrero","2");
		months.put("Marzo","3");
		months.put("Abril","4");
		months.put("Mayo","5");
		months.put("Junio","6");
		months.put("Julio","7");
		months.put("Agosto","8");
		months.put("Septiembre","9");
		months.put("Octubre","10");
		months.put("Noviembre","11");
		months.put("Diciembre","12");
		
		monthOptions[ 0 ] = new SelectItem(""		, "Seleccione...");  
		monthOptions[ 1 ] = new SelectItem("Ene"	,"Enero");
		monthOptions[ 2 ] = new SelectItem("Feb"	,"Febrero");
		monthOptions[ 3 ] = new SelectItem("Mar"	,"Marzo");
		monthOptions[ 4 ] = new SelectItem("Abr"	,"Abril");
		monthOptions[ 5 ] = new SelectItem("May"	,"Mayo");
		monthOptions[ 6 ] = new SelectItem("Jun"	,"Junio");
		monthOptions[ 7 ] = new SelectItem("Jul"	,"Julio");
		monthOptions[ 8 ] = new SelectItem("Ago"	,"Agosto");
		monthOptions[ 9 ] =	new SelectItem("Sep"	,"Septiembre");
		monthOptions[ 10 ] =new SelectItem("Oct"	,"Octubre");
		monthOptions[ 11 ] =new SelectItem("Nov"	,"Noviembre");
		monthOptions[ 12 ] =new SelectItem("Dic"	,"Diciembre");
	}
	
	public Map<String, String> getMonths() {
		return months;
	}
	public void setMonths(Map<String, String> months) {
		this.months = months;
	}
	public SelectItem[] getMonthOptions() {
		return monthOptions;
	}
	public void setMonthOptions(SelectItem[] monthOptions) {
		this.monthOptions = monthOptions;
	}
	public Collection<String> getMonthsCollection(){
		return months.keySet();
	}
	
	
	public void postProcessXLS(Object document) {
	    HSSFWorkbook wb = (HSSFWorkbook) document;
	    HSSFSheet sheet = wb.getSheetAt(0);
	    HSSFRow header = sheet.getRow(0);

	    HSSFCellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setFillForegroundColor(HSSFColor.GREEN.index); 
	    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	    for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
	        HSSFCell cell = header.getCell(i);

	        cell.setCellStyle(cellStyle);
	    }
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
	    Document pdf = (Document) document;
	    pdf.open();
	    pdf.setPageSize(PageSize.A4);

	    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	    String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "logo.jpg";

	    pdf.add(Image.getInstance(logo));
	}
}
