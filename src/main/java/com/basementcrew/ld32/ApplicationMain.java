package com.basementcrew.ld32;

import javax.sound.midi.Sequence;

import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.QuitHandler;
import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.ErrorLogger;
import bropals.lib.simplegame.logger.InfoLogger;

import com.basementcrew.ld32.data.Area;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.PlayerData;
import com.basementcrew.ld32.data.Weapon;
import com.basementcrew.ld32.data.loaders.AnimationLoader;
import com.basementcrew.ld32.data.loaders.AreaLoader;
import com.basementcrew.ld32.data.loaders.EnemyLoader;
import com.basementcrew.ld32.data.loaders.MovieLoader;
import com.basementcrew.ld32.data.loaders.PlayerDataLoader;
import com.basementcrew.ld32.data.loaders.SequenceLoader;
import com.basementcrew.ld32.data.loaders.WeaponLoader;
import com.basementcrew.ld32.movie.Movie;
import com.basementcrew.ld32.states.MainMenuState;
import com.basementcrew.ld32.states.TimedGameStateRunner;
import com.basementcrew.ld32.states.TransitionState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.util.HashMap;
import java.util.Map;

public class ApplicationMain {
    public static Map<String, Object> flags = new HashMap<String, Object>();

    public static void main(String[] args) {
        InfoLogger.setSilent(true);
        AssetManager assetManager = new AssetManager(ApplicationMain.class, true);

        //Splash screen
        SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            ErrorLogger.println("No splash screen");
        }
        Graphics2D g = null;
        if (splash != null) {
            assetManager.loadImage("assets/img/splash.png", "splash");
            g = splash.createGraphics();
            if (g != null) {
                g.drawImage(assetManager.getImage("splash"), 0, 0, (int) splash.getSize().getWidth(), (int) splash.getSize().getHeight(), null);
                g.setColor(Color.BLUE);
                splash.update();
            }
        }

        //Register the loaders
        assetManager.addAssetLoader(new AnimationLoader(assetManager), Animation.class);
        assetManager.addAssetLoader(new AreaLoader(assetManager), Area.class);
        assetManager.addAssetLoader(new EnemyLoader(assetManager), Enemy.class);
        assetManager.addAssetLoader(new MovieLoader(assetManager), Movie.class);
        assetManager.addAssetLoader(new PlayerDataLoader(assetManager), PlayerData.class);
        assetManager.addAssetLoader(new WeaponLoader(assetManager), Weapon.class);
        assetManager.addAssetLoader(new SequenceLoader(), Sequence.class);

        //Load all assets
        assetManager.loadImage("assets/img/mainMenu/playDown.png", "playDown");
        assetManager.loadImage("assets/img/mainMenu/playUp.png", "playUp");
        assetManager.loadImage("assets/img/mainMenu/playHover.png", "playHover");
        assetManager.loadImage("assets/img/mainMenu/quitDown.png", "quitDown");
        assetManager.loadImage("assets/img/mainMenu/quitUp.png", "quitUp");
        assetManager.loadImage("assets/img/mainMenu/quitHover.png", "quitHover");
        assetManager.loadImage("assets/img/mainMenu/titleBanner.png", "titleBanner");
        assetManager.loadImage("assets/img/mainMenu/mainMenuBackground.png", "mainMenuBackground");

        if (splash != null && g != null) {
            g.fillRect(26, 189, 25, 28); //Max size is 350
            splash.update();
        }
        
        assetManager.loadImage("assets/img/townMenu/shopDown.png", "shopDown");
        assetManager.loadImage("assets/img/townMenu/shopHover.png", "shopHover");
        assetManager.loadImage("assets/img/townMenu/shopUp.png", "shopUp");
        assetManager.loadImage("assets/img/townMenu/toTownDown.png", "toTownDown");
        assetManager.loadImage("assets/img/townMenu/toTownHover.png", "toTownHover");
        assetManager.loadImage("assets/img/townMenu/toTownUp.png", "toTownUp");
        assetManager.loadImage("assets/img/townMenu/townBackground.png", "townBackground");
        assetManager.loadImage("assets/img/townMenu/swamp_icon.png", "swamp_icon");
        assetManager.loadImage("assets/img/townMenu/fire_icon.png", "fire_icon");
        assetManager.loadImage("assets/img/townMenu/ice_icon.png", "ice_icon");
        assetManager.loadImage("assets/img/townMenu/savanna_icon.png", "savanna_icon");

        if (splash != null && g != null) {
            g.fillRect(26, 189, 70, 28); //Max size is 350
            splash.update();
        }
        
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
        assetManager.loadImage("assets/img/battleSequence/abilityButtonTemplate.png", "abilityButtonTemplate");
        assetManager.loadImage("assets/img/battleSequence/abilityButtonTemplateHover.png", "abilityButtonTemplateHover");
        assetManager.loadImage("assets/img/battleSequence/abilityButtonFists.png", "fist_button");
        assetManager.loadImage("assets/img/battleSequence/abilityButtonFistsHover.png", "hover_fist_button");
            
        if (splash != null && g != null) {
            g.fillRect(26, 189, 120, 28); //Max size is 350
            splash.update();
        }
        
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

        if (splash != null && g != null) {
            g.fillRect(26, 189, 160, 28); //Max size is 350
            splash.update();
        }
        
        assetManager.loadImage("assets/img/movies/introMovieBackground1.png", "introMovieBackground1");
        assetManager.loadImage("assets/img/movies/introMovieBackground2.png", "introMovieBackground2");
        assetManager.loadImage("assets/img/movies/introMountains.png", "introMountains");
        assetManager.loadImage("assets/img/movies/dieSonne.png", "dieSonne");
        assetManager.loadImage("assets/img/movies/text1.png", "text1");
        assetManager.loadImage("assets/img/movies/text2.png", "text2");
        assetManager.loadImage("assets/img/movies/text3.png", "text3");
        assetManager.loadImage("assets/img/movies/burningTown.png", "burningTown");
        assetManager.loadImage("assets/img/movies/mountainsStatic.png", "mountainsStatic");
        assetManager.loadImage("assets/img/movies/darkFace.png", "darkFace");
        assetManager.loadImage("assets/img/movies/verses.png", "verses");
        
        if (splash != null && g != null) {
            g.fillRect(26, 189, 200, 28); //Max size is 350
            splash.update();
        }

        //Load animations
        assetManager.loadAsset("assets/data/animation/goblin.animation", "goblin", Animation.class);
        assetManager.loadAsset("assets/data/animation/yeti.animation", "yeti", Animation.class);
        assetManager.loadAsset("assets/data/animation/warthog.animation", "warthog", Animation.class);
        assetManager.loadAsset("assets/data/animation/imp.animation", "imp", Animation.class);
        assetManager.loadAsset("assets/data/animation/player.animation", "player", Animation.class);
        assetManager.loadAsset("assets/data/animation/introMountains.animation", "introMountains", Animation.class);
        assetManager.loadAsset("assets/data/animation/burningBuildings.animation", "burningBuildings", Animation.class);
        assetManager.loadAsset("assets/data/animation/verses.animation", "verses", Animation.class);
        
        if (splash != null && g != null) {
            g.fillRect(26, 189, 220, 28); //Max size is 350
            splash.update();
        }

        //Load enemies
        assetManager.loadAsset("assets/data/enemy/goblin.enemy", "goblin", Enemy.class);
        assetManager.loadAsset("assets/data/enemy/yeti.enemy", "yeti", Enemy.class);
        assetManager.loadAsset("assets/data/enemy/warthog.enemy", "warthog", Enemy.class);
        assetManager.loadAsset("assets/data/enemy/imp.enemy", "imp", Enemy.class);

        if (splash != null && g != null) {
            g.fillRect(26, 189, 240, 28); //Max size is 350
            splash.update();
        }

        //Load areas
        assetManager.loadAsset("assets/data/area/fire.area", "fire", Area.class);
        assetManager.loadAsset("assets/data/area/ice.area", "ice", Area.class);
        assetManager.loadAsset("assets/data/area/savanna.area", "savanna", Area.class);
        assetManager.loadAsset("assets/data/area/swamp.area", "swamp", Area.class);

        if (splash != null && g != null) {
            g.fillRect(26, 189, 280, 28); //Max size is 350
            splash.update();
        }

        //Load weapons
        assetManager.loadAsset("assets/data/weapon/fist.weapon", "fist", Weapon.class);

        if (splash != null && g != null) {
            g.fillRect(26, 189, 300, 28); //Max size is 350
            splash.update();
        }

        //Load player data
        assetManager.loadAsset("assets/data/playerdata/default.playerdata", "default_playerdata", PlayerData.class);

        if (splash != null && g != null) {
            g.fillRect(26, 189, 310, 28); //Max size is 350
            splash.update();
        }

        //Load Movies
        assetManager.loadAsset("assets/data/movie/intro.movie", "intro", Movie.class);
        assetManager.loadAsset("assets/data/movie/enter_battle_goblin.movie", "enter_battle_goblin", Movie.class);
        assetManager.loadAsset("assets/data/movie/enter_battle_imp.movie", "enter_battle_imp", Movie.class);
        assetManager.loadAsset("assets/data/movie/enter_battle_yeti.movie", "enter_battle_yeti", Movie.class);
        assetManager.loadAsset("assets/data/movie/enter_battle_warthog.movie", "enter_battle_warthog", Movie.class);
        assetManager.loadAsset("assets/data/movie/win_battle.movie", "win_battle", Movie.class);
        assetManager.loadAsset("assets/data/movie/lose_battle.movie", "lose_battle", Movie.class);
        assetManager.loadAsset("assets/data/movie/win_game.movie", "win_game", Movie.class);

        if (splash != null && g != null) {
            g.fillRect(26, 189, 330, 28); //Max size is 350
            splash.update();
        }

        // Load Music
        assetManager.loadAsset("assets/music/song_20150419_054852_239.mid", "main_song", Sequence.class);

        if (splash != null && g != null) {
            g.fillRect(26, 189, 350, 28); //Max size is 350
            splash.update();

            //Done loading
            g.dispose();
            splash.close();
        }
        
        GameWindow window = new AWTGameWindow("Ludum Dare 32", 800, 600);
        window.registerQuitHandler(new QuitHandler() {
			@Override
			public void onQuit() {
				// Otherwise game doesn't quit
				MusicPlayer.getInstance().shutdown();
			}
		});
        
        TimedGameStateRunner runner = new TimedGameStateRunner(window, assetManager);

        /*
         runner.setState(new MainMenuState());
         */
                
        // Skip constant caller arg TODO: Replace with public domain flag parsing code
        for (int i = 0; i < args.length; i++) {
        	String param = args[i];
        	param = param.trim();
        	if (param.charAt(0) == '-') {
        		String key = param.substring(1); // -key
        		if (key.indexOf('=') > 0) {						// -key=value 
        			key = key.substring(0, key.indexOf('='));	// TODO: if value is a "string" that might break this type
        			String value = key.substring(key.indexOf('=') + 1);
        			flags.put(key, value);
        		} else if ((i+1 < args.length) && args[i+1].charAt(0) != '-') {	// -key value
        			String value = args[i+1];
        			i++; // Skip next arg since we already handle it as a value
        			flags.put(key, value);
        		} else {									// -key (flag)
        			flags.put(key, true);
        		}
        	}
        }
        
        runner.setState(new TransitionState(assetManager.getAsset("intro", Movie.class), new MainMenuState()));
        try {
            runner.loop();
        } catch (Exception e) {
            System.out.println("THERE WAS AN EXCEPTION \n CLOSING WINDOW");
            e.printStackTrace();
        }
        window.destroy();

    }

}
