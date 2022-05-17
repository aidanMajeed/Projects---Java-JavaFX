/**
 * 
 */
package majeed;

/**
 * @author aidan
 *
 *A sample game that utilizes a series of arrays, images and classes. This program
 * creates a game that involves the player shooting zombies.
 *
 * Demonstrates a few fundamental game patterns:
 * 1. AnimationTimer use
 * 2. Using objects as game pieces (Zombie, Bullets, Shooter)
 * 3. Pausing the Timer with the keyboard and player death
 * 4. Simple collision check (Bullets killing zombies, Zombies killing player)
 * 
 * @version 2021-03-31
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class ZombieAttack extends Application {

	//CLASS VARIABLES -----------------------------------------------

	//A HashSet can store a list of values - in this case KeyCodes
	private final HashSet<KeyCode> keyboard = new HashSet<KeyCode>();

	//Variables that control the main game timer
	private AnimationTimer timer;
	private long previousTime = -1;
	private double elapsedTime = 0;

	private final int WALKERS_LIMIT = 16;
	private double coolDownPeriod = 0;

	//user interface elements
	private Pane root = new Pane();
	private Image gameDone = new Image("file:images\\gameOver!.png",
			450,200, false, false);
	private Image pause = new Image("file:images\\pause.png",
			100,100, false, false);
	private ImageView popUp = new ImageView();
	private ImageView pauseIcon = new ImageView();
	private Pane notif = new Pane();

	//game objects
	private shooter player = new shooter (350, 250); 
	private ArrayList<Zombie> walkerZombies = new ArrayList<Zombie>();
	private ArrayList<gunBullet> bullets = new ArrayList<gunBullet>(); 

	//Class Methods -----------------------------------------------
	public void start(Stage primaryStage) 
	{
		//add the background for the game
		Image setting = new Image("file:images\\zombie_game_background.png");
		ImageView iviewBackground = new ImageView(setting);
		iviewBackground.setLayoutX(0);
		iviewBackground.setLayoutY(0);
		root.getChildren().addAll(iviewBackground);


		//Add the player to the scene
		root.getChildren().addAll(player.getPlayerView());


		//Setup the initial array of zombies, with one on screen and the rest
		// waiting off screen to walk
		for(int i = 0; i < WALKERS_LIMIT; i++) {
			walkerZombies.add(new Zombie());
		} 

		Collections.sort(walkerZombies);

		for(Zombie walk : walkerZombies) {
			root.getChildren().add(walk.getView());
		}

		// Initialize the AnimationTimer object
		timer = new AnimationTimer() {
			public void handle(long currentTime) {
				if (!(previousTime == -1)) {

					//calculate elapsed time in seconds (not nanoseconds)
					elapsedTime = (currentTime - previousTime) / 1000000000.0; 
					update(elapsedTime);
				}
				//update for next iteration...	
				previousTime = currentTime; 				
			}
		};
		// Start the AnimationTimer
		timer.start();


		//Set the Stage and show it
		Scene scene = new Scene(root, 900, 675);
		primaryStage.setTitle("Zombie Attack!!");
		primaryStage.setScene(scene);
		primaryStage.show();
		root.setSnapToPixel(false);

		//set up for game over image
		popUp.setImage(gameDone);
		notif.setLayoutX(230);
		notif.setLayoutY(210);
		popUp.setVisible(false);
		root.getChildren().addAll(notif);
		notif.getChildren().addAll(popUp);
		
		//set up for pause image
		pauseIcon.setImage(pause);
		pauseIcon.setVisible(false);
		root.getChildren().addAll(pauseIcon);

		//Add input events so the program can respond
		scene.setOnKeyPressed(key -> keyPressed(key));
		scene.setOnKeyReleased(key -> keyReleased(key));


	} 

	private void walkerClean() {

		//look at each zombie to see if it is ready for removal
		for(int i = 0; i < walkerZombies.size(); i++) {
			if (walkerZombies.get(i).isReadyForCleanUp()) {
				root.getChildren().remove(walkerZombies.get(i).getView());
				walkerZombies.remove(i);
			}
		}
	}

	public void update(double elapsedTime) {
		//update all movable objects
		newWalker();
		moveWalker(elapsedTime);
		moveBullets(elapsedTime);

		//handle keyboard inputs
		keyboardInput();

		//check for collision
		checkIfPlayerDied();
		checkBulletCollisions();


		//Clean up
		walkerClean();
		cleanUpBullets();



		if (coolDownPeriod > 0) 
			coolDownPeriod -= elapsedTime;
	}

	//makes new zombies appear after it goes offscreen
	private void newWalker() {
		if (walkerZombies.size() < WALKERS_LIMIT) {

			for(Zombie walk : walkerZombies) {
				root.getChildren().remove(walk.getView());
			}

			walkerZombies.add(new Zombie());
			Collections.sort(walkerZombies);

			for(Zombie walk : walkerZombies) {
				root.getChildren().add(walk.getView());
			}
		}
	}

	//Makes zombies move
	private void moveWalker(double elapsedTime) {
		for(Zombie walker : walkerZombies) {
			walker.update(elapsedTime);
		}
	}

	/**
	 * Respond to key press events by checking for the pause key (P),
	 * and starting or stopping the game timer appropriately
	 * 
	 * Other key presses are stored in the "Keyboard" HashSet for polling
	 * during the main game update.
	 * 
	 * @param key KeyEvent program is responding to 
	 */
	public void keyPressed(KeyEvent key) {
		//Before checking if this is the pause key, make sure it's not
		// being held down...
		if (!keyboard.contains(KeyCode.P)) {
			if (key.getCode() == KeyCode.P) {
				//pause or restart the game timer
				if (previousTime == -1) {
					timer.start();
					pauseIcon.setVisible(false);
				}
				else {
					timer.stop();
					previousTime = -1;
					elapsedTime = 0;
					pauseIcon.setVisible(true);
				}
			}
		}
		//record this particular key has been pressed:
		keyboard.add(key.getCode());	
	}
	
	/**
	 * Removes a key from the "keyboard" HashSet when it is released
	 * 
	 * @param key KeyEvent that triggered this method call
	 */
	public void keyReleased(KeyEvent key) {
		//remove the record of the key being pressed:
		keyboard.remove(key.getCode());
	}

	private void keyboardInput() {
		//walk left key
		if (keyboard.contains(KeyCode.A)) {
			if (player.getX() >= 0) {
				player.moveLeft();
			}			
		}

		//walk right key
		if (keyboard.contains(KeyCode.D)) {
			if (player.getX() <= 812) {
				player.moveRight();
			}
		}

		//walk down key
		if (keyboard.contains(KeyCode.W)) {
			if 
			(player.getY() >= 0){
				player.moveUp();
			}			
		}

		//walk up key
		if (keyboard.contains(KeyCode.S)) {
			if (player.getY() <= 555) {
				player.moveDown();					}
		}

		//Key to fire
		if (keyboard.contains(KeyCode.SPACE)) {
			if (coolDownPeriod <= 0) {
				
				//new bullet should appear
				gunBullet b = player.shoot(); 

				root.getChildren().add(b.getView());
				bullets.add(b);

				//time between gun shots
				coolDownPeriod = 0.5;

			}
		}

		//Player can quit the game quickly by pressing escape
		if (keyboard.contains(KeyCode.ESCAPE)) {
			Platform.exit();
		}
	}

	//allows for bullets to move when key is pressed
	private void moveBullets(double elapsedTime) {
		for(gunBullet b: bullets) {
			b.move(elapsedTime);
		}
	}

	//checks if there has been a collision with a zombie and player\
	//kills player if there is contact
	private void checkIfPlayerDied() {
		for(Zombie walk : walkerZombies) {
			if (!walk.isDead()) {
				if (player.getObjectBounds().intersects(walk.
						getObjectBounds()))
{
					System.out.println("Player has come in contact with a Walker!");
					player.killed();

					//game pauses and ceases all movement
					timer.stop();

					//image stating "Game Over" when player has died
					popUp.setVisible(true);


				}
			}
		}		
	}

	//Check for collisions between bullets and zombies,
	//if one is detected clear the bullet and the zombie 
	private void checkBulletCollisions() {
		for (gunBullet b: bullets) {		//Compare every bullet
			for (Zombie c: walkerZombies) {	//  to every zombie
				if (!c.isDead()) {		//   but only if that player isn't dead already

					if (b.getBoundsInParent().intersects(
							c.getBoundsInParent())) {
						b.kill();
						c.kill();
					}
				}
			}
		}
	}

	
	 //Clean up bullets that have moved off screen or hit a zombie
	private void cleanUpBullets() {
		for(int i=0; i< bullets.size(); i++) {
			if (bullets.get(i).isReadyForCleanUp()) {
				//remove the bullet Image from the screen
				root.getChildren().remove(bullets.get(i).getView());
				//remove the bullet object from the list of bullets
				bullets.remove(bullets.get(i));
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
