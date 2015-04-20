/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class HelpState extends GameState {

    private BufferedImage help;
    
    @Override
    public void update() {
    }

    @Override
    public void render(Object o) {
        Graphics g = (Graphics)o;
        g.drawImage(help, 0, 0, null);
    }

    @Override
    public void onEnter() {
        help = getAssetManager().getImage("help");
    }

    @Override
    public void onExit() {
    }

    @Override
    public void key(int keycode, boolean pressed) {
        if (keycode == KeyCode.KEY_SPACE && pressed) {
            getGameStateRunner().setState(new MainMenuState());
        }
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        if (pressed) {
            getGameStateRunner().setState(new MainMenuState());
        }
    }  
    
}
