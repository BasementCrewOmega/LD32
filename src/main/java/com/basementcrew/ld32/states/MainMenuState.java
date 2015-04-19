/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiButton;
import bropals.lib.simplegame.gui.GuiButtonAction;
import bropals.lib.simplegame.gui.GuiGroup;
import bropals.lib.simplegame.gui.GuiImage;
import bropals.lib.simplegame.state.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.sound.midi.Sequence;

import com.basementcrew.ld32.MusicPlayer;

/**
 * The main menu
 * @author Kevin
 */
public class MainMenuState extends GameState {

    private Gui gui = new Gui();
    
    @Override
    public void update() {
        Point mouse = getWindow().getMousePosition();
        if (mouse!=null) {
            gui.update(mouse.x, mouse.y);
        }
    }

    @Override
    public void render(Object o) {
        Graphics g = (Graphics)o;
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 800, 600);
        
        gui.render(o);
    }

    @Override
    public void onEnter() {
        GuiGroup group = new GuiGroup();
        GuiButton playButton = new GuiButton(80, 300, 120, 80,
                getAssetManager().getImage("playUp"),
                getAssetManager().getImage("playUp"),
                getAssetManager().getImage("playHover"),
                new PlayButton()
        );
        GuiButton quitButton = new GuiButton(80, 450, 120, 80,
                getAssetManager().getImage("quitUp"),
                getAssetManager().getImage("quitUp"),
                getAssetManager().getImage("quitHover"),
                new QuitButton()
        );
        GuiImage title = new GuiImage(0, 0, 800, 200, getAssetManager().getImage("titleBanner"));
        group.addElement(playButton);
        group.addElement(quitButton);
        group.addElement(title);
        gui.addGroup("main", group);
        gui.enable("main");
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        gui.mouseInput(x, y);
    }
        
    @Override
    public void onExit() {
    
    }

    class QuitButton implements GuiButtonAction {
        @Override
        public void onButtonPress() {
            //When the player pressed quit
            //Quit
            getWindow().requestToClose();
            System.exit(0);
            
        }
    }
    
    class PlayButton implements GuiButtonAction {
        @Override
        public void onButtonPress() {
            //When the player presses play
            playIntroMovie();
            startGame();
        }
    }
    
    public void playIntroMovie() {
        
    }
    
    public void startGame() {
        TownState townState = new TownState();
        
        getGameStateRunner().setState(townState);
        
        Sequence seq = getAssetManager().getAsset("main_song", Sequence.class);
		MusicPlayer player = MusicPlayer.getInstance();
		player.play(seq, true, 0, 15, 25530);
    }
}
