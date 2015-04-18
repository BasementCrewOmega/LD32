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
            assetManager.loadImage("assets/img/townMenu/swamp_icon.png", "swamp_icon");
            assetManager.loadImage("assets/img/townMenu/fire_icon.png", "fire_icon");
            assetManager.loadImage("assets/img/townMenu/ice_icon.png", "ice_icon");
            assetManager.loadImage("assets/img/townMenu/savanna_icon.png", "savanna_icon");
            
            assetManager.loadImage("assets/img/battleSequence/cooldownBar.png", "cooldownBar");
            assetManager.loadImage("assets/img/battleSequence/cooldownBarBackground.png", "cooldownBarBackground");
            assetManager.loadImage("assets/img/battleSequence/healthBar.png", "healthBar");
            assetManager.loadImage("assets/img/battleSequence/healthBarBackground.png", "healthBarBackground");
            assetManager.loadImage("assets/img/battleSequence/selector.png", "selector");
            assetManager.loadImage("assets/img/battleSequence/lowerMenuBackground.png", "lowerMenuBackground");
            assetManager.loadImage("assets/img/battleSequence/swamp_background.png", "swamp_background");
            assetManager.loadImage("assets/img/battleSequence/ice_background.png", "ice_background");
            assetManager.loadImage("assets/img/battleSequence/fire_background.png", "fire_background");
            assetManager.loadImage("assets/img/battleSequence/savanna_background.png", "savanna_background");
            
            assetManager.loadImage("assets/img/entity/goblin_idle.png", "goblin_idle");
            assetManager.loadImage("assets/img/entity/goblin_attack.png", "goblin_attack");
            assetManager.loadImage("assets/img/entity/yeti_idle.png", "yeti_idle");
            assetManager.loadImage("assets/img/entity/yeti_attack.png", "yeti_attack");
            assetManager.loadImage("assets/img/entity/warthog_idle.png", "warthog_idle");
            assetManager.loadImage("assets/img/entity/warthog_attack.png", "warthog_attack");
            assetManager.loadImage("assets/img/entity/imp_idle.png", "imp_idle");
            assetManager.loadImage("assets/img/entity/imp_attack.png", "imp_attack");
            assetManager.loadImage("assets/img/entity/player_idle.png", "player_idle");
            assetManager.loadImage("assets/img/entity/player_attack.png", "player_attack");
            
            //Load animations
            assetManager.loadAsset("assets/data/animation/goblin_attack.animation", "goblin_attack", Animation.class);
            assetManager.loadAsset("assets/data/animation/goblin_idle.animation", "goblin_idle", Animation.class);
            assetManager.loadAsset("assets/data/animation/yeti_attack.animation", "yeti_attack", Animation.class);
            assetManager.loadAsset("assets/data/animation/yeti_idle.animation", "yeti_idle", Animation.class);
            assetManager.loadAsset("assets/data/animation/warthog_attack.animation", "warthog_attack", Animation.class);
            assetManager.loadAsset("assets/data/animation/warthog_idle.animation", "warthog_idle", Animation.class);
            assetManager.loadAsset("assets/data/animation/imp_attack.animation", "imp_attack", Animation.class);
            assetManager.loadAsset("assets/data/animation/imp_idle.animation", "imp_idle", Animation.class);
            assetManager.loadAsset("assets/data/animation/player.animation", "player", Animation.class);
            
            //Load enemies
            assetManager.loadAsset("assets/data/enemy/goblin.enemy", "goblin", Enemy.class);
            assetManager.loadAsset("assets/data/enemy/yeti.enemy", "yeti", Enemy.class);
            assetManager.loadAsset("assets/data/enemy/warthog.enemy", "warthog", Enemy.class);
            assetManager.loadAsset("assets/data/enemy/imp.enemy", "imp", Enemy.class);
            
            //Load areas
            assetManager.loadAsset("assets/data/area/fire.area", "fire", Area.class);
            assetManager.loadAsset("assets/data/area/ice.area", "ice", Area.class);
            assetManager.loadAsset("assets/data/area/savanna.area", "savanna", Area.class);
            assetManager.loadAsset("assets/data/area/swamp.area", "swamp", Area.class);
            
            //Load weapons
            assetManager.loadAsset("assets/data/weapon/fist.weapon", "fist", Weapon.class);
            
            
            //Load player data
            assetManager.loadAsset("assets/data/playerdata/default.playerdata", "default_playerdata", PlayerData.class);
            
            //Load Movies
            assetManager.loadAsset("assets/data/movie/intro.movie", "intro", Movie.class);
            assetManager.loadAsset("assets/data/movie/enter_battle.movie", "enter_battle", Movie.class);
            assetManager.loadAsset("assets/data/movie/win_battle.movie", "win_battle", Movie.class);
            assetManager.loadAsset("assets/data/movie/lose_battle.movie", "lose_battle", Movie.class);
            assetManager.loadAsset("assets/data/movie/win_game.movie", "win_game", Movie.class);
            
            GameWindow window = new AWTGameWindow("Ludum Dare 32", 800, 600);
            
            TimedGameStateRunner runner = new TimedGameStateRunner(window, assetManager);
            /*
            runner.setState(new MainMenuState());
            */
            runner.setState(new TransitionState(assetManager.getAsset("intro", Movie.class), new MainMenuState()));
            try {
                runner.loop();
            } catch(Exception e) {
                System.out.println("THERE WAS AN EXCEPTION \n CLOSING WINDOW");
                e.printStackTrace();
            }
            window.destroy();
            
	}

}
