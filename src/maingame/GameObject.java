package maingame;

import java.awt.Graphics;

public abstract class GameObject {
	
	protected int x, y;
	protected double velx, vely;
	protected ID id;
	protected boolean move;
	
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.move = false;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setVelX(double velx) {
		this.velx = velx;
	}
	
	public void setVelY(double vely) {
		this.vely = vely;
	}
	
	public double getVelX() {
		return this.velx;
	}
	
	public double getVelY() {
		return this.vely;
	}
	
	public void setId(ID id) {
		this.id = id;
	}
	
	public ID getId() {
		return id;
	}
	
	public void setMove(boolean move) {
		this.move = move;
	}
	
	public boolean getMove() {
		return this.move;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);

}
