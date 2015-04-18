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
            assetManager.addAssetLoader(new WeaponLoader(), Weapon.class);

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
            
            // enemy1 files
            assetManager.loadImage("assets/img/entity/enemy1_idle.png", "img_enemy1_idle");
            assetManager.loadAsset("assets/data/enemy1/anim_idle.txt", "anim_enemy1_idle", Animation.class);
            assetManager.loadAsset("assets/data/enemy1/enemy.txt", "enemy_enemy1", Enemy.class);
            
            // boss1 files
            assetManager.loadImage("assets/img/entity/boss1_idle.png", "img_boss1_idle");
            assetManager.loadAsset("assets/data/boss1/anim_idle.txt", "anim_boss1_idle", Animation.class);
            assetManager.loadAsset("assets/data/boss1/enemy.txt", "enemy_boss1", Enemy.class);
            
            // player file
            assetManager.loadAsset("assets/data/weapon_fists.txt", "weapon_fists", Weapon.class);

            // areas
            assetManager.loadImage("assets/img/battleSequence/area1_background.png", "area1_background");
            assetManager.loadAsset("assets/data/area_area1.txt", "area_area1", Area.class);
           

            //Movie files
            assetManager.loadAsset("assets/data/movies/cool_movie.txt", "cool_movie", Movie.class);

            GameWindow window = new AWTGameWindow("Ludum Dare 32", 800, 600);
            TimedGameStateRunner runner = new TimedGameStateRunner(window, assetManager);
            /*
            runner.setState(new MainMenuState());
            */
            runner.setState(new TransitionState(assetManager.getAsset("cool_movie", Movie.class), new MainMenuState()));
            runner.loop();
	}

}
