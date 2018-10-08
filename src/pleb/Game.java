package pleb;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;



public class Game extends Canvas implements Runnable, KeyListener, MouseMotionListener {

	private Thread thread;
	private static final long serialVersionUID = 1L;
	private boolean running = false;
	private static Random rand = new Random();
	
	// TODO: move to NATURE class ASAP!
	private static Stars stars;

	public static Point mousePos;
	public static GameFrame gameFrame;
	
	/*
	 *	when specific keys are pressed, their key_codes are stored in a hashMap 
	 */
	public static HashMap<Integer,Boolean> keyDownMap = new HashMap<Integer, Boolean>();
	
	/*
	 *	Player object is seperately saves inside a static variable for easier(, global) access 
	 */
	public static Player player;
	
	/*
	 * These variables are used to determine the status of the program
	 * killGame would be QUITTING, and pausegame, would obviously be pausing the game 
	 * */
	public static boolean killGame, pauseGame;
	
	/*
	 *
	 * Game fonts
	 *
	 */
	public static Font txtFont;
	
	/*
	 * time is used to calculate FPS of the game using dt
	 * 
	 * */
	public static long time;
	

	/*
	 * 	Loads images
	 * */
	static BufferedImageLoader loader;
	
	/*
	 *  NOT IN USE !!!!!
	 * */
	static Texture tex;

	/*
	 * 
	 *  Handler object calls both update 
	 *  and render method of GameObject(s)
	 * 
	 */
	public static Handler handler;
	
	/*
	 *	Camera object translates any GameObject 
	 *	inversely relative to the position of player
	 *	if player goes RIGHT, all other game objects go LEFT  
	 */
	public static Camera cam;

	
	/*
	 * 	Nature class takes care of natural effects,
	 *  such as Rain, Snow, Storm, Daylight  
	 */
	Nature nature;

	/*
	 *	Main class method 
	 */
	public static void main(String[] args)
	{
		gameFrame = new GameFrame(Config.WINDOW_WH[0], Config.WINDOW_WH[1], "Pleb Title", new Game());
	}


	/*
	 * @param deltaTime 
	 * @param time
	 * 
	 * */
	private void update(float deltaTime, float time)
	{
		handler.update(keyDownMap, deltaTime, time);
		gameFrame.update();
		
		for(int i = 0; i < handler.object.size(); i++)
		{
			if(handler.object.get(i).getId() == ObjectId.Player)
			{
				cam.update(handler.object.get(i));
			}
		}
		
		stars.update(time);

	}

	private void render(float deltaTime, float time)
	{
		BufferStrategy bs = this.getBufferStrategy();

		// to initialize buffer once
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(Config.skyBackground);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        stars.render(g);
        
		////////////////////////////////////////////////
		g.translate(cam.getX(), cam.getY()); // cam begin
		handler.render(g, deltaTime);
		g.translate(-cam.getX(), -cam.getY()); // cam end

		////////////////////////////////////////////////
		//showPlayerHealth(g);
		drawHud(g);

		g.dispose();
		bs.show();

	}
	
	public static void drawHud(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(45, 45, Config.tilesize+10, Config.tilesize+10);
		g.setColor(Config.Colors.coin);
		g.fillRect(50, 50, Config.tilesize, Config.tilesize);
		
		g.setColor(Color.WHITE);
		g.setFont(txtFont);
	
		FontMetrics metrics = g.getFontMetrics(txtFont);
	    int x = 50 + (Config.tilesize - metrics.stringWidth(Integer.toString(player.getCoinsCollected()))) / 2;
	    int y = 50 + ((Config.tilesize - metrics.getHeight()) / 2) + metrics.getAscent();
		
		g.drawString(Integer.toString(player.getCoinsCollected()), x, y);
	}


	public static BufferedImage loadImage(String path)
	{
		BufferedImage image = loader.loadImage(path);
		return image;
	}

	public synchronized void start()
	{
		if(running) return;

		running = true;
		thread = new Thread(this);
		thread.start();

	}

	private void init()
	{
		killGame = pauseGame = false;
		tex = new Texture();

		handler = new Handler();
		nature = new Nature("rain");
		//readLevel();
		loadImgLevel();

		cam = new Camera(0, 0);

		mousePos = new Point();

		this.addKeyListener(this);

		// Get mouse position in real time
		this.addMouseMotionListener(new MouseMotionAdapter() {
	      public void mouseMoved(MouseEvent mouse)
	      {
	        mousePos.setLocation(mouse.getX(), mouse.getY());
	      }
	    });
		
		txtFont = new Font(Font.MONOSPACED, Font.BOLD, 20);
		
		time = System.nanoTime();
		
		
		stars = new Stars(Config.AmountOfStars);

	}


	private boolean compareColors(Color a, Color b) {
		boolean isSame = true;
		
		int[] a_RGB = new int[3];
		int[] b_RGB = new int[3];
		
		a_RGB[0] = a.getRed();
		a_RGB[1] = a.getGreen();
		a_RGB[2] = a.getBlue();
		
		b_RGB[0] = b.getRed();
		b_RGB[1] = b.getGreen();
		b_RGB[2] = b.getBlue();
		
		for(int i = 0;i < 3; i++) {
			if(a_RGB[i] != b_RGB[i]) {
				isSame = false;
				break;
			}
			
		}
		
		return isSame;
	}
	
	private void loadImgLevel()
	{

		BufferedImageLoader load = new BufferedImageLoader();
		BufferedImage img = load.loadImage("/level_img_2.png");

		int w = img.getWidth();
		int h = img.getHeight();

		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {

				Color pixelcolor = new Color(img.getRGB(x, y));


				ObjectId objId =  ObjectId.Block;

				if(compareColors(pixelcolor, Config.Colors.walkable) || compareColors(pixelcolor, Config.Colors.invalid))
				{
					objId = ObjectId.Block;
				}
				else if(compareColors(pixelcolor, Config.Colors.obstacle))
				{
					objId = ObjectId.Obstacle;
				}
				else if(compareColors(pixelcolor, Config.Colors.support))
				{
					objId = ObjectId.Trampoline;
				}
				else if(compareColors(pixelcolor, Config.Colors.coin))
				{
					objId = ObjectId.Coin;
					Config.coinAmount++;
				}
				else if(compareColors(pixelcolor, Config.Colors.enemy))
				{
					Enemy e = new Enemy(
							(float)x*Config.tilesize,
							(float)y*Config.tilesize,
							(float)Config.tilesize,
							(float)Config.tilesize,
							ObjectId.Enemy,
							handler,
							pixelcolor,
							//new Color(rgb[0], rgb[1], rgb[2]),
							true);

						handler.addObject(e);
						Handler.entities.add(e);

				}
				else if(compareColors(pixelcolor, Config.Colors.player))
				{
					player = new Player(
						(float)x*Config.tilesize,
						(float)y*Config.tilesize,
						(float)Config.tilesize,
						(float)Config.tilesize,
						ObjectId.Player,
						handler,
						pixelcolor,
						//new Color(rgb[0], rgb[1], rgb[2]),
						true);

					handler.addObject(player);
				}
				else
				{
					objId = ObjectId.None;
				}

				if(objId != ObjectId.None
				&& !(compareColors(pixelcolor, Config.Colors.player))
				&& !(compareColors(pixelcolor, Config.Colors.enemy)))
				{
					handler.addObject( new Block(
						x*Config.tilesize,
						y*Config.tilesize,
						Config.tilesize,
						Config.tilesize,
						pixelcolor,//new Color(rgb[0], rgb[1], rgb[2]),
						objId,
						handler,
						false)
					);
				}
			}
		}

		System.out.println("Amount of coins: "+Config.coinAmount);


	}
	
	// TODO: ADD in-game speed adjust (timescale)
	// TODO: ADD particle generation timer
	// TODO: ADD 
	
	private static double timescale = 1.0;
	private static double fps = 1.0/120.0;
	private static double ups = 1.0/60.0;
	private static long lastTime = System.nanoTime();
	private static double deltaF = 0;
	private static double deltaU = 0;
	
	public void run()
	{
		init();
		this.requestFocus();
		
		while(running) {
			
			if(killGame) {
				System.exit(0);
				break;
			}
		
			deltaF += (System.nanoTime() - lastTime) / 1000000000.0;
			deltaU += (System.nanoTime() - lastTime) / 1000000000.0;
			
			if (deltaU >= ups) {
				update((float)((deltaU / ups) * timescale), (float)(deltaU * timescale));
				deltaU -= ups;
			}
			
			if (deltaF >= fps) {
				render((float)((deltaF / fps) * timescale), (float)(deltaF * timescale));
				deltaF -= fps;
			}

			lastTime = System.nanoTime();

			try{ Thread.sleep(1); }catch(Exception e){};
			
		}

	}

	public static Color getColor(float p)
	{
		if(p > 0)
			return new Color((float) (1.0-p), p, 0.1f);
		return new Color(0,0,0);
	}

	public static Color randomColor()
	{
		return new Color(
				(int)(Math.random() * ( 255 - 0 )),
				(int)(Math.random() * ( 255 - 0 )),
				(int)(Math.random() * ( 255 - 0 ))
		);
	}

	public static int getRandInt(int min, int max) {
		return rand.nextInt(max) + min;
	}


	public static float lerp(float point1, float point2, float alpha)
	{
		return point1 + (point2-point1) * alpha;
	}


	public static float getPlayerX()
	{
		return player.getX();
	}


	public static float getPlayerY()
	{
		return player.getY();
	}
	
	
	public static double getObjectDistance(GameObject a, GameObject b) {
		double dist = Math.sqrt(
				Math.pow((a.getX() - b.getX()), 2) + 
				Math.pow((a.getY() - b.getY()), 2)
		);
		
		return dist;
	}


	public static Texture getInstance()
	{
		return tex;
	}


	public void keyPressed(KeyEvent e)
	{
		keyDownMap.put(e.getKeyCode(), true);
	}


	public void keyReleased(KeyEvent e)
	{
		keyDownMap.remove(e.getKeyCode());
	}


	public void keyTyped(KeyEvent e)
	{
		// . . .
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println(e.getComponent().getLocationOnScreen());

	}



}
