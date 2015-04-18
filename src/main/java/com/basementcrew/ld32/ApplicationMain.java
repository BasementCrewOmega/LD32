package com.basementcrew.ld32;

import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.io.AssetManager;

import com.basementcrew.ld32.data.Area;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.PlayerData;
import com.basementcrew.ld32.data.Weapon;
import com.basementcrew.ld32.data.loaders.AnimationLoader;
import com.basementcrew.ld32.data.loaders.AreaLoader;
import com.basementcrew.ld32.data.loaders.EnemyLoader;
import com.basementcrew.ld32.data.loaders.MovieLoader;
import com.basementcrew.ld32.data.loaders.PlayerDataLoader;
import com.basementcrew.ld32.data.loaders.WeaponLoader;
import com.basementcrew.ld32.movie.Movie;
import com.basementcrew.ld32.states.MainMenuState;
import com.basementcrew.ld32.states.TimedGameStateRunner;
import com.basementcrew.ld32.states.TransitionState;

public class ApplicationMain {

	public static void main(String[] args) {
            AssetManager assetManager = new AssetManager(ApplicationMain.class, true);
            
            //Register the loaders
            assetManager.addAssetLoader(new AnimationLoader(assetManager), Animation.class);
            assetManager.addAssetLoader(new AreaLoader(assetManager), Area.class);
            assetManager.addAssetLoader(new EnemyLoader(assetManager), Enemy.class);
            assetManager.addAssetLoader(new MovieLoader(assetManager), Movie.class);
            assetManager.addAssetLoader(new PlayerDataLoader(assetManager), PlayerData.class);
            assetManager.addAssetLoader(new WeaponLoader(assetManager), Weapon.class);

            //Load all assets
            assetManager.loadImage("assets/img/mainMenu/playDown.png", "playDown");
            assetManager.loadImage("assets/img/mainMenu/playUp.png", "playUp");
            assetManager.loadImage("assets/img/mainMenu/playHover.png", "playHover");
            assetManager.loadImage("assets/img/mainMenu/quitDown.png", "quitDown");
            assetManager.loadImage("assets/img/mainMenu/quitUp.png", "quitUp");
            assetManager.loadImage("assets/img/mainMenu/quitHover.png", "quitHover");
            assetManager.loadImage("assets/img/mainMenu/titleBanner.png", "titleBanner");
            
            assetManager.loadImage("assets/img/townMenu/shopDown.png", "shopDown");
            assetManager.loadImage("assets/img/townMenu/shopHover.png", "shopHover");
            assetManager.loadImage("assets/img/townMenu/shopUp.png", "shopUp");
            assetManager.loadImage("assets/img/townMenu/toTownDown.png", "toTownDown");
            assetManager.loadImage("assets/img/townMenu/toTownHover.png", "toTownHover");
            assetManager.loadImage("assets/img/townMenu/toTownUp.png", "toTownUp");
            assetManager.loadImage("assets/img/townMenu/townBackground.png", "townBackground");
            assetManager.loadImage("assets/img/townMenu/townIcon.png", "townIcon");
            
            assetManager.loadImage("assets/img/battleSequence/cooldownBar.png", "cooldownBar");
            assetManager.loadImage("assets/img/battleSequence/cooldownBarBackground.png", "cooldownBarBackground");
            assetManager.loadImage("assets/img/battleSequence/healthBar.png", "healthBar");
            assetManager.loadImage("assets/img/battleSequence/healthBarBackground.png", "healthBarBackground");
            assetManager.loadImage("assets/img/battleSequence/selector.png", "selector");
            assetManager.loadImage("assets/img/battleSequence/lowerMenuBackground.png", "lowerMenuBackground");
            
            GameWindow window = new AWTGameWindow("Ludum Dare 32", 800, 600);
            
            TimedGameStateRunner runner = new TimedGameStateRunner(window, assetManager);
            /*
            runner.setState(new MainMenuState());
            */
            runner.setState(new TransitionState(assetManager.getAsset("cool_movie", Movie.class), new MainMenuState()));
            runner.loop();
	}

}
