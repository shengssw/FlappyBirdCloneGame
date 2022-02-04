package maingame;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	private static final long serialVersionUID = 4657084206480202588L;
	
	/* Constructor */
	public Window( int width, int height, Game game, String title) {
		
		JFrame window = new JFrame(title);
		
		// Set size
		window.setPreferredSize(new Dimension(width, height));
		window.setMaximumSize(new Dimension(width, height));
		window.setMinimumSize(new Dimension(width, height));
		window.setResizable(false);
		
		// Set close
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		
		// Add game
		window.add(game);
		window.setVisible(true);
		game.start();
		
	}
	
}
