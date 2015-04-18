package com.basementcrew.ld32;

import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.io.AssetManager;
import com.basementcrew.ld32.states.MainMenuState;
import java.io.File;

public class ApplicationMain {

	public static void main(String[] args) {
            AssetManager assetManager = new AssetManager(ApplicationMain.class, true);
            
            //Load all assets
            assetManager.loadImagesInDirectories("assets/img", true);
            
            GameWindow window = new AWTGameWindow("Ludum Dare 32", 800, 600);
            GameStateRunner runner = new GameStateRunner(window, assetManager);
            runner.setState(new MainMenuState());
            runner.loop();
	}

}
