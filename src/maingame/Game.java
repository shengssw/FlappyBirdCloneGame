package maingame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1968152406444437499L;
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 640/12 * 10;
	
	public static final int BUBBLEX = 300;
	public static final int BUBBLEY = 200;
	
	public static boolean DIE = false;

	private Thread currentThread;
	private boolean running = false;
	private Handler gameHandler;
	private ScoreManager scoreManager;
	private Menu gameMenu;
	private EndMenu endMenu;
	
	// Images that will be displayed
	private BufferedImage background;
	private BufferedImage background1;
	private BufferedImage background2;
	private BufferedImage background3;
	
	// Store Data
	private String saveDataPath;
	private String filename = "scoreData";
	private File f;

	public enum STATE {
		Menu,
		Score,
		Game,
		Die
	};
	
	public STATE gameState = STATE.Menu;
	
	/* Constructor */
	public Game() {
		
		prepareFile();
		scoreManager = new ScoreManager(this);
		gameHandler = new Handler(scoreManager);
		gameMenu = new Menu(this);
		endMenu = new EndMenu(this);
		this.addKeyListener(new KeyInput(gameHandler));
		this.addMouseListener(gameMenu);
		this.addMouseListener(scoreManager);
		this.addMouseListener(endMenu);
		new Window(WIDTH, HEIGHT, this, "Bubble Land");
		
		gameHandler.addObject(new bubble(BUBBLEX, BUBBLEY, ID.bubble, gameHandler));
		gameHandler.addObject(new barrier(700, 0, ID.barrier));
		loadBackground();
		
		
	}
	
	private void prepareFile() {
		try {
			saveDataPath = Game.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			f = new File(saveDataPath, filename);
			if(!f.exists()) {
				f.createNewFile();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadBackground() {
		
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/background/background.png"));
			background1 = ImageIO.read(getClass().getResourceAsStream("/background/background2.png"));
			background2 = ImageIO.read(getClass().getResourceAsStream("/background/endmenu.png"));
			background3 = ImageIO.read(getClass().getResourceAsStream("/background/scoreBoard.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void start() {
		currentThread = new Thread(this);
		currentThread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			currentThread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Main Game loop */
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick();
				delta--;
			}
			
			if(running) {
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		
		if (gameState == STATE.Game) {
			
			gameHandler.tick();
			scoreManager.tick();
			
			
			if (Game.DIE) {	
				gameState = STATE.Die;
			} 
			
		} else if (gameState == STATE.Menu) {
			gameMenu.tick();
		} else if (gameState == STATE.Die) {
			endMenu.tick();
		} else if (gameState == STATE.Score) {
			scoreManager.tick();
		}
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;

		
		if(gameState == STATE.Game) {
			g2.drawImage(background1, 0, 0, WIDTH, HEIGHT, null);
			
			gameHandler.render(g);
			scoreManager.render(g);
		} else if (gameState == STATE.Menu) {
			g2.drawImage(background,0,0, WIDTH, HEIGHT, null);
			gameMenu.render(g);
		} else if (gameState == STATE.Die) {
			g2.drawImage(background2,0,0, WIDTH, HEIGHT, null);
			endMenu.render(g);
		} else if (gameState == STATE.Score) {
			g2.drawImage(background3,0,0, WIDTH, HEIGHT, null);
			scoreManager.render(g);
		}
		
		g.dispose();
		bs.show();
		
	}
	
	
	public void addNewObjects() {
		gameHandler.manager.resetScore();
		gameHandler.clearObject();
		//System.out.println("The game has " + gameHandler.objects.size() + " objects");
		gameHandler.addObject(new bubble(BUBBLEX, BUBBLEY, ID.bubble, gameHandler));
		gameHandler.addObject(new barrier(700, 0, ID.barrier));
	}
	
	public int getCurrentScore() {
		return scoreManager.getScore();
	}
	
	public File getFile() {
		return this.f;
	}

	public static void main(String[] args) {
		
	    new Game();
	    
	}
}
