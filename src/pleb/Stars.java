package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.math.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Stars {
	
	private int amount;
	private List<Star> starLayer1, starLayer2, starLayer3;

	public Stars(int amount) {
		this.amount = amount;
		starLayer1 = new ArrayList<Star>();
		starLayer2 = new ArrayList<Star>();
		starLayer3 = new ArrayList<Star>();
		
		for(int i = 0; i < amount; i++) {
			starLayer1.add(new Star(Math.random() * Config.General.WINDOW_WH[0], Math.random() * Config.General.WINDOW_WH[1], 5, 5, Color.WHITE, 3));
			starLayer2.add(new Star(Math.random() * Config.General.WINDOW_WH[0], Math.random() * Config.General.WINDOW_WH[1], 5, 5, new Color(237, 237, 237), 4));
			starLayer3.add(new Star(Math.random() * Config.General.WINDOW_WH[0], Math.random() * Config.General.WINDOW_WH[1], 5, 5, new Color(198, 198, 198), 6));
		}
	}
	
	public void update(float time) {
		for(int i = 0;i < starLayer1.size();i++) {
			starLayer1.get(i).update(time, 2);
			starLayer2.get(i).update(time, 4);
			starLayer3.get(i).update(time, 6);
		}
	    // for(Star s: this.stars) {
	    //	 s.update(time);
	    // }
	    
	}
	
	public void render(Graphics2D g, float time) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/*for(Star s: this.stars) {
			for(Star t: this.stars) {
				double dist = Math.sqrt(
						Math.pow((s.x - t.x), 2) + 
						Math.pow((s.y - t.y), 2)
				);
				
				//bind line color
				if(dist <= 200) {
					int alpha = (int) (((Config.maxStarDist - dist) / Config.maxStarDist) * 255);
					g.setColor(new Color(255,255,255, Math.abs(alpha)));
					g.drawLine((int)(s.x+(s.w*0.5)), (int)(s.y+(s.h*0.5)), (int)(t.x+(t.w*0.5)), (int)(t.y+(t.h*0.5)));
				}
				
				
			}
		}*/
		
		
		for(Star s: this.starLayer3) {
	    	s.render(g, time);
	    }
		for(Star s: this.starLayer2) {
	    	s.render(g, time);
	    }
		for(Star s: this.starLayer1) {
	    	s.render(g, time);
	    }
	
		
	}
	
	private class Star extends GameEffect {
		private int w, h;
		double x, originX, y, originY, vx, vy;

		
		public Star(double x, double y, int w, int h, Color selfColor, int layer) {
			super((float)x, (float)y, w, h, ObjectId.Effect, selfColor, layer);
			this.x = originX = x;
			this.y = originY = y;
			this.w = w;
			this.h = h;
			this.vx = -Game.player.getVelX();
			this.vy = -Game.player.getVelY();
		}

		public void update(float time, int loz) {
			this.vx = -Game.player.getVelX()/getLayer();
			this.vy = -Game.player.getVelY()/getLayer();
			
			if(Game.getDistance(x, y, originX, originY) < 100) {
				this.x += this.vx;
				this.y += this.vy;
			}
	
			this.x = Game.lerp((float)x, (float)originX, 0.05f);
			this.y = Game.lerp((float)y, (float)originY, 0.05f);
		}


		public void render(Graphics2D g, float dt) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(getSelfColor());
			g.fillOval((int)this.x, (int)this.y, this.w, this.h);
		}


	}
	
}
