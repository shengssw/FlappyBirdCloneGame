package maingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class bubble extends GameObject{	
	
	private boolean wobble;
	private int count;
	private int index;
	private int imageIndex;
	private Handler handler;
	private BufferedImage b1, b2, b3, b4, b5, b6;
	
	public bubble(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		wobble = true;
		count = 0;
		index = 0;
		imageIndex = 0;
		vely = 0;
		loadBubbleImage();
	}
	
	public void setWobble(boolean wobble) {
		this.wobble = wobble;
	}
	
	public Rectangle getBound() {
		return new Rectangle(x, y, 32, 32);
	}
	
	private void detectCollision() {
		for(int i = 0; i < this.handler.objects.size(); i++) {
			
			GameObject currentObj = this.handler.objects.get(i);
			
			if(currentObj.getId() == ID.barrier) {
				// see if there is collision
				barrier b = (barrier)currentObj;
				if(getBound().intersects(b.getTop()) || getBound().intersects(b.getBottom())) {
					//int score = handler.manager.getScore();
					//System.out.println("You die! Your score is " + score);
					handler.manager.saveCurrentScore();
					Game.DIE = true;
				}
			}
		}
	}
	
	private void loadBubbleImage() {
		
		try {
			b1 = ImageIO.read(getClass().getResourceAsStream("/bubbleImage/b1.png"));
			b2 = ImageIO.read(getClass().getResourceAsStream("/bubbleImage/b2.png"));
			b3 = ImageIO.read(getClass().getResourceAsStream("/bubbleImage/b3.png"));
			b4 = ImageIO.read(getClass().getResourceAsStream("/bubbleImage/b4.png"));
			b5 = ImageIO.read(getClass().getResourceAsStream("/bubbleImage/b5.png"));
			b6 = ImageIO.read(getClass().getResourceAsStream("/bubbleImage/b6.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		// Update Image index
		index++;
		if (index % 180 == 0) {
			index = 0;
		}
		imageIndex = index / 30 + 1;	
		
		// Beginning of the game, the bubble is wobbling in the air
		if(!this.move) {
			if(wobble) {
				this.setVelY(1);
				y += vely;
				count++;
				
				if (count > 10) {
					wobble = false;
					count = 0;
				}
				
			} else {
				this.setVelY(-1);
				y += vely;
				count++;
				
				if (count > 10) {
					wobble = true;
					count = 0;
				}	
			}
		} else {
			//this.setVelY(-2);
			y += vely;
			
			detectCollision();
		} 
		
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.white);
		//g.fillRect(x, y, 30, 30);
		
		Graphics2D g2 = (Graphics2D) g;
		
		BufferedImage image = null;
		switch(imageIndex) {
		case 1:
			image = b1;
			break;
		case 2:
			image = b2;
			break;
		case 3:
			image = b3;
			break;
		case 4:
			image = b4;
			break;
		case 5:
			image = b5;
			break;
		case 6:
			image = b6;
			break;
		default:
			image = b1;
		}
		
		g2.drawImage(image, x, y, 32, 32, null);
	}

}
