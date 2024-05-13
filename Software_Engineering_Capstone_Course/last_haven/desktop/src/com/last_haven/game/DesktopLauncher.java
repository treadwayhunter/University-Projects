package com.last_haven.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.Graphics.DisplayMode;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		
		DisplayMode primaryMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setFullscreenMode(primaryMode);
		
		config.setForegroundFPS(60);
		config.setTitle("Last Haven");
		//new Lwjgl3Application(new LastHaven(), config);
		new Lwjgl3Application(new LastHaven(), config);
	}
}
