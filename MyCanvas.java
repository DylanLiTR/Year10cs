package gameone;

// imports classes
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.Timer;

import sun.audio.*;

public class MyCanvas extends Canvas implements KeyListener {

	// global variables
	private static final long serialVersionUID = 1L;
	Goodguy Yoshi = new Goodguy(707, 424, 26, 52, "files/yoshi/Front3.png"); // creates Yoshi placement and size
	LinkedList <Background> background = new LinkedList <Background>();
	LinkedList<Badguy> badguys = new LinkedList<Badguy>();
	int hp = 100;
	int score = 0;
	int highScore = 0;
	Random xy = new Random();
	
	// direction keypress booleans
	boolean keyW = false;
	boolean keyA = false;
	boolean keyS = false;
	boolean keyD = false;
	volatile boolean keyEnter = false;
	
	/**
	 * MyCanvas extends java.awt.Canvas
	 * @author dylan.li
	 * @since October 25, 2018
	 * @param no parameters, default constructor
	 */

	public MyCanvas() {
		this.setSize(1440, 900); // set same size as MyScreen
		this.addKeyListener(this); // add the listener to your canvas
		playIt("files/sounds/Mario.wav"); // access .wav background music file
		background(); // runs background method
		start.start(); // starts initiating timer
	}
	
	public void playIt(String filename) { // plays audio
		try {
			InputStream in = new FileInputStream(filename);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	/**
	 *  paint overload Canvas to draw an oval
	 *  @param graphics context variable called g 
	 */
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Font title = new Font("Times New Roman", Font.BOLD, 72);
		Font normal = new Font("Arial", Font.PLAIN, 12);
		g.setFont(normal);
		int xposition = Yoshi.getxCoord() + Yoshi.getWidth()/2 - hp/2; // actual centered xposition
		int yposition = Yoshi.getyCoord() + Yoshi.getHeight() * 3/2; // actual centered yposition
		float opacity = 0.9f; // sets opacity
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)); // reduce opacity
		for(int i = 0; i < background.size(); i++) { // draw background
			Background backg = (Background) background.get(i); // gets each background
			g2d.drawImage(backg.getImg(), backg.getxCoord(), backg.getyCoord(), backg.getWidth(), backg.getHeight(), this); // draws each background tile
		}
		
		g.drawImage(Yoshi.getImg(), Yoshi.getxCoord(), Yoshi.getyCoord(), Yoshi.getWidth(), Yoshi.getHeight(), this); // draw good guy
		
		// changes hp bar color as it decreases
		if (hp > 70) {
			g.setColor(Color.GREEN); // green color of hp bar
		} else if (hp > 30) {
			g.setColor(Color.YELLOW); // yellow color of hp bar
		} else {
			g.setColor(Color.RED); // red color of hp bar
		}
		g.fillRect(xposition, yposition, hp, 15); // draw hp bar
		
		if (!health.isRunning()) {
			g.drawString("Use WASD Keys to Move Around", 0, 10);
			
			g.setFont(title);
			g.drawString("YOSHI'S DAY OFF", Yoshi.getxCoord() - 290, yposition - 300);
			
			g.drawImage(Toolkit.getDefaultToolkit().getImage("files/Enter.png"), xposition - 260, yposition + Yoshi.getHeight(), 640, 74, this); // start instructions
		}

		g.setFont(normal);
		
		if (hp > 40) {
			g.drawString("HP: " + hp, xposition, yposition); // displays hp
		} else {
			g.drawString("HP: " + hp, Yoshi.getxCoord() - 5, yposition); // displays hp recentered
		}
		
		for(int i = 0; i < badguys.size(); i++) { // draw bad guys
			Badguy bg = (Badguy) badguys.get(i); // gets each badguy
			g.drawImage(bg.getImg(), bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight(), this); // draws each badguy
		}
		
		g.setColor(Color.WHITE); // sets drawing color to white
		g.drawString("Score: " + score, 1340, 20); // displays score in the top right corner
		g.drawString("Highscore: " + highScore, 1200, 20); // displays highscore in the top right corner
		
		// game end || restart
		if (hp == 0) {
			playIt("files/sounds/Gameover.wav"); // gameover sound effects
			spawn.stop(); // stops spawning badguys
			run.stop(); // stops run timer
			badguys.clear(); // clears all badguys
			g.drawImage(Toolkit.getDefaultToolkit().getImage("files/gameover.png"), 0, 0, 1440, 900, this);
			g.drawString("Score: " + score, xposition - 50, yposition + 120); // displays score below endscreen
			g.drawString("Press Enter to Play Again", xposition - 95, yposition + 200); // instructions to play again
			
			if (score > highScore) {
				g.setFont(title);
				g.drawString("NEW HIGHSCORE!", xposition - 300, yposition - 300); // displays whether highscore is achieved
			}
			
			start.start(); // starts initiating timer
 		}
	}

	Timer start = new Timer(50, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (keyEnter) {
				if (score > highScore) {
					highScore = score; // sets new highscore
				}
				
				score = 0; // resets score
				hp = 100; // resets hp
				
				// begin all timers and runs initial methods
				add();
				run.start();
				health.start();
				spawn.start();
				start.stop();
			}
		}
	});
	
	// spawns badguys at edges of the window
	Timer spawn = new Timer(2500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Badguy bgt = new Badguy(xy.nextInt(1400)-35, 0-35, 35, 35, "files/badguy/Goomba.png"); // new badguy at top edge
			Badguy bgl = new Badguy(-35, xy.nextInt(900)-35, 35, 35, "files/badguy/Goomba.png"); // new badguy at left edge
			Badguy bgb = new Badguy(xy.nextInt(1400)+35, 825+35, 35, 35, "files/badguy/Goomba.png"); // new badguy at bottom edge
			Badguy bgr = new Badguy(1440, xy.nextInt(900)+35, 35, 35, "files/badguy/Goomba.png"); // new badguy at right edge
			
			// adds each badguy
			badguys.add(bgt);
			badguys.add(bgl);
			badguys.add(bgb);
			badguys.add(bgr);
			
			repaint();
		}
	});
	
	Timer health = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (hp > 0) {
				hp -= 2; // decreases health over time
			}
		}
	});
	
	Timer run = new Timer(50, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < badguys.size(); i++) { // check if badguy is eaten
				Badguy bg = (Badguy) badguys.get(i); // gets data on each badguy
				
				/*if (bg.getxCoord() < winwidth && bg.getxCoord() > 0 && bg.getyCoord() < winheight && bg.getyCoord() > 0) {
				
				double bgDist = Math.sqrt((bg.getxCoord()-Yoshi.getxCoord())^2+(bg.getyCoord()-Yoshi.getyCoord())^2);
				double bgRatio = speed/bgDist;
				
				System.out.println(bg.getxCoord());
				//System.out.println("bgDist = " + bgDist);
				if (bgDist < 300) {
					bg.setxCoord((int) (bgRatio*(bg.getxCoord()-Yoshi.getxCoord())+bg.getxCoord()));
					bg.setyCoord((int) (bgRatio*(bg.getyCoord()-Yoshi.getyCoord())+bg.getyCoord()));
					
					//System.out.println(bg.getxCoord());
					//System.out.println(bg.getyCoord());*/
					bg.run(Yoshi.getxCoord(), Yoshi.getyCoord()); // run method
					repaint();
				//}
				//}
			}
		}
	});
	
	public void background() {
		int winwidth = this.getWidth(); // window width
		int winheight = this.getHeight(); // window height
		
		for (int i = 0; i < 2; i++) { // adds background
			Background backg = new Background(-winwidth/2 + i*winwidth, -winheight/2, winwidth, winheight, "files/background.png"); // creates a new tile
			background.add(backg); // adds the new tiles
		}
		
		for (int i = 0; i < 2; i++) { // adds background
			Background backg = new Background(-winwidth/2 + i*winwidth, winheight/2, winwidth, winheight, "files/background2.png"); // creates a new tile
			background.add(backg); // adds the new tiles
		}
	}
	
	public void add() {
		Random rand = new Random();
		int winwidth = this.getWidth(); // window width
		int winheight = this.getHeight(); // window height
		
		for (int i = 0; i < 10; i++) { // repeats 10 times
			Badguy bg = new Badguy(rand.nextInt(winwidth), rand.nextInt(winheight), 35, 35,"files/badguy/Goomba.png"); // spawns badguys in random locations
			Rectangle r = new Rectangle(100, 100, 50, 50); // restricted spawn area
			if (r.contains(Yoshi.getxCoord(),Yoshi.getyCoord())) { // check to see if badguy spawns on Yoshi
				System.out.println("enemy spawned atop Yoshi"); // prints if badguy spawns atop Yoshi
				continue;
			}
			badguys.add(bg); // adds all badguys
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println(e);
	}
	
	int s = 0; // sprite change variable
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e);
		
		// multikey booleans
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			keyW = true;
			break;
		case KeyEvent.VK_A:
			keyA = true;
			break;
		case KeyEvent.VK_S:
			keyS = true;
			break;
		case KeyEvent.VK_D:
			keyD = true;
			break;
		case KeyEvent.VK_ENTER:
			keyEnter = true;
			break;	
		}
		
		String[] movement = { // goodguy movement animation
				// Right Facing
				"files/yoshi/Right3.png", 
				"files/yoshi/Right2.png",
				"files/yoshi/Right.png",
				"files/yoshi/Right2.png", 
				"files/yoshi/Right3.png",
				"files/yoshi/Right4.png",
				"files/yoshi/Right5.png",
				"files/yoshi/Right4.png",
				
				// Left Facing
				"files/yoshi/Left3.png",
				"files/yoshi/Left2.png",
				"files/yoshi/Left.png",
				"files/yoshi/Left2.png",
				"files/yoshi/Left3.png",
				"files/yoshi/Left4.png",
				"files/yoshi/Left5.png",
				"files/yoshi/Left4.png",
				
				// Front Facing
				"files/yoshi/Front3.png",
				"files/yoshi/Front2.png",
				"files/yoshi/Front.png",
				"files/yoshi/Front2.png",
				"files/yoshi/Front3.png",
				"files/yoshi/Front4.png",
				"files/yoshi/Front5.png",
				"files/yoshi/Front4.png",
				
				//Back Facing
				"files/yoshi/Back3.png",
				"files/yoshi/Back2.png",
				"files/yoshi/Back.png",
				"files/yoshi/Back2.png",
				"files/yoshi/Back3.png",
				"files/yoshi/Back4.png",
				"files/yoshi/Back5.png",
				"files/yoshi/Back4.png",
		};
		
		if (keyD) { // moves right if D or right arrow is pressed
			Yoshi.setImg(movement[((s + 2)/2) % 8]); // right sprite cycle
		} else if (keyA) { // moves left if A or left arrow is pressed
			Yoshi.setImg(movement[(((s + 2)/2) % 8) + 8]); // left sprite cycle
		}  else if (keyS) { // moves down if S or down arrow is pressed
			Yoshi.setImg(movement[(((s + 2)/2) % 8) + 16]); // down sprite cycle
		} else if (keyW) { // moves up if W or up arrow is pressed
			Yoshi.setImg(movement[(((s + 2)/2) % 8) + 24]); // up sprite cycle
		}
		
		s++; // changes sprite variable one time per keypress
		
		// change dimensions of goodguy due to sprite size
		if (keyD || keyA) {
			Yoshi.setWidth(32);
			Yoshi.setHeight(51);
		} else if (keyS || keyW) {
			Yoshi.setWidth(26);
			Yoshi.setHeight(52);
		}
		
		for (int i = 0; i < background.size(); i++) { // scroll background
			Background backg = (Background) background.get(i); // gets background
			backg.scroll(keyD, keyA, keyW, keyS, backg.getWidth(), backg.getHeight()); // scrolling background
			
			// tiles background
			if (backg.getyCoord() + backg.getHeight() < 0) {
				int over = backg.getyCoord() + backg.getHeight(); // fix bottom margins
				backg.setyCoord(900 + over); // sets new ycoord under current tile
			}
			if (backg.getxCoord() + backg.getWidth() < 0) {
				int over = backg.getxCoord() + backg.getWidth(); // fix right margins
				backg.setxCoord(1440 + over); // sets new xcoord to the right of current tile
			}
			if (backg.getyCoord() > 900) {
				int over = backg.getyCoord() - 900; // fix top margins
				backg.setyCoord(-900 + over); // sets new ycoord above current tile
			}
			if (backg.getxCoord() > 1440) {
				int over = backg.getxCoord() - 1440; // fix left margins
				backg.setxCoord(-1440 + over); // sets new xcoord to the left of current tile
			}
		}
		
		if (hp == 0) {
			return;
		}
		
		for (int i = 0; i < badguys.size(); i++) { // check if badguy is eaten
			Badguy bg = (Badguy) badguys.get(i);
			Rectangle ggBox = new Rectangle(Yoshi.getxCoord(), Yoshi.getyCoord(), Yoshi.getWidth(), Yoshi.getHeight()); // rectangle hitbox around Yoshi
			Rectangle bgBox = new Rectangle(bg.getxCoord(),bg.getyCoord(),bg.getWidth(),bg.getHeight()); // rectangle hitbox around badguys
			if (bgBox.intersects(ggBox)) { // hitbox intersection
				System.out.println("Yoshi ate 'em");
				badguys.remove(i); // removes the overlapped badguy
				
				score += 10; // increase score for each eaten badguy
				
				if (hp < 100) { // adds hp if badguy is eaten
					hp += 2;
				}
				
				if (hp > 100) { // max hp
					hp = 100;
				}
			}
			bg.moveIt(keyD, keyA, keyW, keyS, bg.getWidth(), bg.getHeight()); // moves badguys
		}
		repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println(e);
		s = 0; // resets sprite variable
		
		if (keyD) { // moves right if D or right arrow is pressed
			Yoshi.setImg("files/yoshi/Right3.png");
		} else if (keyA) { // moves left if A or left arrow is pressed
			Yoshi.setImg("files/yoshi/Left3.png");
		}  else if (keyS) { // moves down if S or down arrow is pressed
			Yoshi.setImg("files/yoshi/Front3.png");
		} else if (keyW) { // moves up if W or up arrow is pressed
			Yoshi.setImg("files/yoshi/Back3.png");
		}
		
		// resets keypress booleans
		keyW = false;
		keyA = false;
		keyS = false;
		keyD = false;
		keyEnter = false;
		
		if (hp > 0) {
			repaint();
		}
	}
}