package com.bytingbulldogs.team3539;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Settings
{
	public Stage settings;
	Properties props;
	private AnchorPane AnchorPaneSettings;
	String path;
	OutputStream output = null;
	FileInputStream in;

	public Settings()
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

		CheckBox noti = new CheckBox("Use Notifications");

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

				props.setProperty("Use_Noti", "xxx");

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
			try
			{
				in = new FileInputStream(path + "\\config.properties");
				prop.load(in);
				in.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
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
					String value = (String) prop.get("Use_Noti");
					if (value.equals("xxx"))
					{
						noti.setSelected(true);
					}
					if (value.equals("T true"))
					{
						noti.setSelected(true);
					}
					if (value.equals("T false"))
					{
						noti.setSelected(false);
					}
				}
				catch (NullPointerException x3)
				{
					System.out.println("OLD CONFIG V1.2");
					props = new Properties();
					FileInputStream in;
					try
					{
						in = new FileInputStream(path + "\\config.properties");
						props = new Properties();
						props.load(in);
						in.close();
						output = new FileOutputStream(path + "\\config.properties");

						props.setProperty("Use_Noti", "xxx");

						props.store(output, null);

					}
					catch (FileNotFoundException e1)
					{
						e1.printStackTrace();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}

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
					FileInputStream in;
					try
					{
						in = new FileInputStream(path + "\\config.properties");
						props = new Properties();
						props.load(in);
						in.close();
						output = new FileOutputStream(path + "\\config.properties");

						props.setProperty("ProfName_Current", "xxx");
						props.setProperty("ProfName_Save1", "xxx");
						props.setProperty("ProfName_Save2", "xxx");
						props.setProperty("ProfName_Save3", "xxx");

						props.setProperty("Use_Noti", "xxx");

						props.store(output, null);

					}
					catch (FileNotFoundException e1)
					{
						e1.printStackTrace();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}

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

					props.setProperty("Use_Noti", "xxx");

					System.out.println("OLD CONFIG V1.0");

					props.store(output, null);

				}
				catch (NullPointerException x1)
				{

					System.out.println("BAD CONFIG");
					props = new Properties();
					try
					{
						output = new FileOutputStream(path + "\\config.properties");
					}
					catch (FileNotFoundException e2)
					{
						e2.printStackTrace();
					}

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

					props.setProperty("Use_Noti", "xxx");

					try
					{
						props.store(output, null);
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
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

					props.setProperty("Use_Noti", "T " + noti.isSelected());

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

		AnchorPane.setTopAnchor(noti, 5.0);
		AnchorPane.setLeftAnchor(noti, 280.0);
		AnchorPaneSettings.getChildren().add(noti);

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
			if (props.getProperty("Use_Noti").equals("xxx"))
				noti.setSelected(true);
			else if (props.getProperty("Use_Noti").equals("T true"))
				noti.setSelected(true);
			else if (props.getProperty("Use_Noti").equals("T false"))
				noti.setSelected(false);
		});

		settings.setTitle("Settings");
		settings.hide();
		settings.getIcons().add(new Image(Main.class.getResourceAsStream("download.png")));
		////////////////////////////////////////////////////////////////////
		// End of Settings
		////////////////////////////////////////////////////////////////////

	}

	public void showSettings()
	{
		settings.show();
		settings.setIconified(false);
	}

	public void closeSettings()
	{
		settings.close();
	}

	public void hideSettings()
	{
		settings.hide();
	}
}
