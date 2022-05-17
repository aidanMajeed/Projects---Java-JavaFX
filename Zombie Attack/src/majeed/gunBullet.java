package majeed;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class gunBullet {
	private static final Image BULLETS = new Image("file:images\\bulletEast.png");
	private static final Image BULLETS_WEST = new Image("file:images\\bulletWest.png");
	
	private double x = 0;
	private double y = 0;
	private double width = 20;
	private double height = 7;
	private double xSpeed = 300;
	private double ySpeed = 0;
	private boolean isDead = false;
	
	private ImageView view = new ImageView(BULLETS);
	
	//stores location of bullets
	public gunBullet(double startX, double startY) {
		this.x = startX;
		this.y = startY;
		
		view.setFitWidth(width);
		view.setFitHeight(height);
		
		locateImage();		
	}

	//creates a change in direction of the bullets
	public gunBullet(double startX, double startY, int direction) {
		this.x = startX;
		this.y = startY;
		
		if (direction == shooter.EAST) {
			xSpeed = 300;
		}
		else if (direction == shooter.WEST) {
			xSpeed = -300;
			view.setImage(BULLETS_WEST);
		}
		
		view.setFitWidth(width);
		view.setFitHeight(height);
		
		locateImage();		
	}

	//return x location of the bullet
	public double getX() {
		return x;
	}
	
	//return y location of the bullet
	public double getY() {
		return y;
	}
		
	//Returns a reference to the ImageView for displaying the bullet
	//returns bullet image view
	public ImageView getView() { 
		return view; 
	}
	
	//sets the bullet for collisions
	public void kill() { 
		isDead = true;
		view.setImage(null);
	}
	
	//tells if the bullet is ready for game clean up to remove it
	 //return true if the zombie is dead or bullet has moved off screen
	public boolean isReadyForCleanUp() {
		if (isDead) return true;
		if (y < -height) return true;
		
		return false;
	}
	
	// Move the bullet according to how much time has elapsed since the 
	//last update
	 //elapsedTime time in seconds
	public void move(double elapsedTime) {
		x += xSpeed * elapsedTime;
		y += ySpeed * elapsedTime;

		locateImage();		
	}
	
	//moves the image view to correspond to the x, y of the bullet
	 //use when location changes to keep image updated
	private void locateImage() {
		view.relocate(x, y);
	}
	
	//boundaries for bullets
	public Bounds getBoundsInParent() {
		return view.getBoundsInParent();
	}
}


