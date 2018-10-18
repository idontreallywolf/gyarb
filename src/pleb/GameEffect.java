package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

public abstract class GameEffect {

	protected ObjectId id;
	protected float width, height, x, y;
	protected float velX = 0, velY = 0;
	protected Color selfColor;
	protected int layer;

	/**
		@param x 		- x Position of object
		@param y 		- y Position of object
		@param w 		- width of object
		@param h 		- height of object
		@param id 	- ObjectId._ObjName_
		@param c 		- Object color
		@param layer 	- which "layer" to be displayed on
	**/
	public GameEffect(float x, float y, float w, float h, ObjectId id, Color c, int layer) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.selfColor = c;
		this.layer = layer;
	}

	public abstract void update(/*LinkedList<GameObject> object, */float time, int loz);
	public abstract void render(/*LinkedList<GameObject> object, */Graphics2D g, float dt);

	// getters
	public ObjectId getId() 		{ return this.id; 		}
	public float getX()			{ return this.x; 		}
	public float getY()			{ return this.y; 		}
	public float getWidth() 		{ return this.width;	}
	public float getHeight() 	{ return this.height; 	}
	public Color getSelfColor() 	{ return this.selfColor; }
	public int getLayer() 		{ return this.layer; 	}
}
