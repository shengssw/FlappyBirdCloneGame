package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import maingame.Game.STATE;

public class EndMenu extends MouseAdapter{

	private Game game;
	private int buttonWidth = 200;
	private int buttonHeight = 60;
	private int playX = 220;
	private int playY = 270;
	private int quitX = 220;
	private int quitY = 350;
	private BufferedImage restart;
	private BufferedImage quit;
	
	public EndMenu(Game game) {
		this.game = game;
		loadImage();
	}
	
	private void loadImage() {
		try {
			restart = ImageIO.read(getClass().getResourceAsStream("/button/restart.png"));
			quit = ImageIO.read(getClass().getResourceAsStream("/button/quit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, playX, playY, buttonWidth, buttonHeight)) {
			Game.DIE = false;
			game.gameState = STATE.Game;	
			game.addNewObjects();
		}
		
		if(mouseOver(mx, my, quitX, quitY, buttonWidth, buttonHeight)) {
			if(game.gameState == STATE.Die) {
				System.exit(1);
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void tick() {
		
	}
	
	
	public void render(Graphics g) {
		//g.setColor(Color.white);
		//g.drawRect(180, 120, 280, 100);
		Graphics2D g2 = (Graphics2D) g;
		int score = game.getCurrentScore();
		g2.setColor(Color.gray);
		g2.setFont(new Font("TimesRoman", Font.BOLD, 30));
		g2.drawString( " " + score, 280, 190);
		
		//g.drawRect(playX, playY, buttonWidth, buttonHeight);
		//g.drawString("Restart", 250, 300);
		g2.drawImage(restart, playX, playY, buttonWidth, buttonHeight, null);
		g2.drawImage(quit, quitX, quitY, buttonWidth, buttonHeight, null);
		
		//g.drawRect(quitX, quitY, buttonWidth, buttonHeight);
		//g.drawString("Quit", 250, 390);
	}
	
	
}
