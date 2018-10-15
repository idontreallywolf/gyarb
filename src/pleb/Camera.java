package pleb;

public class Camera {
	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y; 
	}
	
	
	public void update(GameObject player) {
		int targetX = (int) (-player.getX() + Config.General.WINDOW_WH[0] / 2 + 16);
		int targetY = (int) (-player.getY() + Config.General.WINDOW_WH[1] / 2 + 64);
		x += (targetX - x) * 0.1;
		y += (targetY - y) * 0.1;
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	public void setX(float x) { this.x =  x; }
	public void setY(float y) { this.y = y; }
}	
