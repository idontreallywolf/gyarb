package pleb;

import java.awt.Color;
import java.awt.GradientPaint;

public class Config {
	
	/*
	 
	 	TODO: Organize variables
	 	TODO: Remove unnecessary variables
	 
	 */
	public static class Colors {
		/* GameObject Colors */
		public static Color coin 			= new Color(255, 211, 42, 128);
		public static Color walkable 		= new Color(5, 196, 107, 128);
		public static Color walkable_trsp 	= new Color(255,255,255,128);
		public static Color obstacle 		= new Color(245, 59, 87, 128);
		public static Color support 		= new Color(0, 216, 214, 128);
		public static Color invalid			= new Color(255, 0, 0, 128);
		public static Color player 			= new Color(30, 39, 46, 128);
		public static Color enemy 			= new Color(128, 142, 155, 128);
		public static Color star			= new Color(255, 255, 255, 128);
		
		/* General Colors */
		public static Color borderColor		= new Color(255, 255, 255);
		public static Color purple 			= new Color(255, 0, 255, 128);
		public static Color yellow			= new Color(255, 255, 0, 128);
		public static Color light_blue		= new Color(0, 255, 255, 128);
	}
	
	/*
	 * 		Configurations for Entities
	 */
	public static class Entity 
	{

		/*
		 *	Common variables for all Entity objects 
		 */
		public static int jump_power = 10;
		
		/*
		 * Configurations for player 
		 */
		public static class Player 
		{
			public static int health = 150;
		}
		
		/*
		 * Configurations for Enemies 
		 */
		public static class Enemy 
		{
			public static int health = 50;
			public static int vision = 350;
		}
	}
	
	/*
	 * Configurations for Particles and Effects 
	 */
	public static class Particle {
		public static float lifeTime = 0.3f;
		public static int size = 5;
		public static int speed = 8;
	}
	
	/*
	 * Configurations for Bullet objects 
	 */
	public static class Bullet {
		public static int destroyDist = 500;
		public static int speed = 16;
		public static int limit = 3;
		public static int delay = 1000;
		public static int size = 10;
	}
	
	
	public static class General {
		public static double timeScale			= 1.0;
		public static double FPS 				= 1.0/60.0;
		public static double UPS				= 1.0/40.0;
		public static int[] WINDOW_WH 			= {810, 510};
		public static float gravity 			= 1.5f;
		public static int tilesize 				= 32;
		public static GradientPaint skyBackground = new GradientPaint(0, 0, new Color(33,39,68), 0, WINDOW_WH[1], new Color(82, 56, 73));
		public static int lvlWidth;
		
		public static int AmountOfStars 		= 30;
		public static int coinAmount 			= 0;
		public static int[] obstacle = {19};
		public static String tilesetPath = "/Tileset_new.png";
	}

}
