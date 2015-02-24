package com.alcedomoreno.sirme.business.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alcedomoreno.sirme.core.util.MyLogger;

public class FileUtil {

	private static Logger log = LoggerFactory.getLogger( FileUtil.class );
	private static final String CLASS_NAME = "FileUtil";
	
	///////////////////////////////////////////////////////////////
	//                         Constantes                        //
	///////////////////////////////////////////////////////////////
	
    ///////////////////////////////////////////////////////////////
	//                     Fin de Constantes                     //
	///////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////
	//                         Singleton                         //
	///////////////////////////////////////////////////////////////
	
	private static FileUtil instance;
	
	private FileUtil(){}
	
	public static synchronized FileUtil getInstance(){
		if ( instance==null )
			instance = new FileUtil();
		return instance;
	}
	///////////////////////////////////////////////////////////////
	//                    Fin del Singleton                      //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                          Metodos                          //
	///////////////////////////////////////////////////////////////

	public boolean isAccesible( String path ){
		boolean success = false;
		
		File file = new File( path );
		if ( file!=null  ){
			success = file.exists();
			log.info( success ?path +" existe": path + " no existe");
			success = file.canRead();
			log.info( success ?path +" se puede leer": path + " no se puede leer");
			success = file.canWrite();
			log.info( success ?path +" se puede escribir": path + " no se puede escribir");
		}
		
		return success;
	}
	
	public boolean createDirectoryIfDontExists( String path ){
		boolean success = false;
		File file = new File( path );

		if ( file!=null && file.exists()){
			success = file.isDirectory();
			log.info( success ?path +" es un directorio": path + " es un fichero");
		} else{
			success = (new File(path)).mkdirs();
			log.info( success ?path +" creado con �xito": path + " no ha podido ser creado");
		}

		return success;
	}
	
	public String getCorrectCompletePath( String path, String fileName ){

		String returned = path;
		
		if ( path.trim().endsWith("/") )
			returned += fileName;
		else
			returned += "/" + fileName;
			
		return returned;
	}
	
	public String getCorrectFileName( String prefix, String fileName, String suffix, String fileExtension ){
		StringBuilder sb = new StringBuilder();

		if ( prefix!=null )
			sb.append( prefix.trim() );

		if ( fileName!=null )
			sb.append( fileName.trim() );

		if ( suffix!=null )
			sb.append( suffix.trim() );

		if ( !sb.toString().endsWith(".") && fileExtension!=null && !fileExtension.startsWith("."))
			sb.append(".");

		if ( fileExtension!=null )
			sb.append( fileExtension.trim() );

		return sb.toString();
	}

	public boolean saveStringInFile( String content, String path, String fileName ){

		boolean success = false;
		File file;
		BufferedWriter fbw = null;

		try {
			file = new File( getCorrectCompletePath(path,fileName) );
			// Creamos el fichero si no existe
			if (!file.exists())
				file.createNewFile();

			OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream(file, true), "UTF-8");

			fbw = new BufferedWriter(writer);

			fbw.write( content );
            fbw.newLine();

			log.info("Done");
			success = true;
 
		} catch (IOException e) {
			log.error( e.getMessage() );
		} finally {
			try {
				if (fbw != null)
					fbw.close();
			} catch (Exception e) {
				log.error( e.getMessage() );
			}
		}

		return success;
	}
	
	public String getContentFromFile(String path, String fileName ) {
		 
		File file = new File( getCorrectCompletePath(path,fileName) );
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
 
		try {
			in = new BufferedReader( new InputStreamReader( new FileInputStream(file), "UTF8") );
	 
			String str;
			while ( (str = in.readLine()) != null )
				sb.append( str )/*.append( System.getProperty("line.separator") )*/;

		} catch (IOException e) {
			log.error( e.getMessage() );
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ex) {
				log.error( ex.getMessage() );
			}
		}

		return new String( sb.toString().getBytes() );
	}
	
	public String getContext(String path){
		return new File( path ).getParentFile().getPath();
	}
	
	public boolean renameFile(String path, String oldFileName, String newFileName){
		File oldFile = new File( getCorrectCompletePath(path,oldFileName) );
		File newFile = new File( getCorrectCompletePath(path,newFileName) );

		return oldFile.renameTo( newFile );
	}
	
	public boolean removeFile(String path, String fileName){
		File file = new File( getCorrectCompletePath(path,fileName) );
		return file.delete();
	}
	
	public boolean removeFile(String fileWithPath){
		File f = new File( fileWithPath );
		return f.delete();
	}
	
	@SuppressWarnings("finally")
	public boolean copyfile(String srFile, String dtFile) {

		boolean exit = false;
		InputStream in = null;
		OutputStream out = null;

		try {
			File f1 = new File( srFile );
			File f2 = new File( dtFile );
			in = new FileInputStream( f1 );
			out = new FileOutputStream( f2 );

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);

			exit = true;

		} catch (FileNotFoundException ex) {
			log.error( ex.getMessage()+ " in the specified directory." );
		} catch (IOException e) {
			log.error( e.getMessage() );
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				log.error( e.getMessage() );
			}
			return exit;
		}
	}

	public List<String> getFirstLevelFiles(String path){
		List<String> files = new ArrayList<String>();

		File directory = new File( path );
		File[] listOfFiles = directory.listFiles(); 
		if ( listOfFiles!=null )
			for ( File f:listOfFiles )
				if ( !f.isDirectory() )
					files.add( f.getAbsolutePath() );

		return files;
	}
	
	public boolean saveFile( InputStream inputStream, String path ) throws Exception{
		boolean returned = true;

		OutputStream outputStream = null;
		try {
			MyLogger.info(log, CLASS_NAME, "saveFile", "IN", "Path", path);
			outputStream = new FileOutputStream( new File( path ) );
	 
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			MyLogger.info(log, CLASS_NAME, "saveFile", "OK");
		} catch (IllegalStateException | IOException e) {
			returned = false;
			MyLogger.error(log, CLASS_NAME, "saveFile", e.getMessage());
			throw new Exception( e.getMessage() );
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		MyLogger.info(log, CLASS_NAME, "saveFile", "OUT", "Path", path);
		return returned;
	}

	///////////////////////////////////////////////////////////////
	//                      Fin de Metodos                       //
	///////////////////////////////////////////////////////////////
}
