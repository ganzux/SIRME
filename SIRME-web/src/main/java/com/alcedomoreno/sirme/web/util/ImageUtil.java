package com.alcedomoreno.sirme.web.util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	///////////////////////////////////////////////////////////////
	//                         Singleton                         //
	///////////////////////////////////////////////////////////////
	
	private static ImageUtil instance;

	private ImageUtil() {
	}

	public static synchronized ImageUtil getInstance() {
		if (instance == null)
			instance = new ImageUtil();

		return instance;
	}
	///////////////////////////////////////////////////////////////
	//                    Fin del Singleton                      //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                          Metodos                          //
	///////////////////////////////////////////////////////////////


	public boolean rotate90Right(String iPath, String oPath, String fileType) throws FileNotFoundException, IOException {

		BufferedImage oldImage = ImageIO.read( new FileInputStream(iPath) );

//		int oldWidth = oldImage.getWidth();
//	    int oldHeight = oldImage.getHeight();
//
//		BufferedImage newImage = new BufferedImage( oldWidth, oldHeight, oldImage.getType());
//		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
//
//		graphics.rotate(Math.toRadians(90), newImage.getWidth() / 2, newImage.getHeight() / 2);
//		graphics.translate((newImage.getWidth() - oldWidth) / 2,	(newImage.getHeight() - oldHeight) / 2);
//		graphics.drawImage(oldImage, 0, 0, oldWidth, oldHeight, null);

		
		ImageIO.write( rotateMyImage(oldImage, 90) , fileType, new FileOutputStream(oPath));

		return true;
	}
	
	public static BufferedImage rotateMyImage(BufferedImage img, double angle) {
		int         width  = img.getWidth();  
        int         height = img.getHeight();  
        BufferedImage   newImage = new BufferedImage( height, width, img.getType() );  
  
        for( int i=0 ; i < width ; i++ )  
            for( int j=0 ; j < height ; j++ )  
                newImage.setRGB( height-1-j, i, img.getRGB(i,j) );  
  
        return newImage;  
	}
	///////////////////////////////////////////////////////////////
	//                      Fin de Metodos                       //
	///////////////////////////////////////////////////////////////

}
