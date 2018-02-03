package com.bytingbulldogs.team3539;

import javafx.scene.paint.Color;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import eu.hansolo.enzo.notification.Notification;
import eu.hansolo.enzo.notification.Notification.*;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.embed.swing.SwingFXUtils;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/*CHANGES 1.1
 * Add Smart Properties file checking system
 * Add profiles
 */
/*CHANGES 1.2
 * Improve Smart Properties file checking system
 * Add profile namesS
 * Add fading feature for Success Label to improve clarity between runs
 */
/*CHANGES 1.3
 * Update smart config.
 * Split each window into their own class.
 * Add Taskbar Support.
 * Add Notifications.
 *
 */

public class Main extends Application
{
	public Stage defaultStage;
	public Stage stage;
	double versionNum = 1.3;
	Properties props;
	OutputStream output = null;
	FileInputStream in;
	String path = System.getProperty("user.home") + "\\documents\\FRCIPController";

	private FadeTransition fadeOut = new FadeTransition(Duration.millis(3000));

	Settings settings = new Settings();
	About about = new About();

	Label info = new Label("Please Change Your Settings.");
	Label interfaceError = new Label("ERROR: Invalid Connection Name.");
	Label ipv4Error = new Label("ERROR: Invalid IPV4 Address.");
	Label maskError = new Label("ERROR: Invalid Mask Address.");
	Label gatewayError = new Label("ERROR: Invalid Gateway Address.");
	Label adminError = new Label("ERROR: Run the program in Administrator.");
	Label success = new Label("Success.");

	public static void main(String[] args)
	{
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception
	{

		this.stage = stage;
		this.stage.initStyle(StageStyle.UTILITY);
		this.stage.setOpacity(0);

		defaultStage = new Stage();

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
			settings.showSettings();
		});

		menuAbout.setOnAction(e ->
		{
			about.showAbout();

		});

		dhcpbutton.setOnAction(e ->
		{
			setDHCP();
		});

		staticbutton.setOnAction(e ->
		{
			setStatic();
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
			about.hideAbout();
			settings.hideSettings();
			defaultStage.hide();
			e.consume();

		});

		defaultStage.setTitle("FRC IP Switch");
		defaultStage.show();
		defaultStage.getIcons().add(new Image(Main.class.getResourceAsStream("download.png")));

		about.setVersion(versionNum);
		////////////////////
		// End of Main window
		addAppToTray();
		this.stage.show();
	}

	public void showStage()
	{
		defaultStage.show();
		defaultStage.toFront();
		defaultStage.setIconified(false);
	}

	public void hideStages()
	{
		defaultStage.hide();
		about.hideAbout();
		settings.hideSettings();

	}

	public void addAppToTray()
	{
		try
		{
			stage.setFullScreen(true);
			stage.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
			stage.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
			Notifier.setPopupLocation(stage, Pos.BOTTOM_RIGHT);

			// ensure awt toolkit is initialized.
			java.awt.Toolkit.getDefaultToolkit();

			// set up a system tray icon.

			java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
			java.awt.Image image1 = ImageIO.read(Main.class.getResource("download.png"));
			java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image1);
			trayIcon.setImageAutoSize(true);

			// show the main app stage.
     		java.awt.MenuItem openItem = new java.awt.MenuItem("Show");
			//
			java.awt.MenuItem DHCP = new java.awt.MenuItem("DHCP");
			java.awt.MenuItem Static = new java.awt.MenuItem("Static");
			DHCP.addActionListener(e ->
			{
				setDHCP();
			});
			Static.addActionListener(e ->
			{

				setStatic();
			});
			
			trayIcon.addActionListener(event -> Platform.runLater(this::showStage));
			
			openItem.addActionListener(event -> Platform.runLater(this::showStage));
			
			 // the convention for tray icons seems to be to set the default icon for opening
			 // the application stage in a bold font.
			java.awt.Font defaultFont = java.awt.Font.decode(null);
			openItem.setFont(defaultFont);
			
			 // to really exit the application, the user must go to the system tray icon
			 // and select the exit option, this will shutdown JavaFX and remove the
			 // tray icon (removing the tray icon will also shut down AWT).
			
			java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
			exitItem.addActionListener(event ->
			{
				Platform.exit();
				tray.remove(trayIcon);
			});


			
			 // setup the popup menu for the application.
			final java.awt.PopupMenu popup = new java.awt.PopupMenu();
			popup.add(openItem);
			popup.add(DHCP);
			popup.add(Static);
			popup.addSeparator();
			popup.add(exitItem);
			trayIcon.setPopupMenu(popup);

			// add the application tray icon to the system tray.
			tray.add(trayIcon);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setDHCP()
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
		if (props.get("Name_Current").equals("xxx") || props.get("Mask_Current").equals("xxx") || props.get("IP_Current").equals("xxx") || props.get("Gateway_Current").equals("xxx"))
		{
			System.out.println("SUCCESSFUL");
			if (props.getProperty("Use_Noti").equals("xxx"))
			{
				notify("ERROR", "Please check your settings.", Notification.ERROR_ICON);
				gatewayError.setVisible(false);
				maskError.setVisible(false);
				ipv4Error.setVisible(false);
				interfaceError.setVisible(false);
				adminError.setVisible(false);
				success.setVisible(false);
			}
			else if (props.getProperty("Use_Noti").equals("T true"))
			{
				notify("ERROR", "Please check your settings.", Notification.ERROR_ICON);
				gatewayError.setVisible(false);
				maskError.setVisible(false);
				ipv4Error.setVisible(false);
				interfaceError.setVisible(false);
				adminError.setVisible(false);
				success.setVisible(false);
			}
		}
		else
		{
			try
			{
				Process p = Runtime.getRuntime().exec("netsh interface ipv4 set address name=" + props.get("Name_Current") + " source=dhcp");
				BufferedReader in1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = "";
				line += in1.readLine();
				line += in1.readLine();

				if (!line.contains("Invalid gateway parameter") && !line.contains("Invalid mask parameter") && !line.contains("Invalid address parameter")
						&& !line.equalsIgnoreCase("The requested operation requires elevation (Run as administrator).") && !line.equalsIgnoreCase("The filename, directory name, or volume label syntax is incorrect."))
				{
					System.out.println("SUCCESSFUL");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Success", "Success", Notification.SUCCESS_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Success", "Success", Notification.SUCCESS_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(true);
						fadeOut.playFromStart();
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}
				}

				else if (line.contains("The filename, directory name, or volume label syntax is incorrect."))
				{
					System.out.println("INTERFACE");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Invalid Connection Name.", Notification.ERROR_ICON);

						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Invalid Connection Name.", Notification.ERROR_ICON);

						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(false);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(true);
						adminError.setVisible(false);
					}

				}

				else if (line.contains("The requested operation requires elevation (Run as administrator)."))
				{
					System.out.println("ADMIN");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Run the program in Administrator.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Run the program in Administrator.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(false);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(true);
					}

				}

				else if (line.contains("Invalid address parameter"))
				{
					System.out.println("IPV4");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Invalid IPV4 Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Invalid IPV4 Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{

						success.setVisible(false);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(true);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}

				}

				else if (line.contains("Invalid mask parameter"))
				{
					System.out.println("MASK");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Invalid Mask Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Invalid Mask Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(false);
						gatewayError.setVisible(false);
						maskError.setVisible(true);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}

				}

				else if (line.contains("Invalid gateway parameter"))
				{
					System.out.println("GATEWAY");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Invalid Gateway Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Invalid Gateway Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(false);
						gatewayError.setVisible(true);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}

				}

				else if (line.contains("DHCP is already enabled on this interface."))

				{
					System.out.println("SUCCESSFUL");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Success", "Success", Notification.SUCCESS_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Success", "Success", Notification.SUCCESS_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(true);
						fadeOut.playFromStart();
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}

				}

			}
			catch (

			IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public void setStatic()
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
		if (props.get("Name_Current").equals("xxx") || props.get("Mask_Current").equals("xxx") || props.get("IP_Current").equals("xxx") || props.get("Gateway_Current").equals("xxx"))
		{
			System.out.println("SUCCESSFUL");
			if (props.getProperty("Use_Noti").equals("xxx"))
				notify("ERROR", "Please check your settings.", Notification.ERROR_ICON);
			else if (props.getProperty("Use_Noti").equals("T true"))
				notify("ERROR", "Please check your settings.", Notification.ERROR_ICON);
		}
		else
		{
			try
			{
				Process p = Runtime.getRuntime()
						.exec("netsh interface ipv4 set address name=" + props.get("Name_Current") + " static " + props.get("IP_Current") + " " + props.get("Mask_Current") + " " + props.getProperty("Gateway_Current"));
				BufferedReader in1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = "";
				line += in1.readLine();
				line += in1.readLine();

				if (!line.contains("Invalid gateway parameter") && !line.contains("Invalid mask parameter") && !line.contains("Invalid address parameter")
						&& !line.equalsIgnoreCase("The requested operation requires elevation (Run as administrator).") && !line.equalsIgnoreCase("The filename, directory name, or volume label syntax is incorrect."))
				{
					System.out.println("SUCCESSFUL");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
						notify("Success", "Success", Notification.SUCCESS_ICON);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Success", "Success", Notification.SUCCESS_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(true);
						fadeOut.playFromStart();
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}
				}

				else if (line.contains("The filename, directory name, or volume label syntax is incorrect."))
				{
					System.out.println("INTERFACE");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Invalid Connection Name.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Invalid Connection Name.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(false);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(true);
						adminError.setVisible(false);
					}

				}

				else if (line.contains("The requested operation requires elevation (Run as administrator)."))
				{
					System.out.println("ADMIN");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Run the program in Administrator.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Run the program in Administrator.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(false);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(true);
					}

				}

				else if (line.contains("Invalid address parameter"))
				{
					System.out.println("IPV4");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Invalid IPV4 Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Invalid IPV4 Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{

						success.setVisible(false);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(true);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}

				}

				else if (line.contains("Invalid mask parameter"))
				{
					System.out.println("MASK");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Invalid Mask Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Invalid Mask Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(false);
						gatewayError.setVisible(false);
						maskError.setVisible(true);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}

				}

				else if (line.contains("Invalid gateway parameter"))
				{
					System.out.println("GATEWAY");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Error", "Invalid Gateway Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Error", "Invalid Gateway Address.", Notification.ERROR_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(false);
						gatewayError.setVisible(true);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}

				}

				else if (line.contains("DHCP is already enabled on this interface."))
				{
					System.out.println("SUCCESSFUL");
					if (props.getProperty("Use_Noti").equals("xxx"))
					{
						notify("Success", "Success", Notification.SUCCESS_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else if (props.getProperty("Use_Noti").equals("T true"))
					{
						notify("Success", "Success", Notification.SUCCESS_ICON);
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
						success.setVisible(false);
					}
					else
					{
						success.setVisible(true);
						fadeOut.playFromStart();
						gatewayError.setVisible(false);
						maskError.setVisible(false);
						ipv4Error.setVisible(false);
						interfaceError.setVisible(false);
						adminError.setVisible(false);
					}

				}
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public void notify(String title, String body, Image type)
	{
		Platform.runLater(new Runnable()
		{
			public void run()
			{

				Notifier.INSTANCE.notify(new Notification(title, body, type));
			}
		});
	}
}
