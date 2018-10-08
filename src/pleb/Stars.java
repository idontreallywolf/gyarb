package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.math.*;
import java.util.ArrayList;
import java.util.List;

public class Stars {
	
	private int amount;
	private List<Star> stars;

	public Stars(int amount) {
		this.amount = amount;
		stars = new ArrayList<Star>();
		
		for(int i = 0; i < amount; i++) {
			stars.add(new Star(Math.random() * Config.WINDOW_WH[0], Math.random() * Config.WINDOW_WH[1], 5, 5));
		}
	}
	
	public void update(float time) {
	    for(Star s: this.stars) {
	    	s.update(time);
	    }
	    
	}
	
	public void render(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for(Star s: this.stars) {
			for(Star t: this.stars) {
				double dist = Math.sqrt(
						Math.pow((s.x - t.x), 2) + 
						Math.pow((s.y - t.y), 2)
				);
				if(dist <= 200) {
					int alpha = (int) (((Config.maxStarDist - dist) / Config.maxStarDist) * 255);
					g.setColor(new Color(255,255,255, Math.abs(alpha)));
					g.drawLine((int)(s.x+(s.w*0.5)), (int)(s.y+(s.h*0.5)), (int)(t.x+(t.w*0.5)), (int)(t.y+(t.h*0.5)));
				}
				
				
			}
		}
		
		for(Star s: this.stars) {
	    	s.draw(g);
	    }
	}
	
	private class Star {
		private int w, h;
		double x, y, vx, vy;

		
		public Star(double x, double y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.vx = Math.floor(Math.random() * 50) - 25;
			this.vy = Math.floor(Math.random() * 50) - 25;
		}

		public void update(float time) {
			this.x += this.vx * time;
			this.y += this.vy * time;
			
			if(x < 0 || x > Config.WINDOW_WH[0]) this.vx = -this.vx;
			if(y < 0 || y > Config.WINDOW_WH[1]) this.vy = -this.vy;
		}
		
		public void draw(Graphics2D g) {
			g.setColor(Config.Colors.star);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.fillOval((int)this.x, (int)this.y, this.w, this.h);
		}
	}
	
}
