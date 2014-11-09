package com.alcedomoreno.sirme.web.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.Contact;
import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.business.data.TypeCustomer;

public class CustomerXLSExtract {

	private static final String C_NUM_CLIENTE 	= "Nº Cliente";

	private static CustomerXLSExtract instance;
	private CustomerXLSExtract(){}
	public static CustomerXLSExtract getInstance(){
		if ( instance == null )
			instance = new CustomerXLSExtract();
		return instance;
	}

	public Customer getCustomerFromXLS( InputStream file ) throws IOException{
		Customer customer = new Customer();
		customer.setActive( true );
		Address address = new Address();
		customer.setTypeCustomer( new TypeCustomer() );

            HSSFWorkbook  workbook = new HSSFWorkbook ( file );
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int fila 	= 0;
            int columna = 0;
            boolean cpcontact = false;

            // FILAS
            while (rowIterator.hasNext()) {
            	// Si hemos hecho ya columnas, reseteamos la fila
            	if ( fila != 0 )
            		fila++;
            	columna = 1;
            	
            	// CELDA POR COLUMNAS
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                cpcontact = false;

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = getStringCellValue( cell );

                    // Hay dato en la celda
                    if ( (cellValue!=null && !cellValue.isEmpty() ) ){
                    	
                    	// Primera celda a partir de la cual miraremos
                    	if ( cellValue.contains(C_NUM_CLIENTE) ){
                    		fila++;
                    	}
                    	
                    	if ( fila>0 ){
	                    	switch( fila ){
	                    		case 1:
    	                    		if ( columna==2 ){
    	                    			cellValue = cellValue.substring( 0,cellValue.indexOf(".") );
    	                    			customer.setCodeCustomer( cellValue );
    	                    		}
    	                    		else if ( columna==4 )
    	                    			customer.setCifCustomer( cellValue );
                    			break;
                    			
	                    		case 2:
	                    			if ( columna==2 ){
    	                    			customer.setNameCustomer( cellValue );
    	                    			if ( cellValue.trim().toLowerCase().contains("comunidad")
    	                    				 || cellValue.trim().toLowerCase().contains("propietarios") )
    	                    				customer.setTypeCustomer( new TypeCustomer(1) );
    	                    			else
    	                    				customer.setTypeCustomer( new TypeCustomer(2) );
	                    			}
	                    			else if ( columna==6 ){
    	                    			customer.setServiceLocation( cellValue );
    	                    			address.setLocation( cellValue );
	                    			}
                    			break;
                    			
	                    		case 3:
    	                    		if ( columna==2 )
    	                    			customer.setMainAddress( cellValue );
    	                    		else if ( columna==6 ){
    	                    			customer.setServiceAddress( cellValue );
    	                    			address.setMainAddress( cellValue );
    	                    		}
                    			break;
                    			
	                    		case 4:
    	                    		if ( columna==2 )
    	                    			customer.setMainPobl( cellValue );
    	                    		else if ( columna==6 ){
    	                    			customer.setServicePobl( cellValue );
    	                    			address.setMainPobl( cellValue );
    	                    		}
                    			break;
                    			
	                    		case 5:
    	                    		if ( columna==2 )
    	                    			customer.setMainProv( cellValue );
    	                    		else if ( columna==4 ){
    	                    			try{
    	                    				if ( cellValue.contains("E") )
    	                    					cellValue = cellValue.substring(0, cellValue.indexOf("E")).replaceAll("\\.", "");
    	                    				cellValue = cellValue.replaceAll(" ", "").replaceAll("", "");
    	                    			} catch( Exception e){}
    	                    			customer.setMainPhone( cellValue );
    	                    		}
    	                    		else if ( columna==6 ){
    	                    			customer.setServiceProv( cellValue );
    	                    			address.setMainProv( cellValue );
    	                    		}
                    			break;
                    			
	                    		case 6:
	                    			if ( columna==1 ){
	                    				if ( "contacto:".equalsIgnoreCase(cellValue.trim()) )
	                    					cpcontact = true;
	                    			}
	                    			if ( columna==2 )
    	                    			try{
    	                    				if ( !cpcontact )
    	                    					customer.setMainPostalCode( cellValue.substring(0,cellValue.indexOf(".")) );
    	                    				else{
    	                    					Contact c = new Contact();
    		                    				c.setNameContact("Contacto");
    		                    				c.setDataContact( cellValue );
    		                    				if ( customer.getContacts()==null )
    		                    					customer.setContacts( new ArrayList<Contact>() );
    		                    				customer.getContacts().add( c );
    	                    				}
    	                    			} catch( Exception e){
    	                    			}
	                    			else if ( columna==6 )
	                    				try{
    	                    				customer.setServicePostalCode( cellValue.substring(0,cellValue.indexOf(".")) );
    	                    				address.setMainPostalCode( cellValue.substring(0,cellValue.indexOf(".")) );
    	                    			} catch( Exception e){
    	                    			}
	                    		break;
	                    		
	                    		case 7:
	                    			if ( columna==2 ){
	                    				Contact c = new Contact();
	                    				c.setNameContact("Contacto");
	                    				c.setDataContact( cellValue );
	                    				if ( customer.getContacts()==null )
	                    					customer.setContacts( new ArrayList<Contact>() );
	                    				customer.getContacts().add( c );
	                    			}
	                    			else if ( columna==4 ){
	                    				Contact c = new Contact();
	                    				c.setNameContact("Observaciones");
	                    				c.setDataContact( cellValue );
	                    				if ( customer.getContacts()==null )
	                    					customer.setContacts( new ArrayList<Contact>() );
	                    				customer.getContacts().add( c );
	                    			}
	                    		break;
                    			
	                    		case 20:
	                    			if ( columna==2 ){
	                    				try{
	                    					String meses = cellValue.substring( cellValue.lastIndexOf(":") );
	                    					address.setMonths( meses );
	                    				} catch ( Exception e ){
	                    					
	                    				}
	                    			}
                    			break;
                    			
    	                    	
	                    		case 21:
	                    			if ( columna==6 )
	                    				try{
	                    					address.setAmount( cellValue.substring(0, Math.min(cellValue.length(), 10)) );
	                    				} catch( Exception e){}
                    			break;
	                    	}
                    	}
                    }
                    columna++;
                }
            }
            file.close();

            if ( customer.getServiceAddress() == null )
            	customer.setServiceAddress( customer.getMainAddress() );
            if ( customer.getServicePobl() == null )
            	customer.setServicePobl( customer.getMainPobl() );
            if ( customer.getServiceProv() == null )
            	customer.setServiceProv( customer.getMainProv() );
            if ( customer.getServicePostalCode() == null )
            	customer.setServicePostalCode( customer.getMainPostalCode() );
            if ( customer.getCifCustomer() == null )
            	customer.setCifCustomer( customer.getCodeCustomer() );
            
            if ( address.getMainAddress() == null )
            	address.setMainAddress( customer.getMainAddress() );
            if ( address.getMainPobl() == null )
            	address.setMainPobl( customer.getMainPobl() );
            if ( address.getMainProv() == null )
            	address.setMainProv( customer.getMainProv() );
            if ( address.getMainPostalCode() == null )
            	address.setMainPostalCode( customer.getMainPostalCode() );

        customer.setAddress( new ArrayList<Address>() );
        customer.getAddress().add( address );
		return customer;
	}
	
	private String getStringCellValue(Cell cell){
		switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_NUMERIC:
	            return String.valueOf( cell.getNumericCellValue() );
	        case Cell.CELL_TYPE_STRING:
	        	return cell.getStringCellValue();
	        case Cell.CELL_TYPE_FORMULA:
	        	try{
	        		return String.valueOf( cell.getNumericCellValue() );
	        	} catch( Exception w){
	        		return null;
	        	}
		}
		return null;
	}
}
