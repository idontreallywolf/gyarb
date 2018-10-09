package pleb;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;

public class Handler {
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	public static LinkedList<GameObject> entities = new LinkedList<GameObject>();
	private GameObject tempObject;

	public void update(HashMap<Integer, Boolean> keyDownMap, float dt, float time) {
			
		for(int i = 0;i < object.size();i++) {
			tempObject = object.get(i);
			
			if(tempObject.isEntity() && tempObject.getId() == ObjectId.Enemy && tempObject.getCurrentHealth() <= 0) {
				object.remove(tempObject);
			}
			
			// Do not UPDATE player when game is PAUSED
			if(tempObject.getId() == ObjectId.Player) {
				if(!Game.pauseGame) {
					tempObject.update(object, dt, time);
				}
			} else {
				tempObject.update(object, dt, time);
			}
		}
	}
	
	public void render(Graphics2D g, float dt) {
	
		for(int i = 0;i < object.size();i++) {
			tempObject = object.get(i);
			if((tempObject.getX() > -Game.cam.getX() -Config.tilesize && tempObject.getX() < -Game.cam.getX() + Config.WINDOW_WH[0]) &&
				(tempObject.getY() > -Game.cam.getY() -Config.tilesize && tempObject.getY() < -Game.cam.getY() + Config.WINDOW_WH[1])) {
				// Do not RENDER player when game is PAUSED
				if(tempObject.getId() == ObjectId.Player) {
					if(!Game.pauseGame) {
						tempObject.render(g, dt);
					}
				} else {
					tempObject.render(g, dt);
				}	
			}
		}
		
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
	public void createLevel() {
		
	}
}

