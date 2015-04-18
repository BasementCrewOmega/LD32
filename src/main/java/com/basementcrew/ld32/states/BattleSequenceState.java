/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiGroup;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.PlayerData;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * The main state - the state where you go through the sequence of the battle
 * @author Kevin
 */
public class BattleSequenceState extends TimedGameState {

    private PlayerData playerData;
    private Enemy fighting;
    private BufferedImage lowerMenuBackground;
    private BufferedImage selector;
    private BufferedImage backgroundImage;
    
    private Gui gui = new Gui();
    
    public BattleSequenceState(Enemy fighting, PlayerData playerData, BufferedImage backgroundImage) {
        this.playerData = playerData;
        this.fighting = fighting;
        this.backgroundImage = backgroundImage;
    }
    
    @Override
    public void update(long dt) {
    }

    @Override
    public void render(Object o) {
        Graphics g = (Graphics)o;
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(lowerMenuBackground, 0, 350, null);
        
    }

    @Override
    public void onEnter() {
        lowerMenuBackground = getImage("lowerMenuBackground");
        selector = getImage("selector");
        
        GuiGroup main = new GuiGroup();
        //Make the health bars and other Gui elements
        
    }

    @Override
    public void onExit() {
    }
    
}
