package gameone;

import java.awt.Image;
import java.awt.Toolkit;

public class Background {

	private int xCoord;
	private int yCoord;
	private int width;
	private int height;
	private Image img;
	
	/**
	 * Goodguy default constructor
	 */
	
	public Background() { // sets default goodguy
		setxCoord(0);
		setyCoord(0);
		setWidth(1440);
		setHeight(900);
		setImg("../files/background.png");
	}
	
	/**
	 * Goodguy overloaded constructor
	 * @param x initial x location
	 * @param y initial y location
	 * @param w initial width
	 * @param h initial height
	 */
	
	public Background(int x, int y, int w, int h, String imgpath) { // allows position, size and image changes
		setxCoord(x);
		setyCoord(y);
		setWidth(w);
		setHeight(h);
		setImg(imgpath);
	}
	
	public void scroll(boolean keyD, boolean keyA, boolean keyW, boolean keyS, int w, int h) {
		int x = getxCoord();
		int y = getyCoord();
		int velocity = 10;
		int multikey = 0;
		
		if (keyD) {
			multikey++;
		} if (keyA) {
			multikey++;
		} if (keyS) {
			multikey++;
		} if (keyW) {
			multikey++;
		}
		
		if (multikey >= 2) {
			velocity = 7;
		}
		
		if (keyD) { // moves right if D or right arrow is pressed
			x -= velocity; // adds velocity to x-value
			setxCoord(x); // sets to new x-value
		} 
		if (keyA) { // moves left if A or left arrow is pressed
			x += velocity; // subtracts velocity from x-value
			setxCoord(x); // sets to new x-value
		} 
		if (keyS) { // moves down if S or down arrow is pressed
			y -= velocity; // subtracts velocity from y-value
			setyCoord(y); // sets to new y-value
		} 
		if (keyW) { // moves up if W or up arrow is pressed
			y += velocity; // adds velocity to y-value
			setyCoord(y); // sets to new y-value
		}
	}
	
	// setters and getters
	public void setImg(String imgpath) {
		this.img = Toolkit.getDefaultToolkit().getImage(imgpath);
	}
	
	public Image getImg() {
		return img;
	}

	public int getxCoord() {
		return this.xCoord;
	}
	
	public int getyCoord() {
		return this.yCoord;
	}

	public void setxCoord(int x) {
		this.xCoord = x;
	}
	
	public void setyCoord(int y) {
		this.yCoord = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int h) {
		this.height = h;
	}
	
}