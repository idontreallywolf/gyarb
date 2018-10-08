package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.ListIterator;

public class ParticleManager {

	
	private ArrayList<Particle> particles;
	private ArrayList<Particle> removeParticles;
	
	public ParticleManager() {
		particles = new ArrayList<Particle>();
		removeParticles = new ArrayList<Particle>();
	}
	

	public int getListSize() {
		return particles.size();
	}
	
	/*
	 		Generate particles
	 */
	public void genParticle(String type,int x, int y, int amount, int speed, Color c) {
		for(int i = 0; i < amount; i++) {
			particles.add(new Particle(type , x,y, i, speed, c));
		}
	}
	
	public void update(float time) {

	     // Iterate trough particle objects
		// update them & check for lifeTime
		for(Particle p: particles) {
			
			// Updating particle object before 
			// checking for time lapse
			p.update(time);
			
			// Append outdated particles to removeParticles
			// if time limit has passed
			if(p.timePassed >= Config.particleLife) {
				removeParticles.add(p);
			}
		}
		
		// finally, delete all "remove-marked" objects
		particles.removeAll(removeParticles);
		
		clearTrash();
	}
	
	private void clearTrash() {
		ListIterator litr = removeParticles.listIterator();
	      
	      while(litr.hasNext()) {
	         Particle particle = (Particle) litr.next();
	         particle = null;
	         litr.remove();
	      }
	}
	
	public void render(Graphics2D g, float dt) {
		for(Particle p: particles) {
			p.render(g, dt);
		}
	}
	
}

class Particle {
	
	private double px, py, x, y; 
	private int radius, angle;
	public float timePassed;
	private Color selfColor;
	String type;
	
	public Particle(String type, double x, double y, int angle, int radius, Color pColor) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.angle = angle;	
		this.timePassed = 0;
		this.type = type; // explosion, tail
		this.selfColor = pColor;

	}
	
	public void update(float time) {
		this.timePassed += time;
	}
	
	public void render(Graphics2D g, float dt) {
		px  =	x + radius * Math.cos(angle);
		py  =	y + radius * Math.sin(angle);
		radius += 2 * dt;
		
		g.setColor(this.selfColor);
		
		g.fillOval(
				(this.type == "tail" ? (int)(px+Config.tilesize/2):(int)px), 
				(this.type == "tail" ? (int)(py+Config.tilesize/2):(int)py), 5, 5); 
	}
}
