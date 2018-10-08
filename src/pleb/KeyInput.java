package pleb;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	Handler handler;
	
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i< handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectId.Player) {
				System.out.println("Some key pressed");
				if(key == KeyEvent.VK_A) tempObject.setVelX(-5);
				else if(key == KeyEvent.VK_D) tempObject.setVelX(5);
				
				if(key == KeyEvent.VK_W && !tempObject.isJumping()) {
					System.out.println("jumped X: "+tempObject.getX());
					tempObject.setJumping(true);
					tempObject.setVelY(-10);
				}
				
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE) { System.exit(1); }
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i< handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectId.Player) {
				if(key == KeyEvent.VK_A || key == KeyEvent.VK_D)
					tempObject.setVelX(tempObject.getVelX() - 0.3f);
				
				//if(key == KeyEvent.VK_D) tempObject.setVelX(0);
				
			}
		}
	}
}
