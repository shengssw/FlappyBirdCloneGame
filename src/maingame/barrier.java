package maingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class barrier extends GameObject{
	
	private int width = 85;
	private int height;
	private int space = 120;
	private int distance = 100;
	private boolean follow = false;
	private boolean scored = false;
	private Random rand;
	private BufferedImage p1, p2;
	
	public barrier(int x, int y, ID id) {
		super(x, y, id);
		
		// Set parameters 
		rand = new Random();
		height = 80 + rand.nextInt(150);
		loadImage();
		
	}
	
	private void loadImage() {
		
		try {
			p1 = ImageIO.read(getClass().getResourceAsStream("/pipeImage/p1.png"));
			p2 = ImageIO.read(getClass().getResourceAsStream("/pipeImage/p2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getFollow() {
		return this.follow;
	}
	
	public void setFollow(boolean follow) {
		this.follow = follow;
	}
	
	public boolean getScored() {
		return this.scored;
	}
	
	public void setScored(boolean scored) {
		this.scored = scored;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public Rectangle getTop() {
		return new Rectangle(x,y, width, height);
	}
	
	public Rectangle getBottom() {
		return new Rectangle(x, (height+space), width, (Game.HEIGHT-height-space));
	}
	
	public void tick() {
		if(this.move) {
			x += velx;
		}
	}
	
	
	public void render(Graphics g) {
		//g.setColor(Color.gray);
		//g.fillRect(x, y, width, height);
		//g.fillRect(x, (height+space), width, (Game.HEIGHT-height-space));
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(p1, x, y, width, height, null);
		g2.drawImage(p2, x, (height+space), width, (Game.HEIGHT-height-space), null);
	}

}
