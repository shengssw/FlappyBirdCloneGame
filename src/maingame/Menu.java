package maingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import maingame.Game.STATE;

public class Menu extends MouseAdapter{
	
	private Game game;
	private int buttonWidth = 200;
	private int buttonHeight = 60;
	private int playX = 220;
	private int playY = 270;
	private int scoreX = 220;
	private int scoreY = 350;
	BufferedImage play;
	BufferedImage score;
	
	public Menu(Game game) {
		this.game = game;
		loadImage();
	}
	
	private void loadImage() {
		try {
			play = ImageIO.read(getClass().getResourceAsStream("/button/play.png"));
			score = ImageIO.read(getClass().getResourceAsStream("/button/score.png"));
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
		}
		
		if(mouseOver(mx, my, scoreX, scoreY, buttonWidth, buttonHeight)) {
			if(game.gameState != STATE.Die) {
				game.gameState = STATE.Score;
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
		/*g.setColor(Color.black);
		
		g.drawRect(playX, playY, buttonWidth, buttonHeight);
		g.drawString("Play", 250, 300);
		
		g.drawRect(scoreX, scoreY, buttonWidth, buttonHeight);
		g.drawString("Score Board", 250, 390); */
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(play, playX, playY, buttonWidth, buttonHeight, null );
		g2.drawImage(score, scoreX, scoreY, buttonWidth, buttonHeight, null );
		
	}

}
