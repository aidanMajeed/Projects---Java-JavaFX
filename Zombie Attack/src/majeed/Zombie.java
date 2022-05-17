package majeed;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.geometry.Bounds;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Zombie implements Comparable<Zombie>{

	public final int EAST = 1;	//EAST and WEST are constant numbers that just flag which direction the
	public final int WEST = -1; // zombie is walking

	//properties
	private static final Random RNG = new Random();
	
	private static final Image[] ZombieIMGs = {
		new Image("file:images\\zombie0West.png"),
		new Image("file:images\\zombie0East.png"),
		new Image("file:images\\zombie1West.png"),
		new Image("file:images\\zombie1East.png"),
		new Image("file:images\\zombie2West.png"),
		new Image("file:images\\zombie2East.png"),
		new Image("file:images\\zombie3West.png"),
		new Image("file:images\\zombie3East.png")
	};


	
	private static final Image deathEXPLOSION = new Image("file:images\\deadZombie.png");
	
	private double x = 0;
	private double y = 0;
	private double width = 73;
	private double height = 120;
	private double xSpeed = 0;
	private double ySpeed = 0;

	private int facing = EAST; //<-- pick a direction zombies walk by default, the construct could make this random

	private boolean isDead = false;
	private double deathTimer = 1;

	private boolean cleanUp = false;
	
	private ImageView zombieView = new ImageView();
	
	public Zombie() {
		//store the zombie location
		y = 100 + (575 - height) * Math.random();
		//setup the zombie's initial speed
		xSpeed = 20 + 50 * Math.random(); 
		
		//Choose a random image for the new zombie
		int type = RNG.nextInt(8);
		zombieView.setImage(ZombieIMGs[type]);
		
		//random zombies 0, 2, 4, 6 will face west, and 1, 3, 5, 7 face east
		if (type == 0 || type == 2 || type == 4 || type == 6){
			facing = WEST;
			x = 900 + width + 3 * xSpeed * Math.random();		
			}
		else {
			facing = EAST;
			x = -width - 3 * xSpeed * Math.random();		
			}
	
		//zombieView.relocate(x, y);
		//set the size of the car
			zombieView.setPreserveRatio(false);
			zombieView.setFitHeight(height);
			zombieView.setFitWidth(width);
			zombieView.setSmooth(true);

			locateImage();
	}
//CLASS METHODS--------------------
	
	//Moves the zombieView to correspond to the (x, y) location of the zombie
	 //Use any time the location changes to keep the position of the 
	 // image updated
	private void locateImage() {
		zombieView.relocate(x, y);
	}
	
	//returns left side of zombie
	public double getX() {
		return x;
	}
	
	//changes left side of zombie
	public void setX(double x) {
		this.x = x;
		locateImage();
	}
	
	//returns top of the zombie
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
		locateImage();
	}
	
	//returns the horizontal speed
	public double getxSpeed() {
		return xSpeed;
	}
	
	// xSpeed new horizontal speed in pixels per second
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	
	//returns the vertical speed
	public double getySpeed() {
		return ySpeed;
	}
	
	// ySpeed new vertical speed in pixels per second
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	
	//returns the width of the zombie image
	public double getWidth() {
		return width;
	}
	
	//returns the height of the zombie image
	public double getHeight() {
		return height;
	}
	
	//returns the ImageView object, used to draw the zombie on a stage
	public ImageView getView() {
		return zombieView;
	}
	
	//returns Bounds of the zombieView object in the parent frame
	public Bounds getBoundsInParent() {
		return zombieView.getBoundsInParent();
	}
	
	//Reports if the zombie is dead (dead zombies don't collide...)
	public boolean isDead() {
		return isDead;
	}
	
	//Tells if the zombie can be cleaned up because it has been dead long enough
	public boolean isReadyForCleanUp() {	
		return cleanUp;
	}
	
	//Move the zombie, using the current speed * elapsed time
	 // elapsedTime amount of time that passed since the last update, in seconds.
	public void update(double elapsedTime) {
		
		//by multiplying by the "facing" variable, you can get the zombie to walk the right way
		x = x + xSpeed * elapsedTime * facing; 
							

		y = y + ySpeed * elapsedTime; 
		
		locateImage();		

		if (isDead) {
			deathTimer -= elapsedTime;
			if (deathTimer <= 0) cleanUp = true;
		}
		if (facing == WEST && x < -width) { cleanUp = true; }
		if (facing == EAST && x > 900 + width) { cleanUp = true; }
	}
	
	//kill the zombie by making it explode
	public void kill() {
		isDead = true;
		//Set the Dead image
		zombieView.setImage(deathEXPLOSION);
		zombieView.setFitWidth(70);
		zombieView.setFitHeight(120);
		xSpeed =0;
		ySpeed=0;
	}

	//creates more accurate boundaries for the zombie
	public Bounds getObjectBounds() {
		Rectangle rect = new Rectangle(x + 15, y, width - 30, height);
		return rect.getBoundsInParent();
		}
	
	
	//Returns the direction the zombie is facing
	public int getFacing() {
		return facing;
	}
	public int compareTo(Zombie walkerZombie) {
		// TODO Auto-generated method stub
		return (int)(this.y - walkerZombie.getY());
	}

}
