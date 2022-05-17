package majeed;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class shooter {

	private double x = 0;
	private double y = 0;
	private double width = 88;
	private double height = 120;
	private boolean shooterDead = true;
	private int facing = EAST;

	public static final int EAST = 1;
	public static final int WEST = 2;
	
	public static final Image PLAYER_PIC = new Image("file:images\\shooterEast.png");
	public static final Image PLAYER_PIC_WEST = new Image("file:images\\shooterWest.png");
	private static final Image deadPlayer = new Image("file:images\\deadPlayer.png");

	
	private ImageView playerView = new ImageView(PLAYER_PIC);
	
	public shooter(double xPos, double yPos) {
		x = xPos;
		y = yPos;
		
		//set the size of the player
		playerView.setPreserveRatio(false);
		playerView.setFitHeight(height);
		playerView.setFitWidth(width);
		playerView.setSmooth(true);
		
		locateImage();
	}	
	
	/**
	 * @return the playerDead
	 */
	public boolean shooterDead() {
		return shooterDead();
	}
	
	//Moves the playerView to correspond to the (x, y) location of the player
	//Use any time the location changes to keep the position of the 
	//image updated
	private void locateImage() {
		playerView.relocate(x, y);
	}
	
	//return left side of player
	public double getX() {
		return x;
	}
	
	//set the left side of the player
	public void setX(double x) {
		this.x = x;
		locateImage();
	}
	
	//return the top side of the player
	public double getY() {
		return y;
	}
	
	//set top edge of player
	public void setY(double y) {
		this.y = y;
		locateImage();
	}
	
	//return the PLAYER_PIC
	public static Image getPlayerPic() {
		return PLAYER_PIC;
	}
	
	//return the playerView
	public ImageView getPlayerView() {
		return playerView;
	}
	
	//return the width
	public double getWidth() {
		return width;
	}
	
	//return the height
	public double getHeight() {
		return height;
	}

	public void moveLeft() {
		//change the player image to face left
		playerView.setImage(PLAYER_PIC_WEST);
		facing = WEST;

		x -= 1.5;
		locateImage();
	}

	public void moveRight() {
		//change the player image to face left
		playerView.setImage(PLAYER_PIC);
		facing = EAST;

		x += 1.5;
		locateImage();
	}
	
	public void moveDown() {
		y += 1.5;
		locateImage();
	}

	public void moveUp() {
		y -= 1.5;
		locateImage();
	}
	
	// kill the shooter by making it explode
	public void killed() {
		shooterDead = false;
		//Set the Dead image
		playerView.setImage(deadPlayer);
		playerView.setFitWidth(100);
		playerView.setFitHeight(175);
	}
	
	//creates more accurate boundaries for the shooter
	public Bounds getObjectBounds() {
		Rectangle rect = new Rectangle(x + 15, y, width - 35, height);
		return rect.getBoundsInParent();
		}

	//uses bullet class to change direction of bullet
	//dependant on which direction player is facing
	public gunBullet shoot() {
		gunBullet b; 

		if (facing == EAST) {
			b = new gunBullet(x + width/2 + 50, y + 40, EAST);
		}
		else {
			b = new gunBullet(x + width/2 - 65, y + 40, WEST);
		}

		return b;
	}
}
