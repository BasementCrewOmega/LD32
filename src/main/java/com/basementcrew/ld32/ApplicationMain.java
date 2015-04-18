package com.basementcrew.ld32;

import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.io.AssetManager;
import com.basementcrew.ld32.states.TimedGameStateRunner;
import java.io.File;

public class ApplicationMain {

	public static void main(String[] args) {
            AssetManager assetManager = new AssetManager(ApplicationMain.class, true);
            
            GameWindow window = new AWTGameWindow("Ludum Dare 32", 800, 600);
            TimedGameStateRunner runner = new TimedGameStateRunner(window, assetManager);
            runner.loop();

	}

}
