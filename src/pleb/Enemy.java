package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

public class Enemy extends GameObject {
	private double dist;
	private long lastBullet;

	
	private ParticleManager particleMgr;

	private ArrayList<Bullet> Bullets = new ArrayList<Bullet>();
	private ArrayList<Bullet> removeBullets = new ArrayList<Bullet>();
	
	public Enemy(float x, float y, float w, float h, ObjectId id, Handler handler, Color selfColor, boolean isEntity) 
	{
		super(x, y, w, h, id, handler, selfColor, isEntity);
		dist = 0;
		lastBullet = 0;
		setHP(50);
		particleMgr = new ParticleManager();
	}

	public void update(LinkedList<GameObject> object, float dt, float time) 
	{
		
		dist = Math.sqrt(
				Math.pow((Game.getPlayerX() - getX()), 2) + 
				Math.pow((Game.getPlayerY() - getY()), 2)
		);
		if(dist <= Config.EnemyVision) {				
			if((System.currentTimeMillis() - lastBullet >= Config.bulletDelay) && (Bullets.size() < Config.BulletLimit)) {
				Bullets.add( new Bullet(
					getX()+(getWidth()/2), 
					getY()+(getHeight()/2-(getHeight()/4)), 
					getId(), handler, Game.getPlayerX(), Game.getPlayerY()
				) );
				lastBullet = System.currentTimeMillis();
			}
		}

		handleBullets((Game.getPlayerX() > getX() ? 1:0), object, dt, time);
		particleMgr.update(time);

	}

	public void render(Graphics2D g, float dt) 
	{
		
		// If the enemy gets attacked by player, 
		// a rectangle is drawn to show (this) target
		if(isTarget()) {
			g.setColor(Color.ORANGE);
			g.fillRect((int)getX()-5, (int)getY()-5, (int)getWidth()+10, (int)getHeight()+10);
		}
		
		// draw entity
		g.setColor(getSelfColor());
		g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
		
		// View health status
		showHP(g);
		
		// Draw bullets generated by (this) entity
		for(Bullet b: Bullets)
			b.render(g, dt);
		
		// Particle management
		particleMgr.render(g, dt);
		
		
	}

	private void handleBullets(int direction, LinkedList<GameObject> object, float dt, float time) {
		for(Bullet b: Bullets) {
			b.update(object, dt, time);
			if(b.collided) {
				removeBullets.add(b);
				particleMgr.genParticle("explosion", (int)b.getX(), (int)b.getY(), 30, Config.particleSpeed*1, Config.Colors.purple);
			}
			
		}
		Bullets.removeAll(removeBullets);
		removeBullets.clear();
	}
	
	public Rectangle getBounds() { return new Rectangle( (int)x, (int)y, (int)width, (int)height ); }
		
		public Rectangle getBoundsTop() 
		{
			return new Rectangle(
					(int)(x+(width*0.2f)),
					(int)y, 
					(int)(width-(width*0.45f)), 
					(int)(height/2)
			);
		}
		public Rectangle getBoundsBottom() 
		{
			return new Rectangle(
					(int)(x+(width*0.2f)),
					(int)(y+(height/2)),
					(int)(width-(width*0.45f)),
					(int)(height/2)
			);
		}
	
		public Rectangle getBoundsLeft() 
		{
			return new Rectangle(
					(int)x,
					(int)(y+height*0.15f), 
					(int)((int)width*0.15f), 
					(int)(height-(height*0.3f))
			);
		}
		
		public Rectangle getBoundsRight() 
		{
			return new Rectangle(
					(int)(x+(width-(width*0.15f))), 
					(int)(y+height*0.15f), 
					(int)((int)width*0.15f), 
					(int)(height-(height*0.3f))
			);
		}
	
		public Rectangle getBoundsPepe() 
		{
			return new Rectangle(
					(int)(x), 
					(int)(y+height*1.15f), 
					(int)(width*0.15f), 
					(int)(height-(height*0.3f))
			);
		}
}
