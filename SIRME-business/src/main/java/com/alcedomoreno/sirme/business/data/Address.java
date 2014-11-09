package com.alcedomoreno.sirme.business.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alcedomoreno.sirme.business.data.Month.MonthE;
import com.alcedomoreno.sirme.business.transform.AddressTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Address implements BusinessObject,Cloneable{

	private int idAddress;
	private String mainAddress;
	private String mainProv;
	private String mainPobl;
	private String mainPostalCode;
	private String other;
	private String location;
	private String amount;
	private Collection<MonthE> months;

	private Customer customer;
	
	private Collection<Work> works;

	public int getIdAddress() {
		return idAddress;
	}
	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}
	public String getMainAddress() {
		return mainAddress;
	}
	public void setMainAddress(String mainAddress) {
		this.mainAddress = mainAddress;
	}
	public String getMainProv() {
		return mainProv;
	}
	public void setMainProv(String mainProv) {
		this.mainProv = mainProv;
	}
	public String getMainPobl() {
		return mainPobl;
	}
	public void setMainPobl(String mainPobl) {
		this.mainPobl = mainPobl;
	}
	public String getMainPostalCode() {
		return mainPostalCode;
	}
	public void setMainPostalCode(String mainPostalCode) {
		this.mainPostalCode = mainPostalCode;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Customer getCustomer() {
		return customer;
	}
	public Collection<Work> getWorks() {
		return works;
	}
	public void setWorks(Collection<Work> works) {
		this.works = works;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Double getAmountNumber(){
		try{
			return Double.parseDouble( amount.replaceAll(",", ".")  );
		} catch( Exception e){
			return 0d;
		}
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		return AddressTransform.class;
	}
	
	public Collection<MonthE> getMonths() {
		return months;
	}
	public boolean includeMonth( int month1BasedId ){
		if ( months!=null )
			for ( MonthE month:months ){
				if ( month.getValue() == month1BasedId )
					return true;
			}
		
		return false;
	}
	public String getMonthsBinary(){
		String binaryMonths = "";
		
		boolean add = false;
		if ( months != null ){
			for ( int i=1;i<13;i++){
				add = false;
				for ( MonthE month:months ){
					if ( month.getValue()==i )
						add = true;
				}
				binaryMonths += (add?1:0);
			}
		}
		
		return (binaryMonths.isEmpty()?"":binaryMonths);
	}
	public List<String> getMonthsInt(){
		List<String> intMonths = new ArrayList<String>();

		if ( months != null )
			for ( int i=1;i<13;i++)
				for ( MonthE month:months )
					if ( month.getValue()==i )
						intMonths.add( String.valueOf(i) );

		return intMonths;
	}
	public void setMonths(Collection<MonthE> months) {
		this.months = months;
	}
	public void setMonths(String monthStr) {
		Collection<MonthE> months = new ArrayList<MonthE>();
		if ( monthStr != null && !monthStr.isEmpty() ){
			monthStr = monthStr.toUpperCase();
			if ( monthStr.contains("ENE") )
				months.add( MonthE.ENERO );
			if ( monthStr.contains("FEB") )
				months.add( MonthE.FEBRERO );
			if ( monthStr.contains("MAR") )
				months.add( MonthE.MARZO );
			if ( monthStr.contains("ABR") )
				months.add( MonthE.ABRIL );
			if ( monthStr.contains("MAY") )
				months.add( MonthE.MAYO );
			if ( monthStr.contains("JUN") )
				months.add( MonthE.JUNIO );
			if ( monthStr.contains("JUL") )
				months.add( MonthE.JULIO );
			if ( monthStr.contains("AGO") )
				months.add( MonthE.AGOSTO );
			if ( monthStr.contains("SEP") )
				months.add( MonthE.SEPTIEMBRE );
			if ( monthStr.contains("OCT") )
				months.add( MonthE.OCTUBRE );
			if ( monthStr.contains("NOV") )
				months.add( MonthE.NOVIEMBRE );
			if ( monthStr.contains("DIC") )
				months.add( MonthE.DICIEMBRE );
		}
		
		this.months = months;
	}
	public void setMonths(List<String> monthInt) {
		Collection<MonthE> months = new ArrayList<MonthE>();
		if ( monthInt != null ){
			for ( String numbre:monthInt ){
				if ( numbre.equals("1") )
					months.add( MonthE.ENERO );
				else if ( numbre.equals("2") )
					months.add( MonthE.FEBRERO );
				else if ( numbre.equals("3") )
					months.add( MonthE.MARZO );
				else if ( numbre.equals("4") )
					months.add( MonthE.ABRIL );
				else if ( numbre.equals("5") )
					months.add( MonthE.MAYO );
				else if ( numbre.equals("6") )
					months.add( MonthE.JUNIO );
				else if ( numbre.equals("7") )
					months.add( MonthE.JULIO );
				else if ( numbre.equals("8") )
					months.add( MonthE.AGOSTO );
				else if ( numbre.equals("9") )
					months.add( MonthE.SEPTIEMBRE );
				else if ( numbre.equals("10") )
					months.add( MonthE.OCTUBRE );
				else if ( numbre.equals("11") )
					months.add( MonthE.NOVIEMBRE );
				else if ( numbre.equals("12") )
					months.add( MonthE.DICIEMBRE );
			}
		}
		
		this.months = months;
	}
	
	public String getMonthsStr(){
		StringBuilder sb = new StringBuilder();
		
		if ( months.contains(MonthE.ENERO) )
			sb.append("Ene,");
		if ( months.contains(MonthE.FEBRERO) )
			sb.append("Feb,");
		if ( months.contains(MonthE.MARZO) )
			sb.append("Mar,");
		if ( months.contains(MonthE.ABRIL) )
			sb.append("Abr,");
		if ( months.contains(MonthE.MAYO) )
			sb.append("May,");
		if ( months.contains(MonthE.JUNIO) )
			sb.append("Jun,");
		if ( months.contains(MonthE.JULIO) )
			sb.append("Jul,");
		if ( months.contains(MonthE.AGOSTO) )
			sb.append("Ago,");
		if ( months.contains(MonthE.SEPTIEMBRE) )
			sb.append("Sep,");
		if ( months.contains(MonthE.OCTUBRE) )
			sb.append("Oct,");
		if ( months.contains(MonthE.NOVIEMBRE) )
			sb.append("Nov,");
		if ( months.contains(MonthE.DICIEMBRE) )
			sb.append("Dic,");
		
		return (sb.length()==0?"":sb.toString().substring( 0,sb.length()-1) );
	}
	
	public static String transformIntoMonth(String binaryInt){
		String binary = null;

		if( binaryInt!=null && !binaryInt.isEmpty() )
			binary = String.valueOf( Integer.parseInt(binaryInt,2) );

		return binary;
	}
	
}