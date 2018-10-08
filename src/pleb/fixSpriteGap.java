package pleb;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class fixSpriteGap {

	public static BufferedImageLoader loader = new BufferedImageLoader();
	public static BufferedImage spriteSheet;
	
	public static void main(String[] args) {
		spriteSheet = loader.loadImage("/Tileset.png");
		processImage();
	}
		
	public static boolean isTransparent( int x, int y ) {
		int pixel = spriteSheet.getRGB(x,y);
		if( (pixel>>24) == 0x00 ) {
			return true;
		}
		return false;
	}
	
	public static void moveNextRowUp(int currentY) {
		for(int x = 0; x < spriteSheet.getWidth(); x++) {
			spriteSheet.setRGB(x, currentY, spriteSheet.getRGB(x, currentY+1));
			spriteSheet.setRGB(x, currentY+1, new Color(0,0,0, 0.0f).getRGB());
		}
	}
	
	public static void processImage() {
		int w = spriteSheet.getWidth();
		int h = spriteSheet.getHeight();
		boolean rowIsAlpha = true;
		
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++)
				if(!isTransparent(x, y))
					rowIsAlpha = false;
			
			if(rowIsAlpha) moveNextRowUp(y);
			
			
		}
		
		try { ImageIO.write(spriteSheet, "png", new File("test2.png")); } 
		catch (IOException e) { e.printStackTrace(); }
	}

	

}
