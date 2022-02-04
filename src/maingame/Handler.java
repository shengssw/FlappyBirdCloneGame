package maingame;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	LinkedList<GameObject> objects = new LinkedList<GameObject>();
	ScoreManager manager;
	
	public Handler(ScoreManager manager) {
		this.manager = manager;
	}
	
	public void tick() {	
		for(int i = 0; i < objects.size(); i++) {
			GameObject temp = objects.get(i);
			
			temp.tick();
			
			if(temp.getId() == ID.barrier) {
				barrier b = (barrier)temp;
				
				// Update score
				if(b.getX()+b.getWidth() < Game.BUBBLEX && b.getScored() == false) {
					b.setScored(true);
					manager.increaseScore();
				}
				
				// Delete barrier if move out of frame
				if(b.getX()+b.getWidth() < 0) {
					removeObject(temp);
					//System.out.println("remove!");
				}
				
				// Add new barrier if there is enough space
				int distance = b.getDistance()+b.getX()+b.getWidth();
				if( distance < Game.WIDTH && b.getFollow() == false) {
					b.setFollow(true);
					barrier newBarrier = new barrier(700,0,ID.barrier);
					addObject(newBarrier);
					//System.out.println("Add new!");
				}
			}
			
			
		}
		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < objects.size(); i++) {
			GameObject temp = objects.get(i);
			temp.render(g);
		}
	}
	
	public void addObject(GameObject obj) {
		this.objects.add(obj);
	}
	
	public void removeObject(GameObject obj) {
		this.objects.remove(obj);
	}
	
	public void clearObject() {
		this.objects.clear();
	}
}
