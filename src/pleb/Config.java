package pleb;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
	
	/*
	 
	 	TODO: Organize variables
	 	TODO: Remove unnecessary variables
	 
	 */
	public static class GameColors {
		public static Color walkable 		= new Color(255,255,255,128);

	}
	
	public static class Colors {
		/* GameObject Colors */
		public static Color coin 			= new Color(255, 211, 42, 128);
		public static Color walkable 		= new Color(5, 196, 107, 128);
		public static Color obstacle 		= new Color(245, 59, 87, 128);
		public static Color support 		= new Color(0, 216, 214, 128);
		public static Color invalid			= new Color(255, 0, 0, 128);
		public static Color player 			= new Color(30, 39, 46, 128); //new Color(12, 219, 243);
		public static Color enemy 			= new Color(128, 142, 155, 128);
		public static Color star			= new Color(255, 255, 255, 128); //new Color(149, 84, 0);
		
		/* General Colors */
		public static Color borderColor		= new Color(255, 255, 255);
		public static Color purple 			= new Color(255, 0, 255, 128);
		public static Color yellow			= new Color(255, 255, 0, 128);
		public static Color light_blue		= new Color(0, 255, 255, 128);
	}
	
	public static int AmountOfStars 		= 30;
	public static int maxStarDist 			= 150;
	public static int coinAmount 			= 0;
	
	public static double timeScale			= 1.0;
	public static double FPS 				= 1.0/60.0;
	public static double UPS				= 1.0/40.0;
	public static int[] WINDOW_WH 			= {810, 510};
	

	public static Color panelGameColor 		= new Color(29, 209, 161);
	
	public static int playerHealth 			= 150;
	
	public static int enemyHealth 			= 50;
	public static int EnemyVision 			= 350;
	
	public static int bulletDestroyDist 	= 500;
	public static int BulletSpeed 			= 16;
	public static int BulletLimit 			= 3;
	public static int bulletDelay 			= 1000;
	public static int bulletSize 			= 10;
	
	public static float gravity 			= 1.5f;
	public static int player_acc 			= 1;
	public static double player_friction 	= -0.12;
	public static int player_jump_power 	= 10;
	public static int tilesize 				= 32;
	public static int[] obstacle 			= {19};
	
	public static float particleLife 		= 0.3f; //800000000;
	public static int particleSize 			= 5;
	public static int particleSpeed 		= 8;
	
	//public static Color skyBackground 		= new Color(140, 188, 216);//new Color(35,35,35);
	public static GradientPaint skyBackground = new GradientPaint(0, 0, new Color(33,39,68), 0, Config.WINDOW_WH[1], new Color(82, 56, 73));

	public static int lvlToLoad 			= 6;
	public static String tilesetPath 		= "/Tileset_new.png";
	public static int lvlWidth;

	public static int[][] gameLevel 		= parseLevel("/levels/level_"+lvlToLoad+".txt");
	
	public static int[][] parseLevel(String path) {
		byte[] encoded;
		try {
			
			Instance instance = new Instance();
			String resPath = instance.resPath(path);
			encoded = Files.readAllBytes(Paths.get(resPath));
			String org = new String(encoded, "utf-8");
			
		    org = org.replace("[","");
	        org = org.substring(0, org.length()-2);
	        String s1[] = org.split("],");
	        String s2 = s1[0].trim();
	        String s3[] = s2.split(",");

	        int[][] result = new int[s1.length][s3.length];

	        for (int i = 0; i < s1.length; i++) {
	            s1[i] = s1[i].trim();
	            String singleInt[] = s1[i].split(",");

	            for (int j = 0; j < singleInt.length; j++) {
	                result[i][j] = Integer.parseInt(singleInt[j]);
	            }
	        }
	        return result;
	        
		} catch (IOException e) {
			e.printStackTrace();
			return new int[0][0];
		}
		
	}
}
