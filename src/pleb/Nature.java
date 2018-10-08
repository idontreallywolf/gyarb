package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

public class Nature {
	private String weatherMode;
	private ArrayList<Object> weatherTypes = new ArrayList<Object>();
	private WeatherParticle wp;

	/*
	 * @param String event — defines what will happen to the weather.
	 * 						default is "clear"
	 *  
	 */
	public Nature(String event) {
		
		wp = new WeatherParticle(Config.WINDOW_WH[0]/2, 0, 5, 5, ObjectId.FgObject, new Color(255,255,255), false, event);
		//Game.handler.addObject(wp);
		
		
	}
	
	public void update() {
		System.out.println("updating nature:"+ this.getWeatherMode());
	}
	
	public void render(Graphics2D g) {
		
	}
	
	/*
	 * @param String mode — takes a string where weather type is determined
	 *						i.e: "snow","rain","storm" 
	 * */
	public void setWeatherMode(String mode) 
	{
		this.weatherMode = mode;
	}
	
	/*
	 * @return — returns latest weather mode
	 */
	public String getWeatherMode() 
	{
		return this.weatherMode;
	}
	
	
	
	/** Private Classes **/
	private class WeatherParticle extends GameObject {
		public WeatherParticle(float x, float y, int w, int h, ObjectId id,  Color selfColor, boolean isEntity, String type) {
			super(x, y, w, h, id, Game.handler, selfColor, isEntity);
		}

		public void update(LinkedList<GameObject> object, float dt, float time) 
		{
			setX(getX()+1);
			setY(getY()+1);
		}

		public void render(Graphics2D g, float dt) 
		{
			g.setColor(getSelfColor());
			for(int i = 0; i < 10; i++) 
			{
				g.fillOval(Game.getRandInt(1, Config.WINDOW_WH[0]), Game.getRandInt(1, Config.WINDOW_WH[1]), (int)getWidth(), (int)getHeight());
			}
			
		}

		@Override
		public Rectangle getBounds() 		{ return null; }
		public Rectangle getBoundsTop() 	{ return null; }
		public Rectangle getBoundsBottom() 	{ return null; }
		public Rectangle getBoundsLeft() 	{ return null; }
		public Rectangle getBoundsRight() 	{ return null; }
	}
	
}
