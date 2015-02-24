package com.alcedomoreno.sirme.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		InputStream in = null;
		BufferedOutputStream output = null;
		BufferedInputStream input = null;

		try {
			String path = request.getPathInfo().substring(1);
			path = path.replaceAll("\\+", " ");
			Path p = Paths.get( path );
			in = new FileInputStream( path );
			String type = Files.probeContentType( p );
			
			response.setHeader("Content-Type", getServletContext().getMimeType( path ));
			//response.setHeader("Content-Length", in.read());
			response.setHeader("Content-Disposition", "inline; filename=\"" + "FirextImage" + "\"");

		    input = new BufferedInputStream( in );
		    output = new BufferedOutputStream(response.getOutputStream());
		    byte[] buffer = new byte[8192];
		    int length;
		    while ((length = input.read(buffer)) > 0) {
		        output.write(buffer, 0, length);
		    }
		} finally {
		    if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		    if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
		}
    }
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException	{
		doGet(request, response);
	}
}