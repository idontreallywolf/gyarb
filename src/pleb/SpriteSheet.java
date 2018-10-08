package pleb;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
		//System.out.println(image.getWidth()+", "+image.getHeight());
	}
	
	
	public BufferedImage grabImage(int col, int row, int width, int height) {
		BufferedImage alpha = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// 2, 14, 32, 32
		/*
		 * 
		 791, 461
		 
		 2*32 = 64 - 32 = 	32
		 14*32 = 			416
		 
		 
		 * */
		//System.out.println(((col * width) - width)+", "+((row * height) - height)+", "+width+", "+height);
		BufferedImage img = image.getSubimage(
				(col * width) - width, 
				(row * height) - height, 
				width, 
				height);
		
		
		int w = img.getWidth();
		int h = img.getHeight();

		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				int pixel = img.getRGB(x, y);
				
				int[] rgb = new int[3]; 


				Color pixelcolor = new Color(img.getRGB(x, y));
				rgb[0] = pixelcolor.getRed();
				rgb[1] = pixelcolor.getGreen();
				rgb[2] = pixelcolor.getBlue();
				
				
				if(rgb[0] == 227 && rgb[1] == 64 && rgb[2] == 57) {
					alpha.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());			
				} else {
					alpha.setRGB(x, y, pixel);	
				}
			}	
		}
		
		return alpha;
	}

	
}
