package com.bytingbulldogs.team3539;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/*CHANGES 1.1
 * Add Smart Properties file checking system
 * Add profiles
 */
/*CHANGES 1.2
 * Improve Smart Properties file checking system
 * Add profile names
 * Add fading feature for Success Label to improve clarity between runs
 */

public class Main extends Application
{
	public String versionnum = "1.2";

	public Stage settings;
	public Stage about;
	private AnchorPane AnchorPaneSettings;
	Properties props;
	OutputStream output = null;
	FileInputStream in;
	String path;

	private FadeTransition fadeOut = new FadeTransition(Duration.millis(3000));

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage defaultStage) throws Exception
	{
		////////////////////////////////////////////////////////////////////
		// Begin Of Settings must come first
		////////////////////////////////////////////////////////////////////

		Button apply = new Button("Apply");
		Button save1 = new Button("Save Profile 1");
		Button load1 = new Button("Load Profile 1");

		Button save2 = new Button("Save Profile 2");
		Button load2 = new Button("Load Profile 2");

		Button save3 = new Button("Save Profile 3");
		Button load3 = new Button("Load Profile 3");

		Label connectionName = new Label("Connection Name:");
		Label MaskLabel = new Label("Mask Address: ");
		Label IPLabel = new Label("IP Address: ");
		Label gatewayLabel = new Label("Gateway Address: ");
		Label profLabel = new Label("Profile Name: ");

		TextField name = new TextField();
		TextField Mask = new TextField();
		TextField IP = new TextField();
		TextField gateway = new TextField();
		TextField profName = new TextField();

		name.setPromptText("Local Area Connection");
		Mask.setPromptText("255.0.0.0");
		IP.setPromptText("10.35.39.5");
		gateway.setPromptText("10.35.39.1");
		profName.setPromptText("3539");

		path = System.getProperty("user.home") + "\\documents\\FRCIPController";

		File directory = new File(path);
		File propfilecheck = new File(path + "\\config.properties");

		// "Smart Config"
		// This will check for content inside of the config.

		if (!directory.exists() || !propfilecheck.exists())
		{
			directory.mkdir();
			// If the file location does not exist, create the file path and the config file.
			// or if just the config file does not exist, create it.
			try
			{
				props = new Properties();
				output = new FileOutputStream(path + "\\config.properties");

				// the value xxx is not a "valid return"
				props.setProperty("Name_Current", "xxx");
				props.setProperty("IP_Current", "xxx");
				props.setProperty("Mask_Current", "xxx");
				props.setProperty("Gateway_Current", "xxx");

				props.setProperty("Name_Save1", "xxx");
				props.setProperty("IP_Save1", "xxx");
				props.setProperty("Mask_Save1", "xxx");
				props.setProperty("Gateway_Save1", "xxx");

				props.setProperty("Name_Save2", "xxx");
				props.setProperty("IP_Save2", "xxx");
				props.setProperty("Mask_Save2", "xxx");
				props.setProperty("Gateway_Save2", "xxx");

				props.setProperty("Name_Save3", "xxx");
				props.setProperty("IP_Save3", "xxx");
				props.setProperty("Mask_Save3", "xxx");
				props.setProperty("Gateway_Save3", "xxx");

				props.setProperty("ProfName_Current", "xxx");
				props.setProperty("ProfName_Save1", "xxx");
				props.setProperty("ProfName_Save2", "xxx");
				props.setProperty("ProfName_Save3", "xxx");

				props.store(output, null);
			}
			catch (IOException io)
			{
				io.printStackTrace();
			}
			finally
			{
				if (output != null)
				{
					try
					{
						output.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}

			}
		}
		else
		{
			// If the file does exist, load the config into the currently blank spaces in the settings menu.
			Properties prop = new Properties();
			in = new FileInputStream(path + "\\config.properties");
			prop.load(in);
			in.close();
			try
			{
				// detects whether there is a value
				if (!prop.get("Name_Current").equals("xxx"))
				{
					name.setText((String) prop.get("Name_Current"));
				}
				if (!prop.get("Mask_Current").equals("xxx"))
				{
					Mask.setText((String) prop.get("Mask_Current"));
				}
				if (!prop.get("IP_Current").equals("xxx"))
				{
					IP.setText((String) prop.get("IP_Current"));
				}
				if (!prop.get("Gateway_Current").equals("xxx"))
				{
					gateway.setText((String) prop.get("Gateway_Current"));
				}
				try
				{
					if (!prop.get("ProfName_Current").equals("xxx"))
					{
						profName.setText((String) prop.get("ProfName_Current"));
					}
				}
				// Converts an old config from version (1.1) to the newest version.
				catch (NullPointerException x2)
				{
					System.out.println("OLD CONFIG V1.1");
					props = new Properties();
					FileInputStream in = new FileInputStream(path + "\\config.properties");
					props = new Properties();
					props.load(in);
					in.close();

					output = new FileOutputStream(path + "\\config.properties");

					props.setProperty("ProfName_Current", "xxx");
					props.setProperty("ProfName_Save1", "xxx");
					props.setProperty("ProfName_Save2", "xxx");
					props.setProperty("ProfName_Save3", "xxx");

					props.store(output, null);
				}
			}
			// Catches Error while getting the "new" config file format.
			catch (NullPointerException x)
			{

				try
				{

					props = new Properties();
					output = new FileOutputStream(path + "\\config.properties");
					// attempting to get the old formation and converting it to the new format.
					props.setProperty("Name_Current", prop.getProperty("Name"));
					props.setProperty("IP_Current", prop.getProperty("IP"));
					props.setProperty("Mask_Current", prop.getProperty("Mask"));
					props.setProperty("Gateway_Current", prop.getProperty("Gateway"));

					props.setProperty("Name_Save1", "xxx");
					props.setProperty("IP_Save1", "xxx");
					props.setProperty("Mask_Save1", "xxx");
					props.setProperty("Gateway_Save1", "xxx");

					props.setProperty("Name_Save2", "xxx");
					props.setProperty("IP_Save2", "xxx");
					props.setProperty("Mask_Save2", "xxx");
					props.setProperty("Gateway_Save2", "xxx");

					props.setProperty("Name_Save3", "xxx");
					props.setProperty("IP_Save3", "xxx");
					props.setProperty("Mask_Save3", "xxx");
					props.setProperty("Gateway_Save3", "xxx");

					props.setProperty("ProfName_Current", "xxx");
					props.setProperty("ProfName_Save1", "xxx");
					props.setProperty("ProfName_Save2", "xxx");
					props.setProperty("ProfName_Save3", "xxx");

					System.out.println("OLD CONFIG V1.0");

					props.store(output, null);

				}
				catch (NullPointerException x1)
				{

					System.out.println("BAD CONFIG");
					props = new Properties();
					output = new FileOutputStream(path + "\\config.properties");

					props.setProperty("Name_Current", "xxx");
					props.setProperty("IP_Current", "xxx");
					props.setProperty("Mask_Current", "xxx");
					props.setProperty("Gateway_Current", "xxx");

					props.setProperty("Name_Save1", "xxx");
					props.setProperty("IP_Save1", "xxx");
					props.setProperty("Mask_Save1", "xxx");
					props.setProperty("Gateway_Save1", "xxx");

					props.setProperty("Name_Save2", "xxx");
					props.setProperty("IP_Save2", "xxx");
					props.setProperty("Mask_Save2", "xxx");
					props.setProperty("Gateway_Save2", "xxx");

					props.setProperty("Name_Save3", "xxx");
					props.setProperty("IP_Save3", "xxx");
					props.setProperty("Mask_Save3", "xxx");
					props.setProperty("Gateway_Save3", "xxx");

					props.setProperty("ProfName_Current", "xxx");
					props.setProperty("ProfName_Save1", "xxx");
					props.setProperty("ProfName_Save2", "xxx");
					props.setProperty("ProfName_Save3", "xxx");

					props.store(output, null);
				}
			}
		}

		////////////////////////////////////////////////////////////////////
		// End of settings
		////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////
		// Start of settings (again lol)
		////////////////////////////////////////////////////////////////////

		settings = new Stage();

		settings.setResizable(false);

		settings.setHeight(280);
		settings.setWidth(400);

		AnchorPaneSettings = new AnchorPane();

		load1.setOnAction(e ->

		{
			props = new Properties();
			FileInputStream in;
			try
			{
				in = new FileInputStream(path + "\\config.properties");
				props.load(in);
				in.close();
			}
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}

			if (!props.get("Name_Save1").equals("xxx"))
			{
				name.setText((String) props.get("Name_Save1"));
			}
			if (!props.get("Mask_Save1").equals("xxx"))
			{
				Mask.setText((String) props.get("Mask_Save1"));
			}
			if (!props.get("IP_Save1").equals("xxx"))
			{
				IP.setText((String) props.get("IP_Save1"));
			}
			if (!props.get("Gateway_Save1").equals("xxx"))
			{
				gateway.setText((String) props.get("Gateway_Save1"));
			}

			if (!props.get("ProfName_Save1").equals("xxx"))
			{

				profName.setText((String) props.get("ProfName_Save1"));
			}
		});
		load2.setOnAction(e ->
		{
			props = new Properties();
			FileInputStream in;
			try
			{
				in = new FileInputStream(path + "\\config.properties");
				props.load(in);
				in.close();
			}
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}

			if (!props.get("Name_Save2").equals("xxx"))
			{
				name.setText((String) props.get("Name_Save2"));
			}
			if (!props.get("Mask_Save2").equals("xxx"))
			{
				Mask.setText((String) props.get("Mask_Save2"));
			}
			if (!props.get("IP_Save2").equals("xxx"))
			{
				IP.setText((String) props.get("IP_Save2"));
			}
			if (!props.get("Gateway_Save2").equals("xxx"))
			{
				gateway.setText((String) props.get("Gateway_Save2"));
			}
			if (!props.get("ProfName_Save2").equals("xxx"))
			{

				profName.setText((String) props.get("ProfName_Save2"));
			}
		});
		load3.setOnAction(e ->
		{
			props = new Properties();
			FileInputStream in;
			try
			{
				in = new FileInputStream(path + "\\config.properties");
				props.load(in);
				in.close();
			}
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}

			if (!props.get("Name_Save3").equals("xxx"))
			{
				name.setText((String) props.get("Name_Save3"));
			}
			if (!props.get("Mask_Save3").equals("xxx"))
			{
				Mask.setText((String) props.get("Mask_Save3"));
			}
			if (!props.get("IP_Save3").equals("xxx"))
			{
				IP.setText((String) props.get("IP_Save3"));
			}
			if (!props.get("Gateway_Save3").equals("xxx"))
			{
				gateway.setText((String) props.get("Gateway_Save3"));
			}
			if (!props.get("ProfName_Save3").equals("xxx"))
			{

				profName.setText((String) props.get("ProfName_Save3"));
			}
		});

		save1.setOnAction(e ->
		{
			if (!IP.getText().matches(".*[0-9].*"))
			{
				IPLabel.setTextFill(Color.RED);
			}
			else
				IPLabel.setTextFill(Color.BLACK);

			if (!Mask.getText().matches(".*[0-9].*"))
			{
				MaskLabel.setTextFill(Color.RED);
			}
			else
				MaskLabel.setTextFill(Color.BLACK);
			if (!gateway.getText().matches(".*[0-9].*"))
			{
				gatewayLabel.setTextFill(Color.RED);
			}
			else
				gatewayLabel.setTextFill(Color.BLACK);

			if (IP.getText().matches(".*[0-9].*") && Mask.getText().matches(".*[0-9].*") && gateway.getText().matches(".*[0-9].*"))
			{
				try
				{
					props = new Properties();
					FileInputStream in = new FileInputStream(path + "\\config.properties");
					props = new Properties();
					props.load(in);
					in.close();

					output = new FileOutputStream(path + "\\config.properties");

					props.setProperty("Name_Save1", name.getText());
					props.setProperty("IP_Save1", IP.getText());
					props.setProperty("Mask_Save1", Mask.getText());
					props.setProperty("Gateway_Save1", gateway.getText());

					if (!profName.getText().equals(""))
					{
						props.setProperty("ProfName_Save1", profName.getText());
						load1.setText("Load " + profName.getText());
					}
					else
					{
						props.setProperty("ProfName_Save1", "xxx");
						load1.setText("Load Profile 1");
					}
					props.store(output, null);

				}
				catch (IOException io)
				{
					io.printStackTrace();
				}
				finally
				{
					if (output != null)
					{
						try
						{
							output.close();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}
		});
		save2.setOnAction(e ->
		{
			if (!IP.getText().matches(".*[0-9].*"))
			{
				IPLabel.setTextFill(Color.RED);
			}
			else
				IPLabel.setTextFill(Color.BLACK);

			if (!Mask.getText().matches(".*[0-9].*"))
			{
				MaskLabel.setTextFill(Color.RED);
			}
			else
				MaskLabel.setTextFill(Color.BLACK);
			if (!gateway.getText().matches(".*[0-9].*"))
			{
				gatewayLabel.setTextFill(Color.RED);
			}
			else
				gatewayLabel.setTextFill(Color.BLACK);

			if (IP.getText().matches(".*[0-9].*") && Mask.getText().matches(".*[0-9].*") && gateway.getText().matches(".*[0-9].*"))
			{
				try
				{
					props = new Properties();
					FileInputStream in = new FileInputStream(path + "\\config.properties");
					props = new Properties();
					props.load(in);
					in.close();

					output = new FileOutputStream(path + "\\config.properties");

					props.setProperty("Name_Save2", name.getText());
					props.setProperty("IP_Save2", IP.getText());
					props.setProperty("Mask_Save2", Mask.getText());
					props.setProperty("Gateway_Save2", gateway.getText());
					if (!profName.getText().equals(""))
					{
						props.setProperty("ProfName_Save2", profName.getText());
						load2.setText("Load " + profName.getText());
					}
					else
					{
						props.setProperty("ProfName_Save2", "xxx");
						load2.setText("Load Profile 2");
					}

					props.store(output, null);

				}
				catch (IOException io)
				{
					io.printStackTrace();
				}
				finally
				{
					if (output != null)
					{
						try
						{
							output.close();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}
		});
		save3.setOnAction(e ->
		{
			if (!IP.getText().matches(".*[0-9].*"))
			{
				IPLabel.setTextFill(Color.RED);
			}
			else
				IPLabel.setTextFill(Color.BLACK);

			if (!Mask.getText().matches(".*[0-9].*"))
			{
				MaskLabel.setTextFill(Color.RED);
			}
			else
				MaskLabel.setTextFill(Color.BLACK);
			if (!gateway.getText().matches(".*[0-9].*"))
			{
				gatewayLabel.setTextFill(Color.RED);
			}
			else
				gatewayLabel.setTextFill(Color.BLACK);
			if (IP.getText().matches(".*[0-9].*") && Mask.getText().matches(".*[0-9].*") && gateway.getText().matches(".*[0-9].*"))
			{
				try
				{
					props = new Properties();
					FileInputStream in = new FileInputStream(path + "\\config.properties");
					props = new Properties();
					props.load(in);
					in.close();

					output = new FileOutputStream(path + "\\config.properties");

					props.setProperty("Name_Save3", name.getText());
					props.setProperty("IP_Save3", IP.getText());
					props.setProperty("Mask_Save3", Mask.getText());
					props.setProperty("Gateway_Save3", gateway.getText());
					if (!profName.getText().equals(""))
					{
						props.setProperty("ProfName_Save3", profName.getText());
						load3.setText("Load " + profName.getText());
					}
					else
					{
						props.setProperty("ProfName_Save3", "xxx");
						load3.setText("Load Profile 3");
					}

					props.store(output, null);

				}
				catch (IOException io)
				{
					io.printStackTrace();
				}
				finally
				{
					if (output != null)
					{
						try
						{
							output.close();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}
		});

		apply.setOnAction(e ->
		{
			// If the IP address field does not contain the
			if (!IP.getText().matches(".*[0-9].*"))
			{
				IPLabel.setTextFill(Color.RED);
			}
			else
				IPLabel.setTextFill(Color.BLACK);

			if (!Mask.getText().matches(".*[0-9].*"))
			{
				MaskLabel.setTextFill(Color.RED);
			}
			else
				MaskLabel.setTextFill(Color.BLACK);
			if (!gateway.getText().matches(".*[0-9].*"))
			{
				gatewayLabel.setTextFill(Color.RED);
			}
			else
				gatewayLabel.setTextFill(Color.BLACK);

			// If all of the IP fields are numbers then we will apply the settings to the
			// properties file
			if (IP.getText().matches(".*[0-9].*") && Mask.getText().matches(".*[0-9].*") && gateway.getText().matches(".*[0-9].*"))
			{
				try
				{
					props = new Properties();
					FileInputStream in = new FileInputStream(path + "\\config.properties");
					props = new Properties();
					props.load(in);
					in.close();

					output = new FileOutputStream(path + "\\config.properties");

					props.setProperty("Name_Current", name.getText());
					props.setProperty("IP_Current", IP.getText());
					props.setProperty("Mask_Current", Mask.getText());
					props.setProperty("Gateway_Current", gateway.getText());
					if (!profName.getText().equals(""))
						props.setProperty("ProfName_Current", profName.getText());
					else
						props.setProperty("ProfName_Current", "xxx");

					props.store(output, null);
					settings.hide();

				}
				catch (IOException io)
				{
					io.printStackTrace();
				}
				finally
				{
					if (output != null)
					{
						try
						{
							output.close();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}

		});

		// connection name field
		name.setMaxWidth(150);
		name.setMinWidth(150);
		AnchorPane.setTopAnchor(name, 5.0);
		AnchorPane.setLeftAnchor(name, 125.0);
		AnchorPaneSettings.getChildren().add(name);

		// IP field
		IP.setMaxWidth(150);
		IP.setMinWidth(150);
		AnchorPane.setTopAnchor(IP, 35.0);
		AnchorPane.setLeftAnchor(IP, 125.0);
		AnchorPaneSettings.getChildren().add(IP);
		// Mask field
		Mask.setMaxWidth(150);
		Mask.setMinWidth(150);
		AnchorPane.setTopAnchor(Mask, 65.0);
		AnchorPane.setLeftAnchor(Mask, 125.0);
		AnchorPaneSettings.getChildren().add(Mask);
		// Gateway field
		gateway.setMaxWidth(150);
		gateway.setMinWidth(150);
		AnchorPane.setTopAnchor(gateway, 95.0);
		AnchorPane.setLeftAnchor(gateway, 125.0);
		AnchorPaneSettings.getChildren().add(gateway);

		profName.setMaxWidth(150);
		profName.setMinWidth(150);
		AnchorPane.setTopAnchor(profName, 125.0);
		AnchorPane.setLeftAnchor(profName, 125.0);
		AnchorPaneSettings.getChildren().add(profName);

		// Connection name label
		AnchorPane.setTopAnchor(connectionName, 5.0);
		AnchorPane.setLeftAnchor(connectionName, 5.0);
		AnchorPaneSettings.getChildren().add(connectionName);
		// Mask Label
		AnchorPane.setTopAnchor(IPLabel, 35.0);
		AnchorPane.setLeftAnchor(IPLabel, 5.0);
		AnchorPaneSettings.getChildren().add(IPLabel);
		// Mask Label
		AnchorPane.setTopAnchor(MaskLabel, 65.0);
		AnchorPane.setLeftAnchor(MaskLabel, 5.0);
		AnchorPaneSettings.getChildren().add(MaskLabel);

		// Gateway Label
		AnchorPane.setTopAnchor(gatewayLabel, 95.0);
		AnchorPane.setLeftAnchor(gatewayLabel, 5.0);
		AnchorPaneSettings.getChildren().add(gatewayLabel);

		AnchorPane.setTopAnchor(profLabel, 125.0);
		AnchorPane.setLeftAnchor(profLabel, 5.0);
		AnchorPaneSettings.getChildren().add(profLabel);

		// Apply Button
		AnchorPane.setTopAnchor(apply, 160.0);
		AnchorPane.setLeftAnchor(apply, 300.0);
		AnchorPaneSettings.getChildren().add(apply);

		AnchorPane.setTopAnchor(save1, 200.0);
		AnchorPane.setLeftAnchor(save1, 5.0);
		AnchorPaneSettings.getChildren().add(save1);

		load1.setMaxWidth(89.0);
		load1.setMinWidth(89.0);
		AnchorPane.setTopAnchor(load1, 160.0);
		AnchorPane.setLeftAnchor(load1, 5.0);
		AnchorPaneSettings.getChildren().add(load1);

		AnchorPane.setTopAnchor(save2, 200.0);
		AnchorPane.setLeftAnchor(save2, 100.0);
		AnchorPaneSettings.getChildren().add(save2);

		load2.setMaxWidth(89.0);
		load2.setMinWidth(89.0);
		AnchorPane.setTopAnchor(load2, 160.0);
		AnchorPane.setLeftAnchor(load2, 100.0);
		AnchorPaneSettings.getChildren().add(load2);

		AnchorPane.setTopAnchor(save3, 200.0);
		AnchorPane.setLeftAnchor(save3, 195.0);
		AnchorPaneSettings.getChildren().add(save3);

		load3.setMaxWidth(89.0);
		load3.setMinWidth(89.0);
		AnchorPane.setTopAnchor(load3, 160.0);
		AnchorPane.setLeftAnchor(load3, 195.0);
		AnchorPaneSettings.getChildren().add(load3);

		Scene sceneSettings = new Scene(AnchorPaneSettings);
		settings.setScene(sceneSettings);

		settings.setOnShowing(e ->
		{
			props = new Properties();
			FileInputStream in;
			try
			{
				in = new FileInputStream(path + "\\config.properties");
				props.load(in);
				in.close();
			}
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}

			if (!props.get("Name_Current").equals("xxx"))
			{
				name.setText((String) props.get("Name_Current"));
			}
			if (!props.get("Mask_Current").equals("xxx"))
			{
				Mask.setText((String) props.get("Mask_Current"));
			}
			if (!props.get("IP_Current").equals("xxx"))
			{
				IP.setText((String) props.get("IP_Current"));
			}
			if (!props.get("Gateway_Current").equals("xxx"))
			{
				gateway.setText((String) props.get("Gateway_Current"));
			}
			if (!props.get("ProfName_Current").equals("xxx"))
			{
				profName.setText((String) props.get("ProfName_Current"));
			}
			if (!props.get("ProfName_Save1").equals("xxx"))
			{
				load1.setText("Load " + (String) props.get("ProfName_Save1"));
			}
			else
				load1.setText("Load Profile 1");
			if (!props.get("ProfName_Save2").equals("xxx"))
			{
				load2.setText("Load " + (String) props.get("ProfName_Save2"));
			}
			else
				load2.setText("Load Profile 2");
			if (!props.get("ProfName_Save3").equals("xxx"))
			{
				load3.setText("Load " + (String) props.get("ProfName_Save3"));
			}
			else
				load3.setText("Load Profile 3");
		});

		settings.setTitle("Settings");
		settings.hide();
		////////////////////////////////////////////////////////////////////
		// End of Settings
		////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////
		// Start of the About window
		////////////////////////////////////////////////////////////////////
		about = new Stage();
		about.setTitle("About");
		about.hide();

		GridPane AboutPane = new GridPane();
		AboutPane.setHgap(5);
		AboutPane.setVgap(5);

		Label version = new Label("Version: " + versionnum);
		Label creator = new Label("Creator: Cameron Coesens (Team 3539)");
		Label contact = new Label("Email:    bbprogrammers3539@gmail.com");

		GridPane.setConstraints(version, 8, 10);
		AboutPane.add(version, 8, 10);
		GridPane.setConstraints(creator, 8, 11);
		AboutPane.add(creator, 8, 11);
		GridPane.setConstraints(contact, 8, 12);
		AboutPane.add(contact, 8, 12);

		Scene sceneAbout = new Scene(AboutPane);
		about.setScene(sceneAbout);

		about.setResizable(false);

		about.setHeight(250);
		about.setWidth(350);

		////////////////////////////////////////////////////////////////////
		// End of the About window
		////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////
		// Start of Main window
		////////////////////////////////////////////////////////////////////

		AnchorPane DefaultPane = new AnchorPane();

		MenuBar bar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem menuSettings = new MenuItem("Settings");
		MenuItem menuAbout = new MenuItem("About");

		Button dhcpbutton = new Button("DHCP");

		Button staticbutton = new Button("Static");

		Label info = new Label("Please Change Your Settings.");
		Label interfaceError = new Label("ERROR: Invalid Connection Name.");
		Label ipv4Error = new Label("ERROR: Invalid IPV4 Address.");
		Label maskError = new Label("ERROR: Invalid Mask Address.");
		Label gatewayError = new Label("ERROR: Invalid Gateway Address.");
		Label adminError = new Label("ERROR: Run the program in Administrator.");
		Label success = new Label("Success.");

		fadeOut.setNode(success);

		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setCycleCount(1);
		fadeOut.setAutoReverse(false);
		info.setTextFill(Color.RED);
		interfaceError.setTextFill(Color.RED);
		ipv4Error.setTextFill(Color.RED);
		maskError.setTextFill(Color.RED);
		gatewayError.setTextFill(Color.RED);
		adminError.setTextFill(Color.RED);
		success.setTextFill(Color.LIGHTGREEN);

		success.setStyle("-fx-font-weight: bold");

		dhcpbutton.setMaxSize(100, 40);
		dhcpbutton.setMinSize(100, 40);

		staticbutton.setMaxSize(100, 40);
		staticbutton.setMinSize(100, 40);

		menuSettings.setOnAction(e ->
		{
			settings.setIconified(false);
			settings.show();
		});

		menuAbout.setOnAction(e ->
		{
			about.setIconified(false);
			about.show();

		});

		dhcpbutton.setOnAction(e ->
		{

			props = new Properties();
			FileInputStream in;
			try
			{
				in = new FileInputStream(path + "\\config.properties");
				props.load(in);
				in.close();
			}
			catch (FileNotFoundException e2)
			{
				e2.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			if (props.get("Name_Current").equals("xxx") || props.get("Mask_Current").equals("xxx") || props.get("IP_Current").equals("xxx")
					|| props.get("Gateway_Current").equals("xxx"))
			{
				info.setVisible(true);
			}
			else
			{
				info.setVisible(false);

				try
				{
					Process p = Runtime.getRuntime()
							.exec("netsh interface ipv4 set address name=" + props.get("Name_Current") + " source=dhcp");
					BufferedReader in1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = null;
					while ((line = in1.readLine()) != null)
					{
						if (!line.equals(""))
						{
							if (line.equalsIgnoreCase("The filename, directory name, or volume label syntax is incorrect."))
							{
								System.out.println("INTERFACE");
								interfaceError.setVisible(true);
							}
							else
							{
								interfaceError.setVisible(false);
							}

							if (line.equalsIgnoreCase("The requested operation requires elevation (Run as administrator)."))
							{
								System.out.println("ADMIN");
								adminError.setVisible(true);
								success.setVisible(false);
							}

							else
							{
								adminError.setVisible(false);
							}
							if (line.contains("Invalid address parameter"))
							{
								System.out.println("IPV4");
								ipv4Error.setVisible(true);
								success.setVisible(false);
							}
							else
							{
								ipv4Error.setVisible(false);
							}
							if (line.contains("Invalid mask parameter"))
							{
								System.out.println("MASK");
								maskError.setVisible(true);
								success.setVisible(false);
							}
							else
							{
								maskError.setVisible(false);
							}
							if (line.contains("Invalid gateway parameter"))
							{
								System.out.println("GATEWAY");
								gatewayError.setVisible(true);
								success.setVisible(false);
							}
							else
							{
								gatewayError.setVisible(false);
							}
							if (line.contains("DHCP is already enabled on this interface."))
							{
								System.out.println("SUCCESSFUL");
								success.setVisible(true);

								fadeOut.playFromStart();
							}
							else
							{
								success.setVisible(false);

							}
							System.out.println(line);
						}
					}
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}

		});

		staticbutton.setOnAction(e ->
		{
			props = new Properties();
			FileInputStream in;

			try
			{
				in = new FileInputStream(path + "\\config.properties");
				props.load(in);
				in.close();
			}
			catch (FileNotFoundException e2)
			{
				e2.printStackTrace();
			}
			catch (IOException e1)
			{

				e1.printStackTrace();
			}
			if (props.get("Name_Current").equals("xxx") || props.get("Mask_Current").equals("xxx") || props.get("IP_Current").equals("xxx")
					|| props.get("Gateway_Current").equals("xxx"))
			{
				info.setVisible(true);
			}
			else
			{
				info.setVisible(false);
				try
				{
					Process p = Runtime.getRuntime().exec("netsh interface ipv4 set address name=" + props.get("Name_Current") + " static "
							+ props.get("IP_Current") + " " + props.get("Mask_Current") + " " + props.getProperty("Gateway_Current"));
					BufferedReader in1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = null;
					line = in1.readLine();
					line = in1.readLine() + line;
					System.out.println(line);
					if (line.equalsIgnoreCase("The filename, directory name, or volume label syntax is incorrect."))
					{
						System.out.println("INTERFACE");
						interfaceError.setVisible(true);
						success.setVisible(false);
					}
					else
					{
						interfaceError.setVisible(false);
					}

					if (line.equalsIgnoreCase("The requested operation requires elevation (Run as administrator)."))
					{
						System.out.println("ADMIN");
						adminError.setVisible(true);
						success.setVisible(false);
					}
					else
					{
						adminError.setVisible(false);
					}
					if (line.contains("Invalid address parameter"))
					{
						System.out.println("IPV4");
						ipv4Error.setVisible(true);
						success.setVisible(false);
					}
					else
					{
						ipv4Error.setVisible(false);
					}
					if (line.contains("Invalid mask parameter"))
					{
						System.out.println("MASK");
						maskError.setVisible(true);
						success.setVisible(false);
					}
					else
					{
						maskError.setVisible(false);
					}
					if (line.contains("Invalid gateway parameter"))
					{
						System.out.println("GATEWAY");
						gatewayError.setVisible(true);
						success.setVisible(false);
					}
					else
					{
						gatewayError.setVisible(false);
					}
					if (!line.contains("Invalid gateway parameter") && !line.contains("Invalid mask parameter")
							&& !line.contains("Invalid address parameter")
							&& !line.equalsIgnoreCase("The requested operation requires elevation (Run as administrator).")
							&& !line.equalsIgnoreCase("The filename, directory name, or volume label syntax is incorrect."))
					{
						success.setVisible(true);
						fadeOut.playFromStart();
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
					}
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}

		});

		AnchorPane.setTopAnchor(dhcpbutton, 75.0);
		AnchorPane.setLeftAnchor(dhcpbutton, 150.0);

		DefaultPane.getChildren().add(dhcpbutton);

		AnchorPane.setTopAnchor(staticbutton, 125.0);
		AnchorPane.setLeftAnchor(staticbutton, 150.0);
		DefaultPane.getChildren().add(staticbutton);

		AnchorPane.setTopAnchor(info, 175.0);
		AnchorPane.setLeftAnchor(info, 125.0);
		DefaultPane.getChildren().add(info);

		AnchorPane.setTopAnchor(interfaceError, 175.0);
		AnchorPane.setLeftAnchor(interfaceError, 110.0);
		DefaultPane.getChildren().add(interfaceError);

		AnchorPane.setTopAnchor(ipv4Error, 175.0);
		AnchorPane.setLeftAnchor(ipv4Error, 125.0);
		DefaultPane.getChildren().add(ipv4Error);

		AnchorPane.setTopAnchor(maskError, 175.0);
		AnchorPane.setLeftAnchor(maskError, 125.0);
		DefaultPane.getChildren().add(maskError);

		AnchorPane.setTopAnchor(gatewayError, 175.0);
		AnchorPane.setLeftAnchor(gatewayError, 115.0);
		DefaultPane.getChildren().add(gatewayError);

		AnchorPane.setTopAnchor(adminError, 175.0);
		AnchorPane.setLeftAnchor(adminError, 90.0);
		DefaultPane.getChildren().add(adminError);

		AnchorPane.setTopAnchor(success, 175.0);
		AnchorPane.setLeftAnchor(success, 175.0);
		DefaultPane.getChildren().add(success);

		info.setVisible(false);
		interfaceError.setVisible(false);
		ipv4Error.setVisible(false);
		maskError.setVisible(false);
		gatewayError.setVisible(false);
		adminError.setVisible(false);
		success.setVisible(false);

		menuFile.getItems().addAll(menuSettings, menuAbout);

		bar.prefWidthProperty().bind(defaultStage.widthProperty());

		bar.getMenus().add(menuFile);

		defaultStage.setWidth(400);
		defaultStage.setHeight(260);

		DefaultPane.getChildren().add(bar);

		Scene sceneDefault = new Scene(DefaultPane);
		defaultStage.setScene(sceneDefault);

		defaultStage.setResizable(false);

		defaultStage.setOnCloseRequest(e ->
		{
			about.close();
			settings.close();
		});

		defaultStage.setTitle("FRC IP Switch");
		defaultStage.show();
		defaultStage.getIcons().add(new Image(Main.class.getResourceAsStream("download.png")));
		about.getIcons().add(new Image(Main.class.getResourceAsStream("download.png")));
		settings.getIcons().add(new Image(Main.class.getResourceAsStream("download.png")));

		////////////////////
		// End of Main window
		////////////////////

	}
}
