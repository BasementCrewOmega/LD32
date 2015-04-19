/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiButton;
import bropals.lib.simplegame.gui.GuiButtonAction;
import bropals.lib.simplegame.gui.GuiGroup;
import com.basementcrew.ld32.data.Attack;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.PlayerData;
import com.basementcrew.ld32.data.Weapon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
    
    // variables to manage the turns
    private int turnCount;
    private boolean playerTurn; // true when the player's turn; false when the enemy's turn
    /**
     * Keeps track of when the current attack has started.
     */
    private long startAttackTime;
    
    //Enemy attack management
    private int[] enemyAttackTiming = null;
    private int enemyAttackProgress = 0; //Milliseconds the attack has been occuring
    private Attack enemyAttack;
    private int enemyMaxHealth;
    
    //Player attack management
    private int[] playerAttackTiming = null;
    /** How long the player's attack has been taking place.
        Used to make sure the player hits the spacebar at the right time*/
    private int playerAttackProgress = 0;
    /** The weapon the player as chosen for that attack*/
    private Weapon playerWeapon;
    private int playerMaxHealth;
    /** An array to keep track of the cooldowns*/
    private int[] cooldownCounters;
    
    private GuiGroup abilityButtonsGroup;
    /** The array of buttons for selecting what ability you'll do */
    private ArrayList<GuiButton> abilityButtons;
    private ArrayList<ChosePlayerAbilityAction> abilityActions;
    
    //Player animations
    /** 
     * The animation for the player. 
     * The weapon's animation may be drawn on top of this.
     */
    private Animation playerAnimation;
    private Point playerRenderPosition;
    
    //Enemy animations
    /** 
     * The animation for the enemy. 
     */
    private Animation enemyAnimation;
    private Point enemyRenderPosition;
    
    //Other
    private boolean dodgedEnemyAttack = false;
    private boolean pressedKeyInRegion = false;
    private boolean alreadyPressedAKey = false;
    
    private Gui gui = new Gui();
    
    public BattleSequenceState(Enemy fighting, PlayerData playerData, BufferedImage backgroundImage) {
        this.playerData = playerData;
        this.fighting = fighting;
        this.backgroundImage = backgroundImage;
        
        enemyAttack = fighting.getAttack(0);
        playerWeapon = null;
        if (playerData.getWeapons().size() > 0) {
            cooldownCounters = new int[playerData.getWeapons().size()];
        }
        
    }
    
    @Override
    public void update(long dt) {
        Point mousePos = getWindow().getMousePosition();
        gui.update((int)mousePos.getX(), (int)mousePos.getY());
        
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
        
        if (playerTurn) { // is it the players turn?
            if (playerWeapon != null) { // has the player chosen a weapon?
                System.out.println("The weapon is chosen");
                if (abilityButtonsGroup.isEnabled()) { // remove the gui for chosing abilities after you chose an ability
                    abilityButtonsGroup.setEnabled(false);
                }
                
                if (playerAttackTiming == null) {
                    playerAttackTiming = new int[]{playerWeapon.getTimingStart(0),
                        playerWeapon.getTimingEnd(0), playerWeapon.getTimingEntireEnded(0)};
                }
                playerAttackProgress += dt; // update how long the attack has been going on for.
                if (playerAttackProgress > 
                        startAttackTime + playerAttackTiming[2]) { // see if the attack is done yet
                    // finish the ability
                    System.out.println("Player has attacked the player");
                    fighting.damage(playerWeapon.getAttackDamage()); // do the damage
                    alreadyPressedAKey = false; // reset the lock after the ability has finished
                    pressedKeyInRegion = false;
                    playerTurn = false;
                    playerWeapon = null;
                    playerAttackTiming = null;
                } else { // if the attack is not done yet
                    if (pressedKeyInRegion) { // see if the player has pressed the key at the right time
                        // bonus effect!
                        System.out.println("This is our special effect");
                        playerWeapon.getEffect().doEffect(playerWeapon, fighting, playerData);
                        pressedKeyInRegion = false; // so it can't happen again for this attack
                    }
                }
            } else {
                playerAttackProgress = 0; // reset the progress when there is none
                if (!abilityButtonsGroup.isEnabled()) { // enable the GUI when you don't have a weapon
                    abilityButtonsGroup.setEnabled(true);
                }
            }
        } else { // the enemy's turn
            if (enemyAttack != null) { // does the enemy have an attack?
                if (enemyAttackTiming == null) {
                    enemyAttackTiming = new int[]{enemyAttack.getTimingStart(0),
                      enemyAttack.getTimingEnd(0), enemyAttack.getTimingEntireEnded(0)};
                }
                enemyAttackProgress += dt;
                if (enemyAttackProgress >
                        startAttackTime + enemyAttackTiming[2]) {
                    if (!dodgedEnemyAttack) {
                        // get damaged when you don't dodge
                        playerData.setHealth(playerData.getHealth() - enemyAttack.getDamage());
                        playerTurn = true; // it's now the player's turn
                        enemyAttackTiming = null;
                    }
                } else { // the attack is not done yet
                    if (pressedKeyInRegion) {
                        dodgedEnemyAttack = true;
                    }
                }
            } else {
                // need to chose the attack they're doing
                enemyAttack = fighting.getRandomAttack();
                enemyAttackTiming = new int[]{
                  enemyAttack.getTimingStart(0), enemyAttack.getTimingEnd(0)
                };
                enemyAttackProgress = 0; // reset the progress when there is no attack
                dodgedEnemyAttack = false; // reset that you dodged the attack
            }
        }
        
        // update the animations
        if (playerAnimation != null)
            playerAnimation.update();
        
        if (enemyAnimation != null) 
            enemyAnimation.update();
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
        
        //Draw the player
        if (playerAnimation != null && playerAnimation.getCurrentImage() != null) {
            g.drawImage(playerAnimation.getCurrentImage(), 
                    (int)playerRenderPosition.getX(), (int)playerRenderPosition.getY(), null);
        }
        
        //Draw the enemy
        
        
        
        // debug drawing
        if (playerTurn) {
            if (playerAttackTiming != null) {
                g.setColor(Color.RED);
                if (inTimingRegion(playerAttackProgress, playerAttackTiming)) {
                    g.setColor(Color.GREEN);
                }
                g.fillRect((int)playerRenderPosition.getX(), 
                        (int)playerRenderPosition.getY(), 200, 50);
            }
        } else {
            if (enemyAttackTiming != null && inTimingRegion(enemyAttackProgress, enemyAttackTiming)) {
                g.setColor(Color.BLUE);
                g.fillRect((int)enemyRenderPosition.getX(), 
                        (int)enemyRenderPosition.getY(), 200, 50);
            }
        }
        
        g.drawImage(lowerMenuBackground, 0, 350, null);
        
        gui.render(o);
        abilityButtonsGroup.render(o);
        for (GuiButton gb : abilityButtons) {
//            System.out.println(gb.toString());
//            System.out.println(gb.getX() + ", " + gb.getY() + ", " + 
//                    gb.getWidth() + ", " + gb.getHeight());
           
            g.setColor(Color.BLACK);
            g.fillRect(gb.getX(), gb.getY(), 
                    gb.getWidth(), gb.getHeight());
            gb.render(o);
        }
        
        // draw the health bar for the player
        g.drawImage(getAssetManager().getImage("healthBar"), 
                (int)playerRenderPosition.getX(),
                (int)playerRenderPosition.getY() - 30, null);
        g.setColor(Color.RED);
        // 144x24
        g.fillRect((int)playerRenderPosition.getX() + 3, 
                (int)playerRenderPosition.getY() - 27, 
                (int)(144 * ((double)playerData.getHealth() / playerMaxHealth)), 
                24);
        
        
        // draw the health bar for the enemy
        g.drawImage(getAssetManager().getImage("healthBar"), 
                (int)enemyRenderPosition.getX(),
                (int)enemyRenderPosition.getY() - 30, null);
        g.setColor(Color.RED);
        // 144x24
        g.fillRect((int)enemyRenderPosition.getX() + 3, 
                (int)enemyRenderPosition.getY() - 27, 
                (int)(144 * ((double)fighting.getHealth() / fighting.getHealth())), 
                24);
    }
    
    @Override
    public void onEnter() {
        lowerMenuBackground = getImage("lowerMenuBackground");
        selector = getImage("selector");
        
        playerAnimation = getAssetManager().getAsset("player", Animation.class);
        playerAnimation.setTrack(0); // the idle animations
        playerAnimation.update(); // to set the image
        playerRenderPosition = new Point(60, 100); // can change because you might be moving to hit the enemy
        
        enemyAnimation = fighting.getAnimation();
        enemyAnimation.setTrack(0);
        enemyAnimation.update();
        enemyRenderPosition = new Point(500, 100);
        
        turnCount = 0;
        GuiGroup main = new GuiGroup();
        //Make the health bars and other Gui elements
        
        // Gui elements for selecting weapons.
        abilityButtonsGroup = new GuiGroup();
        abilityButtons = new ArrayList<>();
        abilityActions = new ArrayList<>();
        for (int i=0; i<playerData.getWeapons().size(); i++) {
            System.out.println("Adding a weapon BUTTON");
            ChosePlayerAbilityAction action = new ChosePlayerAbilityAction(i);
            GuiButton newGuiButton = new GuiButton(250, 390 + (i * 40),
                240, 30, getAssetManager().getImage("abilityButtonTemplate"), 
                    getAssetManager().getImage("abilityButtonTemplate"), 
                    getAssetManager().getImage("abilityButtonTemplateHover"), 
                    action); // make a button to chose the weapon
            abilityButtonsGroup.addElement(newGuiButton);
            abilityButtons.add(newGuiButton);
            abilityActions.add(action);
        }
        
        // set the max healths
        playerMaxHealth = playerData.getHealth();
        enemyMaxHealth = fighting.getHealth();
    }

    class ChosePlayerAbilityAction implements GuiButtonAction {

        private int indexNum;
        
        public ChosePlayerAbilityAction(int indexNum) {
            this.indexNum = indexNum;
        }
        
        @Override
        public void onButtonPress() {
            System.out.println("THIS IS BEING PRESSED ITS SO COOL");
            if (playerTurn && playerWeapon == null) { // if it's the player's turn and there is no weapon yet
                playerWeapon = playerData.getWeapons().get(indexNum);
            }
        }
        
    }
    
    @Override
    public void onExit() {
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        //System.out.println(mousebutton);
        if (mousebutton == 0) {
            if (pressed) {
                System.out.println("This totally happened");
                System.out.println(x + ", " + y);
                for (GuiButton gb : abilityButtons) {
                    gb.mouseInput(x, y);
                }
            }
        }
    }

    
    @Override
    public void key(int keycode, boolean pressed) {
        if (keycode == KeyCode.KEY_SPACE && pressed) {
            if (!alreadyPressedAKey) {
                if (playerAttackTiming != null) { // if the player was attacking
                    if (inTimingRegion(playerAttackProgress, playerAttackTiming)) {
                        pressedKeyInRegion = true;
                    }
                } else if (enemyAttackTiming != null) { // if the enemy was attacking
                    if (inTimingRegion(enemyAttackProgress, enemyAttackTiming)) {
                        //Dodge the enemy attack
                        System.out.println("Dodged the attack");
                        dodgedEnemyAttack = true;
                    }
                }
                alreadyPressedAKey = true;
            }
            
        }
    }    
}
