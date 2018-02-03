package com.bytingbulldogs.team3539;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class About
{
	public Stage about;
	public double versionNum;
	Label versionLabel;

	public About()
	{

		////////////////////////////////////////////////////////////////////
		// Start of the About window
		////////////////////////////////////////////////////////////////////
		about = new Stage();
		about.setTitle("About");
		about.hide();

		GridPane AboutPane = new GridPane();
		AboutPane.setHgap(5);
		AboutPane.setVgap(5);

		versionLabel = new Label("Version: " + versionNum);
		Label creator = new Label("Creator: Cameron Coesens (Team 3539)");
		Label contact = new Label("Email:    bbprogrammers3539@gmail.com");

		GridPane.setConstraints(versionLabel, 8, 10);
		AboutPane.add(versionLabel, 8, 10);
		GridPane.setConstraints(creator, 8, 11);
		AboutPane.add(creator, 8, 11);
		GridPane.setConstraints(contact, 8, 12);
		AboutPane.add(contact, 8, 12);

		Scene sceneAbout = new Scene(AboutPane);
		about.setScene(sceneAbout);

		about.setResizable(false);

		about.setHeight(250);
		about.setWidth(350);
		
		about.getIcons().add(new Image(Main.class.getResourceAsStream("download.png")));

		////////////////////////////////////////////////////////////////////
		// End of the About window
		////////////////////////////////////////////////////////////////////
	}

	public void setVersion(double version)
	{
		versionNum = version;
		versionLabel.setText("Version: " + versionNum);
	}

	public void showAbout()
	{
		about.setIconified(false);
		about.show();
	}

	public void closeAbout()
	{
		about.close();
	}
	public void hideAbout()
	{
		about.hide();
	}
}
