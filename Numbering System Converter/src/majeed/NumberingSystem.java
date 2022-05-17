/**
 * 
 */
package majeed;

import java.text.DecimalFormat;
import java.util.Optional;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * @author aidan
 *
 */
public class NumberingSystem extends Application {

	private TextField CentimetreField = new TextField();
	private TextField MetreField = new TextField();
	private TextField KilometreField = new TextField();


	public void start(Stage primaryStage) throws Exception {
		Pane root = new Pane();
		Scene scene = new Scene(root,640,415);	
		primaryStage.setTitle("Metric Unit Converter");
		root.setBackground(null);


		//**Title for the conversion program
		Label lbltitle = new Label();
		lbltitle.setText("Metric Unit Converter");
		lbltitle.setPrefSize(400, 40);
		lbltitle.setAlignment(Pos.CENTER);
		lbltitle.setLayoutX(root.getWidth() / 2 - 400 / 2);
		lbltitle.setLayoutY(30);
		lbltitle.setStyle("-fx-font: bold 40 'ink free';");


		//**Buttons for each text box, in which it identifies which metric unit is to be put in.
		RadioButton cm = new RadioButton();
		cm.setText("CENTIMETRES");
		cm.setPrefSize(135, 40);
		cm.setLayoutX(220);
		cm.setLayoutY(125);
		cm.setStyle("-fx-font: 15 'Arial';");

		RadioButton m = new RadioButton();
		m.setText("METRES");
		m.setPrefSize(135, 40);
		m.setLayoutX(220);
		m.setLayoutY(185);
		m.setStyle("-fx-font: 15 'Arial';");

		RadioButton km = new RadioButton();
		km.setText("KILOMETRES");
		km.setPrefSize(135, 40);
		km.setLayoutX(220);
		km.setLayoutY(245);
		km.setStyle("-fx-font: 15 'Arial';");


		ToggleGroup group = new ToggleGroup();

		cm.setToggleGroup(group);

		m.setToggleGroup(group);

		km.setToggleGroup(group);


		//Buttons for convert, clear, and exit.
		Button cmd =new Button();
		cmd.setText("CONVERT");
		cmd.setPrefSize(150, 40);
		cmd.setLayoutX(250);
		cmd.setLayoutY(320);
		cmd.setStyle("-fx-font: 15 'Arial';");

		Button clr =new Button();
		clr.setText("CLEAR");
		clr.setPrefSize(150, 40);
		clr.setLayoutX(415);
		clr.setLayoutY(320);
		clr.setStyle("-fx-font: 15 'Arial';");

		Button exit = new Button("EXIT");
		exit.setPrefSize(150, 40);
		exit.setStyle("-fx-font: 15 'Arial';");
		exit.setLayoutX(85);
		exit.setLayoutY(320);


		//**Text fields for the each metric unit**


		//--Centimetre--
		CentimetreField.setPrefSize(235, 40);
		CentimetreField.setLayoutX(380);
		CentimetreField.setLayoutY(125);
		CentimetreField.setFont(Font.font("", FontWeight.BOLD,16));

		//Makes Invalid text red
		CentimetreField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					CentimetreField.setStyle("-fx-text-fill: red");
				}
				else {
					CentimetreField.setStyle("-fx-text-fill: Black");
				}}});



		//--Metre--
		MetreField.setPrefSize(235, 40);
		MetreField.setLayoutX(380);
		MetreField.setLayoutY(185);
		MetreField.setFont(Font.font("", FontWeight.BOLD,16));

		//Makes Invalid text red.
		MetreField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					MetreField.setStyle("-fx-text-fill: red");
				}
				else {
					MetreField.setStyle("-fx-text-fill: Black");
				}}});



		//--Kilometre--
		KilometreField.setPrefSize(235, 40);
		KilometreField.setLayoutX(380);
		KilometreField.setLayoutY(245);
		KilometreField.setFont(Font.font("", FontWeight.BOLD,16));

		//Makes Invalid text red.
		KilometreField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					KilometreField.setStyle("-fx-text-fill: red");
				}
				else{
					KilometreField.setStyle("-fx-text-fill: Black");
				}}});


		//Image
		Pane gif = new Pane();
		Image image1 = new Image ("file:images//apple.gif", 200, 200, false, false);
		gif.getChildren().add(new ImageView(image1));
		gif.setLayoutX(48);
		gif.setLayoutY(105);
		gif.setStyle("-fx-effect: dropshadow(three-pass-box, black, 15, 0, 0, 0);");


		//**Set Conversion Action commands


		//**CM to M
		cmd.setOnAction(e -> {
			if(cm.isSelected()) {
				{
					//Creates Alert when invalid text is in the field.
					double toNum;
					try {
						toNum=Double.parseDouble(CentimetreField.getText());
					}
					catch (Exception except) {

						Alert alert1 = new Alert(AlertType.ERROR);
						alert1.setTitle("Error!");
						alert1.setHeaderText(null);
						alert1.setContentText("Invalid symbols. Please Use Numbers.");
						alert1.showAndWait();

						return;
					}			
					double metreResult = toNum/100;
					MetreField.setText("" + new DecimalFormat("#####.#####").format(metreResult)+" M");
					MetreField.setStyle("-fx-text-fill: Black");
				}}

			//**CM to KM
			if(cm.isSelected()) {
				{
					double toNum = Double.parseDouble(CentimetreField.getText());			
					double KilometreResult = toNum/100000;
					KilometreField.setText("" + new DecimalFormat("#####.#####").format(KilometreResult)+" KM");
					KilometreField.setStyle("-fx-text-fill: Black");
				}}	


			//**M to CM
			if(m.isSelected()) {
				{
					//Creates Alert when invalid text is in the field.
					double toNum;
					try {
						toNum=Double.parseDouble(MetreField.getText());
					}
					catch (Exception except) {

						Alert alert2 = new Alert(AlertType.ERROR);
						alert2.setTitle("Error!");
						alert2.setHeaderText(null);
						alert2.setContentText("Invalid symbols. Please Use Numbers.");
						alert2.showAndWait();

						return;
					}				
					double centimetreResult = toNum*100;
					CentimetreField.setText("" + new DecimalFormat("#####.#####").format(centimetreResult)+" CM");
					CentimetreField.setStyle("-fx-text-fill: Black");
				}}

			//**M to KM
			if(m.isSelected()) {
				{
					double toNum = Double.parseDouble(MetreField.getText());			
					double KilometreResult = toNum/1000;
					KilometreField.setText("" + new DecimalFormat("#####.#####").format(KilometreResult)+" KM");
					KilometreField.setStyle("-fx-text-fill: Black");
				}}	


			//**KM to CM
			if(km.isSelected()) {
				{
					//Creates Alert when invalid text is in the field.
					double toNum;
					try {
						toNum=Double.parseDouble(KilometreField.getText());
					}
					catch (Exception except) {

						Alert alert3 = new Alert(AlertType.ERROR);
						alert3.setTitle("Error!");
						alert3.setHeaderText(null);
						alert3.setContentText("Invalid symbols. Please Use Numbers.");

						alert3.showAndWait();

						return;
					}			
					double centimetreResult = toNum*100000;
					CentimetreField.setText("" + new DecimalFormat("#####.#####").format(centimetreResult)+" CM");
					CentimetreField.setStyle("-fx-text-fill: Black");

				}}

			//**KM to M
			if(km.isSelected()) {
				{
					double toNum = Double.parseDouble(KilometreField.getText());			
					double metreResult = toNum*1000;
					MetreField.setText("" + new DecimalFormat("#####.#####").format(metreResult)+" KM");
					MetreField.setStyle("-fx-text-fill: Black");
				}}				
		});


		//Information message before program starts.
		try
		{
			double val1 = Double.parseDouble(CentimetreField.getText());
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "If the text is red, letters have been typed. Please use numbers!", 
					"Before You Continue...", JOptionPane.INFORMATION_MESSAGE);

			CentimetreField.setText("");
			CentimetreField.requestFocus();
		}


		//**Clear Button action commands
		clr.setOnAction(e -> {
			CentimetreField.clear();
			MetreField.clear();
			KilometreField.clear();
		});


		//***Exit Button action commands
		exit.setOnAction(e -> {
			Alert close = new Alert(AlertType.CONFIRMATION);
			close.setTitle("Exit");
			close.setHeaderText(null);
			close.setContentText("Are your sure you want to leave?");
			close.getButtonTypes().clear();
			close.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = close.showAndWait();

			if (result.get() == ButtonType.YES){
				Stage stage = (Stage) exit.getScene().getWindow();
				stage.close();
				if (result.get() == ButtonType.NO)
				{
					e.consume();
				}}});


		//**Action commands to make the text fields disabled when a conversion is being made.
		cm.setOnAction(e -> {
			if(cm.isSelected()) {
				CentimetreField.setEditable(true);
				MetreField.setEditable(false);
				KilometreField.setEditable(false);
				CentimetreField.setDisable(false);
				MetreField.setDisable(true);
				KilometreField.setDisable(true);

			}
		});

		m.setOnAction(e -> {	
			if(m.isSelected()) {
				MetreField.setEditable(true);
				CentimetreField.setEditable(false);
				KilometreField.setEditable(false);
				MetreField.setDisable(false);
				CentimetreField.setDisable(true);
				KilometreField.setDisable(true);
			}
		});

		km.setOnAction(e -> {
			if(km.isSelected()) {
				KilometreField.setEditable(true);
				CentimetreField.setEditable(false);
				MetreField.setEditable(false);
				KilometreField.setDisable(false);
				CentimetreField.setDisable(true);
				MetreField.setDisable(true);
			}
		});


		root.getChildren().addAll(lbltitle,gif);
		root.getChildren().addAll(cm,m,km,cmd,clr,exit);
		root.getChildren().addAll(CentimetreField,MetreField,KilometreField);
		primaryStage.setScene(scene);
		primaryStage.show();

	}


	public static void main(String[] args) {
		launch(args);
	}
}
