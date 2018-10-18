package pleb;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;

public class Handler {
	// public static LinkedList<GameObject> object = new LinkedList<GameObject>();
	// public static LinkedList<GameObject> entities = new LinkedList<GameObject>();
	private static GameObject tempObject;

	/**
		@desc takes care of updating/rendering/destruction of Game- & GameFrame objects.
	**/
	public Handler() {

	}

	/**
		@param keyDownMap 	- {keys, values} of keyboard keys being pressed
		@param dt 		- deltaTime
	**/
	public void update(HashMap<Integer, Boolean> keyDownMap, float dt, float time) {

		for(int i = 0;i < Game.objects.size();i++) {
			tempObject = Game.objects.get(i);

			if(tempObject.isEntity() && tempObject.getId() == ObjectId.Enemy && tempObject.getCurrentHealth() <= 0) {
				Game.objects.remove(tempObject);
			}

			if(tempObject.getId() == ObjectId.Player) {
				// Do not UPDATE player when game is PAUSED
				if(!Game.pauseGame) {
					tempObject.isOnScreen = true;
					tempObject.update(Game.objects, dt, time);
				}
			} else {
				if((tempObject.getX() > -Game.cam.getX() -Config.General.tilesize && tempObject.getX() < -Game.cam.getX() + Config.General.WINDOW_WH[0]) &&
				(tempObject.getY() > -Game.cam.getY() -Config.General.tilesize && tempObject.getY() < -Game.cam.getY() + Config.General.WINDOW_WH[1])) {
					tempObject.isOnScreen = true;
					tempObject.update(Game.objects, dt, time);
				} else {
					tempObject.isOnScreen = false;
					tempObject.update(Game.objects, dt, time);
				}

			}
		}
	}

	public void render(Graphics2D g, float dt) {

		for(int i = 0;i < Game.objects.size();i++) {
			tempObject = Game.objects.get(i);
			if((tempObject.getX() > -Game.cam.getX() -Config.General.tilesize && tempObject.getX() < -Game.cam.getX() + Config.General.WINDOW_WH[0]) &&
				(tempObject.getY() > -Game.cam.getY() -Config.General.tilesize && tempObject.getY() < -Game.cam.getY() + Config.General.WINDOW_WH[1])) {

				if(tempObject.getId() == ObjectId.Player) {
					// Do not RENDER player when game is PAUSED
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
		Game.objects.add(object);
	}
	public void removeObject(GameObject object) {
		Game.objects.remove(object);
	}

	public void createLevel() {

	}
}
