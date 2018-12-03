package gameone;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class Badguy {

	private int xCoord;
	private int yCoord;
	private int width;
	private int height;
	private Image img;
	
	/**
	 * Badguy default constructor
	 */
	
	public Badguy() {
		setxCoord(10);
		setyCoord(10);
		setWidth(30);
		setHeight(30);
		setImg("../files/badguy/goomba.png");
	}
	
	/**
	 * Badguy overloaded constructor
	 * @param x initial x location
	 * @param y initial y location
	 * @param w initial width
	 * @param h initial height
	 */
	
	public Badguy(int x, int y, int w, int h, String imgpath) {
		setxCoord(x);
		setyCoord(y);
		setWidth(w);
		setHeight(h);
		setImg(imgpath);
	}
	
	public void moveIt(boolean keyD, boolean keyA, boolean keyW, boolean keyS, int w, int h) {
		int x = getxCoord(); // set x to Goomba x-coord to allow movement
		int y = getyCoord(); // set y to Goomba y-coord to allow movement
		int velocity = 10;
		int multikey = 0;
		
		// increments multikey movement
		if (keyD) {
			multikey++;
		} if (keyA) {
			multikey++;
		} if (keyS) {
			multikey++;
		} if (keyW) {
			multikey++;
		}
		
		// changes diagonal velocity
		if (multikey >= 2) {
			velocity = 7;
		}
		
		if (keyD) { // moves right if D or right arrow is pressed
			x -= velocity; // adds velocity to x-value
			setxCoord(x); // sets to new x-value

		} if (keyA) { // moves left if A or left arrow is pressed
			x += velocity; // subtracts velocity from x-value
			setxCoord(x); // sets to new x-value

		} if (keyS) { // moves down if S or down arrow is pressed
			y -= velocity; // subtracts velocity from y-value
			setyCoord(y); // sets to new y-value

		} if (keyW) { // moves up if W or up arrow is pressed
			y += velocity; // adds velocity to y-value
			setyCoord(y); // sets to new y-value

		}
	}

	public void run(int Yoshix, int Yoshiy) {
		//int speed = 1;
		int x = getxCoord();
		int y = getyCoord();
		
		// badguys run from goodguy
		if (Math.abs(x - Yoshix) + Math.abs(y - Yoshiy) < 300) { // radius of enemies running away
			if (x > Yoshix) {
				x = x + 4;
				setxCoord(x);
			}
			if (x < Yoshix) {
				x = x - 4;
				setxCoord(x);
			}
			if (y > Yoshiy) {
				y = y + 4;
				setyCoord(y);
			}
			if (y < Yoshiy) {
				y = y - 4;
				setyCoord(y);
			}
		} else {
			Random rand = new Random();
			setxCoord(x + (int) (rand.nextInt(2+1+2)-2)); // random jittery movement
			setyCoord(y + (int) (rand.nextInt(2+1+2)-2)); // random jittery movement
			/*double bgDist = Math.sqrt((getxCoord()-Yoshix^2+(getyCoord()-Yoshiy)^2));
			double bgRatio = speed/bgDist;
			
			setxCoord((int) (bgRatio*(getxCoord()-Yoshix)+getxCoord()));
			setyCoord((int) (bgRatio*(getyCoord()-Yoshiy)+getyCoord()));*/
		}
	}
	
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