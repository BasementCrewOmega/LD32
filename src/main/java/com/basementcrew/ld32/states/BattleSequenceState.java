/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiGroup;
import com.basementcrew.ld32.data.Attack;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.PlayerData;
import com.basementcrew.ld32.data.Weapon;
import java.awt.Graphics;
import java.awt.Point;
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
    
    private int turnCount;
    
    //Enemy attack management
    private int[] enemyAttackTiming = null;
    private int enemyAttackProgress = 0; //Milliseconds the attack has been occuring
    private Attack enemyAttack;
    
    //Player attack management
    private int[] playerAttackTiming = null;
    private int playerAttackProgress = 0;
    private Weapon playerWeapon;
    private int[] cooldownCounters;
    
    //Player animations
    /** 
     * The animation for the player. 
     * The weapon's animation may be drawn on top of this.
     */
    private Animation playerAnimation;
    private Point playerRenderPosition;
    
    //Other
    private boolean dodgedEnemyAttack = false;
    private boolean enemyAttackInRegion = false;
    
    private Gui gui = new Gui();
    
    public BattleSequenceState(Enemy fighting, PlayerData playerData, BufferedImage backgroundImage) {
        this.playerData = playerData;
        this.fighting = fighting;
        this.backgroundImage = backgroundImage;
        
        enemyAttack = fighting.getAttack(0);
        playerWeapon = null;
        if (playerData.getWeapons().size() > 0) {
            cooldownCounters = new int[playerData.
                    getWeapons().
                    size()];
        }
        
    }
    
    @Override
    public void update(long dt) {
        //Update cooldown counters. When the cooldownCounter == the weapon
        //cooldown then the weapon is available to be used
        if (playerData.getWeapons() != null && !playerData.getWeapons().isEmpty()) { // only works when you atually have weapons
            for (int i=0; i<cooldownCounters.length; i++) {
                cooldownCounters[i] += dt;
                //Stop the cooldown from going over 100%
                if (playerData.getWeapons(). get(i).getCooldown() > cooldownCounters[i]) {
                    cooldownCounters[i] = playerData.getWeapons().get(i).getCooldown();
                }
            }
        }
        
        // start doing the attack after the player's weapon is chosen
        if (playerWeapon != null) {
            //Handle player attack timing
            if (playerAttackTiming != null) {
                playerAttackProgress += dt;
                if (inTimingRegion(playerAttackProgress, playerAttackTiming)) {
                    //Got the correct timing, do the effect

                }
            }
        }
        
        //Handle enemy attack timing
        if (enemyAttackTiming != null) {
            enemyAttackProgress += dt;
            if (enemyAttackInRegion && 
                    !(enemyAttackInRegion = inTimingRegion(enemyAttackProgress, enemyAttackTiming))) {
                //Just left the region, deal damage if the player hasn't dodged
                if (!dodgedEnemyAttack) {
                    playerData.setHealth(playerData.getHealth() - enemyAttack.getDamage()); // take damage
                } 
            }
        }
        
        // update the animations
        if (playerAnimation != null)
            playerAnimation.update();
    }
    
    /**
     * Checks to see if the point in time is inside a region of time in
     * the "timing" array.
     * <p>
     * The "timing" array takes the form of { min, max, min, max } where each
     * min, max pair defines a region.
     * @param time the point in time to test
     * @param timing the array that defines the regions of time that make 
     * this check true.
     * @return if the given point of time is in any region of "timing."
     */
    public boolean inTimingRegion(int time, int[] timing) {
        for (int i=0; i<timing.length; i += 2) {
            if (time > timing[i] && time < timing[i+1]) {
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
        
        //Draw the player
        if (playerAnimation != null && playerAnimation.getCurrentImage() != null) {
            g.drawImage(playerAnimation.getCurrentImage(), 
                    (int)playerRenderPosition.getX(), (int)playerRenderPosition.getY(), null);
        }
        
        //Draw the enemy
        
    }
    
    @Override
    public void onEnter() {
        lowerMenuBackground = getImage("lowerMenuBackground");
        selector = getImage("selector");
        
        playerAnimation = getAssetManager().getAsset("player", Animation.class);
        playerAnimation.setTrack(0); // the idle animations
        playerAnimation.update(); // to set the image
        playerRenderPosition = new Point(100, 100); // can change because you might be moving to hit the enemy
        
        turnCount = 0;
        GuiGroup main = new GuiGroup();
        //Make the health bars and other Gui elements
        
        
    }

    @Override
    public void onExit() {
    }

    @Override
    public void key(int keycode, boolean pressed) {
        if (keycode == KeyCode.KEY_SPACE && pressed) {
            if (playerAttackTiming != null) {
                if (inTimingRegion(playerAttackProgress, playerAttackTiming)) {
                    //Do special effect for the current player attack
                    
                }
            }
            if (enemyAttackTiming != null) {
                if (inTimingRegion(enemyAttackProgress, enemyAttackTiming)) {
                    //Dodge the enemy attack
                    System.out.println("Dodged the attack");
                    dodgedEnemyAttack = true;
                }
            }
        }
    }    
}
