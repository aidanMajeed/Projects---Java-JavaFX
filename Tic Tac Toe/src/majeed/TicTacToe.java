/**
 * 
 */
package majeed;

import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/**
 * @author aidan
 * 
 *@version 2021-03-22
 *
 *A simple program that allows the user to play the game Tic Tac Toe.
 *
 */
public class TicTacToe extends Application {

	//Class Variables -----------------------------------------------
	private	Button[][] spaces = new Button[3][3];
	private Label whosTurn = new Label("Turn: X"
			+"\nOverall Score To Win: 15");
	private Label lblXWins = new Label("0");
	private Label lblOWins = new Label("0");
	private Label lblTies = new Label("0");
	private Button newGame = new Button("New Game");
	private String currentPlayer = "X";
	private int xWinCount = 0;
	private int oWinCount = 0;
	private int tieCount = 0;
	private boolean xTurn;

	//Class Methods -------------------------------------------------
	/**
	 * Creates the User Interface and sets up the first game
	 *
	 * Sets up the user interface and control methods for buttons.
	 * @param primarStage is reference to the main application window
	 * 
	 */
	
	public void start(Stage primaryStage) throws Exception {
		TilePane board = new TilePane();
		board.setPadding(new Insets(20));
		board.setVgap(20);
		board.setHgap(20);
		board.setPrefColumns(3);
		board.setMaxSize(380, 380);
		board.setMinSize(380, 380);
		board.setStyle("-fx-background-color: indigo ;");

		for (int row = 0; row < spaces.length; row++) {
			for (int col = 0; col < spaces[0].length; col++) {
				//Create the button
				spaces[col][row] = new Button();

				//Style the Button
				spaces[col][row].setPrefSize(100, 100);
				spaces[col][row].setStyle("-fx-background-color: plum;");

				//Add to the TilePane
				board.getChildren().add(spaces[col][row]);

				//Setup the Action Listener
				final int c = col;
				final int r = row;
				spaces[col][row].setOnAction(e -> spaceClicked(c, r));
			}
		}

		primaryStage.setTitle("Tic Tac Toe");

		TitledPane xWins = new TitledPane("X Wins", lblXWins);
		xWins.setCollapsible(false);
		xWins.setPrefSize(100, 80);


		TitledPane oWins = new TitledPane("O Wins", lblOWins);
		oWins.setCollapsible(false);
		oWins.setPrefSize(100, 80);

		TitledPane ties = new TitledPane("Ties", lblTies);
		ties.setCollapsible(false);
		ties.setPrefSize(100, 80);

		whosTurn.setFont(Font.font("Playbill", FontWeight.BOLD,30));

		Button reset = new Button("Reset");
		reset.setPrefSize(190, 25);
		reset.setOnAction(e -> reset());

		newGame.setPrefSize(190, 25);
		newGame.setOnAction(e -> newGame());

		VBox stats = new VBox();
		stats.setPadding(new Insets(10));
		stats.setSpacing(10);
		stats.getChildren().addAll(whosTurn, xWins, oWins, ties, newGame, reset);

		GridPane root = new GridPane();
		root.add(board, 0, 0);
		root.add(stats, 1, 0);

		Scene scn = new Scene(root);
		primaryStage.setScene(scn);
		primaryStage.setResizable(false);
		primaryStage.show();		
	}

	/**
	 * When a space on the board is clicked the program will:
	 * 
	 * Check to see if the space is empty, before allowing the current
	 * player to claim that space
	 * 
	 * Check to see if playing this space has won the game for the player
	 * 	  Display a message and update the score if there is a winner
	 * 
	 * Check to see if playing this space has made the game a draw
	 * 	  Display a message and update the score board if it is a draw
	 *  
	 * Update the current player (e.g. after X plays it is O's turn)
	 * 
	 * @param col column number of the space that was clicked
	 * @param row row number of the space that was clicked
	 */
	public void spaceClicked(int col, int row) {
		//For turns to swap, as well as output X and O.
		spaces[col][row].setFont(Font.font("Ink Free", FontWeight.EXTRA_BOLD, 40));

		if (spaces[col][row].getText().equals("")) 
		{
			if (xTurn == false)
			{
				spaces[col][row].setText("X");
				xTurn = true;
				whosTurn.setText("Turn: O"
						+ "\nOverall Score To Win: 15");
				spaces[col][row].setTextFill(Color.RED);

			}
			else

			{
				spaces[col][row].setText("O");
				xTurn = false;
				whosTurn.setText("Turn: X"
						+ "\nOverall Score To Win: 15");
				spaces[col][row].setTextFill(Color.BLUE);

			}
		}
		//to demonstrate that there has been a draw
		if (isWinner(currentPlayer)){

		}
		else if(isBoardFull()) {
			tieCount++;
			gameScore();

			//alert to notify players of a tie.
			Alert tieAlert = new Alert(AlertType.INFORMATION);
			tieAlert.setTitle("Uh Oh");
			tieAlert.setHeaderText("It's a Tie!"
					+ "\nPress 'OK' to continue!");
			tieAlert.setContentText(null);
			tieAlert.showAndWait();
			
			spaces[0][0].setDisable(true);
			spaces[0][1].setDisable(true);
			spaces[0][2].setDisable(true);

			spaces[1][0].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[1][2].setDisable(true);

			spaces[2][0].setDisable(true);
			spaces[2][1].setDisable(true);
			spaces[2][2].setDisable(true);
		}
		if (xTurn) { currentPlayer = "X"; }
		else { currentPlayer = "O"; }

		//overall win score alert
		if (xWinCount ==15){

			newGame.setDisable(true);

			Alert gameOver = new Alert(AlertType.INFORMATION);
			gameOver.setTitle("Winner Winner Chicken Dinner");
			gameOver.setHeaderText("X Wins this game!"
					+ "\nIf you want to play again, please reset the game.");
			gameOver.setContentText("Thank you for choosing Bell!");
			Image emojiYAY = new Image("file:images\\yay.png", 
					100,75,false, false);
			ImageView imageView = new ImageView();
			imageView.setImage(emojiYAY);
			gameOver.setGraphic(imageView);
			gameOver.setContentText(null);
			gameOver.showAndWait();
		}

		if (oWinCount ==15){

			newGame.setDisable(true);

			Alert gameOver = new Alert(AlertType.INFORMATION);
			gameOver.setTitle("Winner Winner Chicken Dinner!");
			gameOver.setHeaderText("O Wins this game!"
					+ "\nIf you want to play again, please reset the game.");
			gameOver.setContentText("Thank you for choosing Bell!");
			Image emojiYAY = new Image("file:images\\yay.png", 
					100,75,false, false);
			ImageView imageView = new ImageView();
			imageView.setImage(emojiYAY);
			gameOver.setGraphic(imageView);
			gameOver.setContentText(null);
			gameOver.showAndWait();
		}
	}		



	/**
	 * Test to see if all spaces are taken.  If there is no winner, and the
	 * board is full, the game is a draw.
	 * 
	 * @return false if there is any empty spaces, true otherwise.
	 */
	public boolean isBoardFull() {
		for (int row = 0; row < spaces.length; row++) {
			for (int col = 0; col < spaces.length; col++) {
				if(spaces[col][row].getText().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	private void gameScore()
	{
		lblXWins.setText(String.valueOf(xWinCount));
		lblOWins.setText(String.valueOf(oWinCount));
		lblTies.setText(String.valueOf(tieCount));
	}


	/**
	 * Test to see if the given player has won the game by getting three
	 * tokens in a row horizontally, vertically or diagonally
	 * 
	 * @param player the symbol of the player ("X" or "O") to test
	 * 
	 * @return true if "player" wins, false otherwise
	 */
	public boolean isWinner(String player) {
		String selec1 = spaces[0][0].getText();
		String selec2 = spaces[0][1].getText();
		String selec3 = spaces[0][2].getText();

		String selec4 = spaces[1][0].getText();
		String selec5 = spaces[1][1].getText();
		String selec6 = spaces[1][2].getText();

		String selec7 = spaces[2][0].getText();
		String selec8 = spaces[2][1].getText();
		String selec9 = spaces[2][2].getText();

		//If Player X ends up as the winner.
		if(selec1 == ("X") && selec2 ==("X") && selec3 == ("X"))
		{
			xWinCount++;
			gameScore();

			spaces[0][0].setStyle("-fx-background-color: lime;");
			spaces[0][1].setStyle("-fx-background-color: lime;");
			spaces[0][2].setStyle("-fx-background-color: lime;");


			spaces[1][0].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[1][2].setDisable(true);

			spaces[2][0].setDisable(true);
			spaces[2][1].setDisable(true);
			spaces[2][2].setDisable(true);

			//alert to congratulate the winner
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("CONGRATS!!");
			winner.setHeaderText("X wins!"
					+ "\nPress 'OK' to continue!");
			winner.setContentText(null);
			winner.showAndWait();


			return true;
		}


		if(selec4 == ("X") 
				&& selec5 ==("X") 
				&& selec6 == ("X"))
		{
			xWinCount++;
			gameScore();

			spaces[1][0].setStyle("-fx-background-color: lime;");
			spaces[1][1].setStyle("-fx-background-color: lime;");
			spaces[1][2].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[0][1].setDisable(true);
			spaces[0][2].setDisable(true);

			spaces[2][0].setDisable(true);
			spaces[2][1].setDisable(true);
			spaces[2][2].setDisable(true);


			//alert to congratulate the winner
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("CONGRATS!!");
			winner.setHeaderText("X wins!"
					+ "\nPress 'OK' to continue!");
			winner.setContentText(null);
			winner.showAndWait();


			return true;

		}

		if(selec7 == ("X") 
				&& selec8 ==("X") 
				&& selec9 == ("X"))
		{
			xWinCount++;
			gameScore();

			spaces[2][0].setStyle("-fx-background-color: lime;");
			spaces[2][1].setStyle("-fx-background-color: lime;");
			spaces[2][2].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[0][1].setDisable(true);
			spaces[0][2].setDisable(true);

			spaces[1][0].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[1][2].setDisable(true);

			//alert to congratulate the winner
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("CONGRATS!!");
			winner.setHeaderText("X wins!"
					+ "\nPress 'OK' to continue!");
			winner.setContentText(null);
			winner.showAndWait();

			return true;
		}

		if(selec1 == ("X") 
				&& selec4 ==("X") 
				&& selec7 == ("X"))
		{
			xWinCount++;
			gameScore();

			spaces[0][0].setStyle("-fx-background-color: lime;");
			spaces[1][0].setStyle("-fx-background-color: lime;");
			spaces[2][0].setStyle("-fx-background-color: lime;");

			spaces[0][1].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[2][1].setDisable(true);

			spaces[0][2].setDisable(true);
			spaces[1][2].setDisable(true);
			spaces[2][2].setDisable(true);


			//alert to congratulate the winner
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("CONGRATS!!");
			winner.setHeaderText("X wins!"
					+ "\nPress 'OK' to continue!");
			winner.setContentText(null);
			winner.showAndWait();

			return true;
		}

		if(selec2 == ("X") 
				&& selec5 ==("X") 
				&& selec8 == ("X"))
		{
			xWinCount++;
			gameScore();

			spaces[0][1].setStyle("-fx-background-color: lime;");
			spaces[1][1].setStyle("-fx-background-color: lime;");
			spaces[2][1].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[1][0].setDisable(true);
			spaces[2][0].setDisable(true);

			spaces[0][2].setDisable(true);
			spaces[1][2].setDisable(true);
			spaces[2][2].setDisable(true);

			//alert to congratulate the winner
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("CONGRATS!!");
			winner.setHeaderText("X wins!"
					+ "\nPress 'OK' to continue!");
			winner.setContentText(null);
			winner.showAndWait();

			return true;
		}
		if(selec3 == ("X") 
				&& selec6 ==("X") 
				&& selec9 == ("X"))
		{
			xWinCount++;
			gameScore();

			spaces[0][2].setStyle("-fx-background-color: lime;");
			spaces[1][2].setStyle("-fx-background-color: lime;");
			spaces[2][2].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[1][0].setDisable(true);
			spaces[2][0].setDisable(true);

			spaces[0][1].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[2][1].setDisable(true);

			//alert to congratulate the winner
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("CONGRATS!!");
			winner.setHeaderText("X wins!"
					+ "\nPress 'OK' to continue!");
			winner.setContentText(null);
			winner.showAndWait();

			return true;
		}


		if(selec1 == ("X") 
				&& selec5 ==("X") 
				&& selec9 == ("X"))
		{
			xWinCount++;
			gameScore();

			spaces[0][0].setStyle("-fx-background-color: lime;");
			spaces[1][1].setStyle("-fx-background-color: lime;");
			spaces[2][2].setStyle("-fx-background-color: lime;");

			spaces[0][1].setDisable(true);
			spaces[0][2].setDisable(true);

			spaces[1][0].setDisable(true);
			spaces[1][2].setDisable(true);

			spaces[2][0].setDisable(true);
			spaces[2][1].setDisable(true);

			//alert to congratulate the winner
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("CONGRATS!!");
			winner.setHeaderText("X wins!"
					+ "\nPress 'OK' to continue!");
			winner.setContentText(null);
			winner.showAndWait();

			return true;
		}

		if(selec3 == ("X") 
				&& selec5 ==("X") 
				&& selec7 == ("X"))
		{
			xWinCount++;
			gameScore();

			spaces[0][2].setStyle("-fx-background-color: lime;");
			spaces[1][1].setStyle("-fx-background-color: lime;");
			spaces[2][0].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[0][1].setDisable(true);

			spaces[1][0].setDisable(true);
			spaces[1][2].setDisable(true);

			spaces[2][1].setDisable(true);
			spaces[2][2].setDisable(true);

			//alert to congratulate the winner
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("CONGRATS!!");
			winner.setHeaderText("X wins!"
					+ "\nPress 'OK' to continue!");
			winner.setContentText(null);
			winner.showAndWait();

			return true;
		}

		//If Player O ends up as the winner.
		if(selec1 == ("O") 
				&& selec2 ==("O") 
				&& selec3 == ("O"))
		{
			oWinCount++;
			gameScore();

			spaces[0][0].setStyle("-fx-background-color: lime;");
			spaces[0][1].setStyle("-fx-background-color: lime;");
			spaces[0][2].setStyle("-fx-background-color: lime;");

			spaces[1][0].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[1][2].setDisable(true);

			spaces[2][0].setDisable(true);
			spaces[2][1].setDisable(true);
			spaces[2][2].setDisable(true);

			//alert to congratulate the winner
			Alert winnerO = new Alert(AlertType.INFORMATION);
			winnerO.setTitle("CONGRATS!!");
			winnerO.setHeaderText("O wins!"
					+ "\nPress 'OK' to continue!");
			winnerO.setContentText(null);
			winnerO.showAndWait();

			return true;

		}

		if(selec4 == ("O") 
				&& selec5 ==("O") 
				&& selec6 == ("O"))
		{
			oWinCount++;
			gameScore();

			spaces[1][0].setStyle("-fx-background-color: lime;");
			spaces[1][1].setStyle("-fx-background-color: lime;");
			spaces[1][2].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[0][1].setDisable(true);
			spaces[0][2].setDisable(true);

			spaces[2][0].setDisable(true);
			spaces[2][1].setDisable(true);
			spaces[2][2].setDisable(true);


			//alert to congratulate the winner
			Alert winnerO = new Alert(AlertType.INFORMATION);
			winnerO.setTitle("CONGRATS!!");
			winnerO.setHeaderText("O wins!"
					+ "\nPress 'OK' to continue!");
			winnerO.setContentText(null);
			winnerO.showAndWait();

			return true;
		}

		if(selec7 == ("O") 
				&& selec8 ==("O") 
				&& selec9 == ("O"))
		{
			oWinCount++;
			gameScore();

			spaces[2][0].setStyle("-fx-background-color: lime;");
			spaces[2][1].setStyle("-fx-background-color: lime;");
			spaces[2][2].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[0][1].setDisable(true);
			spaces[0][2].setDisable(true);

			spaces[1][0].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[1][2].setDisable(true);

			//alert to congratulate the winner
			Alert winnerO = new Alert(AlertType.INFORMATION);
			winnerO.setTitle("CONGRATS!!");
			winnerO.setHeaderText("O wins!"
					+ "\nPress 'OK' to continue!");
			winnerO.setContentText(null);
			winnerO.showAndWait();

			return true;
		}

		if(selec1 == ("O") 
				&& selec4 ==("O") 
				&& selec7 == ("O"))
		{
			oWinCount++;
			gameScore();

			spaces[0][0].setStyle("-fx-background-color: lime;");
			spaces[1][0].setStyle("-fx-background-color: lime;");
			spaces[2][0].setStyle("-fx-background-color: lime;");

			spaces[0][1].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[2][1].setDisable(true);

			spaces[0][2].setDisable(true);
			spaces[1][2].setDisable(true);
			spaces[2][2].setDisable(true);


			//alert to congratulate the winner
			Alert winnerO = new Alert(AlertType.INFORMATION);
			winnerO.setTitle("CONGRATS!!");
			winnerO.setHeaderText("O wins!"
					+ "\nPress 'OK' to continue!");
			winnerO.setContentText(null);
			winnerO.showAndWait();

			return true;
		}

		if(selec2 == ("O") 
				&& selec5 ==("O") 
				&& selec8 == ("O"))
		{
			oWinCount++;
			gameScore();

			spaces[0][1].setStyle("-fx-background-color: lime;");
			spaces[1][1].setStyle("-fx-background-color: lime;");
			spaces[2][1].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[1][0].setDisable(true);
			spaces[2][0].setDisable(true);

			spaces[0][2].setDisable(true);
			spaces[1][2].setDisable(true);
			spaces[2][2].setDisable(true);

			//alert to congratulate the winner
			Alert winnerO = new Alert(AlertType.INFORMATION);
			winnerO.setTitle("CONGRATS!!");
			winnerO.setHeaderText("O wins!"
					+ "\nPress 'OK' to continue!");
			winnerO.setContentText(null);
			winnerO.showAndWait();

			return true;         
		}
		if(selec3 == ("O") 
				&& selec6 ==("O") 
				&& selec9 == ("O"))
		{
			oWinCount++;
			gameScore();

			spaces[0][2].setStyle("-fx-background-color: lime;");
			spaces[1][2].setStyle("-fx-background-color: lime;");
			spaces[2][2].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[1][0].setDisable(true);
			spaces[2][0].setDisable(true);

			spaces[0][1].setDisable(true);
			spaces[1][1].setDisable(true);
			spaces[2][1].setDisable(true);

			//alert to congratulate the winner
			Alert winnerO = new Alert(AlertType.INFORMATION);
			winnerO.setTitle("CONGRATS!!");
			winnerO.setHeaderText("O wins!"
					+ "\nPress 'OK' to continue!");
			winnerO.setContentText(null);
			winnerO.showAndWait();

			return true;
		}


		if(selec1 == ("O") 
				&& selec5 ==("O") 
				&& selec9 == ("O"))
		{
			oWinCount++;
			gameScore();

			spaces[0][0].setStyle("-fx-background-color: lime;");
			spaces[1][1].setStyle("-fx-background-color: lime;");
			spaces[2][2].setStyle("-fx-background-color: lime;");

			spaces[0][1].setDisable(true);
			spaces[0][2].setDisable(true);

			spaces[1][0].setDisable(true);
			spaces[1][2].setDisable(true);

			spaces[2][0].setDisable(true);
			spaces[2][1].setDisable(true);

			//alert to congratulate the winner
			Alert winnerO = new Alert(AlertType.INFORMATION);
			winnerO.setTitle("CONGRATS!!");
			winnerO.setHeaderText("O wins!"
					+ "\nPress 'OK' to continue!");
			winnerO.setContentText(null);
			winnerO.showAndWait();

			return true;
		}

		if(selec3 == ("O") 
				&& selec5 ==("O") 
				&& selec7 == ("O"))
		{
			oWinCount++;
			gameScore();

			spaces[0][2].setStyle("-fx-background-color: lime;");
			spaces[1][1].setStyle("-fx-background-color: lime;");
			spaces[2][0].setStyle("-fx-background-color: lime;");

			spaces[0][0].setDisable(true);
			spaces[0][1].setDisable(true);

			spaces[1][0].setDisable(true);
			spaces[1][2].setDisable(true);

			spaces[2][1].setDisable(true);
			spaces[2][2].setDisable(true);

			//alert to congratulate the winner
			Alert winnerO = new Alert(AlertType.INFORMATION);
			winnerO.setTitle("CONGRATS!!");
			winnerO.setHeaderText("O wins!"
					+ "\nPress 'OK' to continue!");
			winnerO.setContentText(null);
			winnerO.showAndWait();

			return true;
		}
		return false;

	}


	/**
	 * Clear the board to start a new game.  Scores from previous complete
	 * games are NOT erased
	 */
	public void newGame() {
		
		Alert gameNew = new Alert(AlertType.CONFIRMATION);
		gameNew.setTitle("New Game");
		gameNew.setHeaderText(null);
		gameNew.setContentText("Are you sure you want to start a new game?");
		gameNew.getButtonTypes().clear();
		gameNew.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result1 = gameNew.showAndWait();

		//If yes is pressed, a new game will start.
		if (result1.get() == ButtonType.YES) {
			
		//resets text
		spaces[0][0].setText("");
		spaces[0][1].setText("");
		spaces[0][2].setText("");

		spaces[1][0].setText("");
		spaces[1][1].setText("");
		spaces[1][2].setText("");

		spaces[2][0].setText("");
		spaces[2][1].setText("");
		spaces[2][2].setText("");

		//re-enables buttons
		spaces[0][0].setDisable(false);
		spaces[0][1].setDisable(false);
		spaces[0][2].setDisable(false);

		spaces[1][0].setDisable(false);
		spaces[1][1].setDisable(false);
		spaces[1][2].setDisable(false);

		spaces[2][0].setDisable(false);
		spaces[2][1].setDisable(false);
		spaces[2][2].setDisable(false);

		//puts buttons back to original color
		spaces[0][0].setStyle("-fx-background-color: plum;");
		spaces[0][1].setStyle("-fx-background-color: plum;");
		spaces[0][2].setStyle("-fx-background-color: plum;");

		spaces[1][0].setStyle("-fx-background-color: plum;");
		spaces[1][1].setStyle("-fx-background-color: plum;");
		spaces[1][2].setStyle("-fx-background-color: plum;");

		spaces[2][0].setStyle("-fx-background-color: plum;");
		spaces[2][1].setStyle("-fx-background-color: plum;");
		spaces[2][2].setStyle("-fx-background-color: plum;");

		//resets x to default turn
		xTurn = false;
		whosTurn.setText("Turn: X"
				+"\nOverall Score To Win: 15");
		}

		//If no, continue game.
		if (result1.get() == ButtonType.NO) {	
		}
		
	}

	/**
	 * Clear the board to start a new game.  Scores from all previous games are
	 * also reset to 0.
	 */
	public void reset() {
		
		Alert gameReset = new Alert(AlertType.CONFIRMATION);
		gameReset.setTitle("Reset");
		gameReset.setHeaderText(null);
		gameReset.setContentText("Are you sure you want to reset your game?"
				+"\nYour progress will not be saved.");
		gameReset.getButtonTypes().clear();
		gameReset.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> reset = gameReset.showAndWait();

		//when yes is selected, game resets
		if (reset.get() == ButtonType.YES) {
		
		//resets win counter
		lblXWins.setText("0");
		lblOWins.setText("0");
		lblTies.setText("0");

		//resets x to default turn
		xTurn = false;
		whosTurn.setText("Turn: X"
				+"\nOverall Score To Win: 15");

		//resets value of wins
		xWinCount = 0;
		oWinCount = 0;
		tieCount = 0;

		//resets text
		spaces[0][0].setText("");
		spaces[0][1].setText("");
		spaces[0][2].setText("");

		spaces[1][0].setText("");
		spaces[1][1].setText("");
		spaces[1][2].setText("");

		spaces[2][0].setText("");
		spaces[2][1].setText("");
		spaces[2][2].setText("");

		//re-enables buttons
		spaces[0][0].setDisable(false);
		spaces[0][1].setDisable(false);
		spaces[0][2].setDisable(false);

		spaces[1][0].setDisable(false);
		spaces[1][1].setDisable(false);
		spaces[1][2].setDisable(false);

		spaces[2][0].setDisable(false);
		spaces[2][1].setDisable(false);
		spaces[2][2].setDisable(false);

		//puts buttons back to original color
		spaces[0][0].setStyle("-fx-background-color: plum;");
		spaces[0][1].setStyle("-fx-background-color: plum;");
		spaces[0][2].setStyle("-fx-background-color: plum;");

		spaces[1][0].setStyle("-fx-background-color: plum;");
		spaces[1][1].setStyle("-fx-background-color: plum;");
		spaces[1][2].setStyle("-fx-background-color: plum;");

		spaces[2][0].setStyle("-fx-background-color: plum;");
		spaces[2][1].setStyle("-fx-background-color: plum;");
		spaces[2][2].setStyle("-fx-background-color: plum;");
		}
		
		//If no, continue game.
		if (reset.get() == ButtonType.NO) {	
		}
		
	}
	/**
	 * Launch the JavaFX application framework
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

