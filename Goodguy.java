package gameone;

import java.awt.Image;
import java.awt.Toolkit;

public class Goodguy {

	private int xCoord;
	private int yCoord;
	private int width;
	private int height;
	private Image img;
	
	/**
	 * Goodguy default constructor
	 */
	
	public Goodguy() { // sets default goodguy
		setxCoord(720);
		setyCoord(450);
		setWidth(26);
		setHeight(52);
		setImg("../files/yoshi/Front3.png");
	}
	
	/**
	 * Goodguy overloaded constructor
	 * @param x initial x location
	 * @param y initial y location
	 * @param w initial width
	 * @param h initial height
	 */
	
	public Goodguy(int x, int y, int w, int h, String imgpath) { // allows position, size and image changes
		setxCoord(x);
		setyCoord(y);
		setWidth(w);
		setHeight(h);
		setImg(imgpath);
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