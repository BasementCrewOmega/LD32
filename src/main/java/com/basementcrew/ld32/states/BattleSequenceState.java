/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiGroup;
import bropals.lib.simplegame.logger.InfoLogger;
import com.basementcrew.ld32.data.Effect;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.PlayerData;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    private boolean playersTurn; //Wait the game is turn based right?
    private int delayBetweenActions = 1000;
    private int actionCounter = 0;
    private int[] currentTimings;
    private int timingCounter = 0;
    private int lastPressedSpace = 0; //The timingCounter value when the player pressed space.
    
    //Current status effects of each side.
    private ArrayList<Effect> playerEffects = new ArrayList<>();
    private ArrayList<Effect> enemyEffects = new ArrayList<>();
    
    //Attack selectors for each side
    private int enemyAttack = 0;
    private int playerWeapon = 0;
    
    private Gui gui = new Gui();
    
    public BattleSequenceState(Enemy fighting, PlayerData playerData, BufferedImage backgroundImage) {
        this.playerData = playerData;
        this.fighting = fighting;
        this.backgroundImage = backgroundImage;
    }
    
    @Override
    public void update(long dt) {
        if (currentTimings == null) {
            if (!playersTurn) {
                actionCounter += dt;
                if (actionCounter > delayBetweenActions) {
                    //Have the enemy do their action
                    //Set the timings variable
                }
            } else {
                //Its the players turn, wait for them to do their action.
                //Have the player set the timings variable when its ready
            }
        } else {
            //Timings exists, which means that the player is currently 
            //Trying to time something
            
            timingCounter += dt;
            
            if (!playersTurn) {
                //Enemy is attacking.
                //Check spacebar press with region.
                if (inTimingRegion(lastPressedSpace, currentTimings)) {
                    //Dodge the attack.
                    InfoLogger.println("Dodged");
                }
            } else {
                //Player is attacking.
                //Check spacebar press with region.
                if (inTimingRegion(lastPressedSpace, currentTimings)) {
                    //Do the special effect of the attack.
                    InfoLogger.println("Special hit");
                }
            }
            
            //See if the animations are done, and end the timing event if they
            //are
            if (!playersTurn) {
                //Did the enemy finish attacking?
                if (timingCounter >= fighting.getAttack(enemyAttack).getAnimation().getTrackOn().getTotalTrackTime()) {
                    currentTimings = null;
                }
            } else {
                //Did the player finish attacking?
                //What is the attack animation for the player?
            }
        }
    }
    
    public boolean inTimingRegion(int space, int[] timing) {
        for (int i=0; i<timing.length; i += 2) {
            if (space > timing[i] && space < timing[i+1]) {
                return true;
            }
        }
        return false;
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
