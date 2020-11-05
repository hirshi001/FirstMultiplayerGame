package com.hirshi001.multiplayerrotmg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Rectangle;
import com.hirshi001.multiplayerrotmg.Rotmg;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Rotmg.size.width;
		config.height = Rotmg.size.height;
		config.resizable = false;
		new LwjglApplication(new Rotmg(), config);
	}
}
