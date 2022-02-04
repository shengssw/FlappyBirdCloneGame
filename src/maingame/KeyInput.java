package maingame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.objects.size(); i++) {
			GameObject currentObj = handler.objects.get(i);
			
			if(currentObj.getId() == ID.bubble) {
				// Key event for bubble
				
				if(key == KeyEvent.VK_DOWN) {
					currentObj.setMove(true);
					
					currentObj.setVelY(2);
				}
			} else if (currentObj.getId() == ID.barrier) {
				
				// Key event for barrier
				if(key == KeyEvent.VK_DOWN) {
					currentObj.setMove(true);
					
					currentObj.setVelX(-2);
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
				
		for(int i = 0; i < handler.objects.size(); i++) {
			GameObject currentObj = handler.objects.get(i);
			
			if(currentObj.getId() == ID.bubble) {
				// Key event for bubble
				
				if(key == KeyEvent.VK_DOWN) {
					currentObj.setVelY(-1);
				}
			}
		}
	}

}
