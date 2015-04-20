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
import bropals.lib.simplegame.logger.ErrorLogger;
import bropals.lib.simplegame.state.GameState;
import com.basementcrew.ld32.Particle;
import com.basementcrew.ld32.data.Area;
import com.basementcrew.ld32.data.Attack;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.PlayerData;
import com.basementcrew.ld32.data.Weapon;
import com.basementcrew.ld32.movie.Movie;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The main state - the state where you go through the sequence of the battle
 *
 * @author Kevin
 */
public class BattleSequenceState extends TimedGameState {

    private PlayerData playerData;
    private Enemy fighting;
    private boolean bossFight;
    private Area areaInside;
    /**
     * Which number enemy you are currently fighting. Used to keep track of
     * enemies to the boss fight
     */
    private int enemyFightingOn;
    private BufferedImage lowerMenuBackground;
    private BufferedImage selector;
    private BufferedImage backgroundImage;
    private Particle particle;
    
    private int moveToAttackDistance;
    private int moveToAttackProgress; // how far has the player or enemy moved to reach their target so far?
    private int moveToAttackDelta; // move distance in 10 frames
    private BufferedImage projectileImage; // if this is null, then the attack is a melee attack.
                                           // otherwise, it's a range attack and the projectile is stored here.
    private int projectileStartTime, projectileDelta, projectileProgress;                                       
    
    // variables to manage the turns
    private boolean playerTurn; // true when the player's turn; false when the enemy's turn
    /**
     * Keeps track of when the current attack has started.
     */

    //Enemy attack management
    private int[] enemyAttackTiming = null;
    private int enemyAttackProgress = 0; //Milliseconds the attack has been occuring
    private Attack enemyAttack;
    private int enemyMaxHealth;

    //Player attack management
    private int[] playerAttackTiming = null;
    /**
     * How long the player's attack has been taking place. Used to make sure the
     * player hits the spacebar at the right time
     */
    private int playerAttackProgress = 0;
    /**
     * The weapon the player as chosen for that attack
     */
    private Weapon playerWeapon;
    private int playerMaxHealth;
    /**
     * An array to keep track of the cooldowns
     */
    private int[] cooldownCounters;

    private GuiGroup abilityButtonsGroup;
    /**
     * The array of buttons for selecting what ability you'll do
     */
    private ArrayList<GuiButton> abilityButtons;
    private ArrayList<ChosePlayerAbilityAction> abilityActions;

    //Player animations
    /**
     * The animation for the player. The weapon's animation may be drawn on top
     * of this.
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
    private int pressedKeyInRegion = -1; // -1 for no region, -2 for failing to press in region
    private int regionCounter; // count what region is being tested

    private Gui gui = new Gui();

    public BattleSequenceState(Enemy fighting, PlayerData playerData,
            BufferedImage backgroundImage, Area areaInsid, int enemyOn) {
        this.playerData = playerData;
        this.fighting = fighting;
        this.backgroundImage = backgroundImage;
        this.areaInside = areaInsid;
        this.bossFight = false;

        enemyAttack = fighting.getAttack(0);
        playerWeapon = null;
        if (playerData.getWeapons().size() > 0) {
            cooldownCounters = new int[playerData.getWeapons().size()];
        }
        enemyFightingOn = enemyOn;
        if (enemyFightingOn > areaInside.getEnemiesToBoss()) {
            fighting = areaInside.getBoss();
            bossFight = true;
        }
    }

    @Override
    public void update(long dt) {
        Point mousePos = getWindow().getMousePosition();
        gui.update((int) mousePos.getX(), (int) mousePos.getY());
        abilityButtonsGroup.update((int) mousePos.getX(), (int) mousePos.getY());

        //Update cooldown counters. When the cooldownCounter == the weapon
        //cooldown then the weapon is available to be used
        if (playerData.getWeapons() != null && !playerData.getWeapons().isEmpty()) { // only works when you atually have weapons
            for (int i = 0; i < cooldownCounters.length; i++) {
                cooldownCounters[i] += dt;
                //Stop the cooldown from going over 100%
                if (playerData.getWeapons().get(i).getCooldown() > cooldownCounters[i]) {
                    cooldownCounters[i] = playerData.getWeapons().get(i).getCooldown();
                }
            }
        }

        if (playerTurn) { // is it the players turn?
            if (playerWeapon != null) { // has the player chosen a weapon?
                // only pay attention to the move timers if it's a melee attack
                if (!playerWeapon.isMelee() || ((moveToAttackProgress >= moveToAttackDistance - 10) || 
                        (playerAttackProgress > playerWeapon.getTimingEntireEnded()))) { // for melee, move the player to attack
                    
                    //System.out.println("PLAYER is attacking");
                    if (abilityButtonsGroup.isEnabled()) { // remove the gui for chosing abilities after you chose an ability
                        abilityButtonsGroup.setEnabled(false);
                    }

                    if (playerAttackTiming == null) {
                        regionCounter = 0; // reset the region counter with a new atttack
                        playerAttackTiming = playerWeapon.getTimings();
                        projectileImage = playerWeapon.getProjectileImage();
                        // if there IS a projectile, then set up the intial stuff for it.
                        if (projectileImage != null) {
                            // calculate the time and stuff for the projectile
                            projectileStartTime = playerAttackTiming[0] - 100; // miliseconds
                            int timeToTake = playerAttackTiming[1] + 100 - projectileStartTime;
                            int numberOfUpdateCycles = (int)(timeToTake / dt);
                            projectileProgress = 0;
                            projectileDelta = moveToAttackDistance / numberOfUpdateCycles;
                        }
                    }

                    playerAttackProgress += dt; // update how long the attack has been going on for.
                    if (!playerWeapon.isMelee() && playerAttackProgress >= projectileStartTime) {
                        projectileProgress += projectileDelta;
                        if (projectileProgress > moveToAttackDistance) {
                            projectileImage = null; // stop drawing the projectile when it reaches it's target
                        }
                    }
                    
                    // change the animation to the track according to the weapon being used
                    if (playerAnimation.getCurrentTrackIndex() != playerWeapon.getAttackAnimationTrack()) {
                        playerAnimation.setTrack( playerWeapon.getAttackAnimationTrack()); // set the animation to the attack animation
                        playerAnimation.getTrackOn().resetCounter();
                    }
                    if (playerAttackProgress > 
                            playerWeapon.getTimingEntireEnded()) { // see if the attack is done yet
                        // finish the ability
                        playerAnimation.setTrack(0); // back to idle animation
                            playerAnimation.getTrackOn().resetCounter();
                        if (!playerWeapon.isMelee() || (moveToAttackProgress <= moveToAttackDelta)) {
                            //System.out.println("Player has attacked the enemy");
                            pressedKeyInRegion = -1; // no zone pressed
                            playerTurn = false;
                            playerWeapon = null;
                            playerAttackTiming = null;
                            projectileImage = null;
                            playerAttackProgress = 0;
                            moveToAttackProgress = 0; //reset the move distance
                        } else {
                            moveToAttackProgress -= moveToAttackDelta;
                        }
                    } else { // if the attack is not done yet
                        // track what region of time we're in right now
                        if (regionCounter != -1 && playerAttackProgress > playerWeapon.getTimingEnd(regionCounter)) {
                            regionCounter++;
                            if (regionCounter > (int)(playerAttackTiming.length/2)-1)
                                regionCounter = -1; // done with regions

                            pressedKeyInRegion = -1;
                            fighting.damage(playerWeapon.getAttackDamage()); // do the damage
                        }

                        // if you pressed the key in the right region
                        if (regionCounter!= -1 && pressedKeyInRegion == regionCounter) {
                            // bonus effect!
                            //System.out.println("you pressed the key in the right region!");
                            playerWeapon.getEffect().doEffect(playerWeapon, fighting, playerData);
                            pressedKeyInRegion = -2; // lock the interval of tiome again
                        } 
                    }
                } else {
                    moveToAttackProgress += moveToAttackDelta;
                }
            } else {
                if (!abilityButtonsGroup.isEnabled()) { // enable the GUI when you don't have a weapon
                    abilityButtonsGroup.setEnabled(true);
                }
            }
        } else { // the enemy's turn
            if (enemyAttack != null) { // does the enemy have an attack?
                // only pay attention to the movement timers if it's a melee attack
                if (!enemyAttack.isMelee() || ((moveToAttackProgress >= moveToAttackDistance - 10) || 
                        (enemyAttackProgress > enemyAttack.getTimingEntireEnded()))) { // move the enemy for the attack
                    //System.out.println("ENEMY is attacking");
                    if (enemyAttackTiming == null) {
                        regionCounter = 0; // reset the region counter with a new attack
                        enemyAttackTiming = enemyAttack.getTimings();
                        //projectileImage = the projectile for the enemy attack;
                    }

                    enemyAttackProgress += dt;
                    if (enemyAnimation.getCurrentTrackIndex() != 1) {
                        enemyAnimation.setTrack(1); // set the animation to the attack animation
                        enemyAnimation.getTrackOn().resetCounter();
                    }
                    if (enemyAttackProgress >
                            enemyAttack.getTimingEntireEnded()) {
                        enemyAnimation.setTrack(0); // back to idle animation
                            enemyAnimation.getTrackOn().resetCounter();
                        if (!enemyAttack.isMelee() || (moveToAttackProgress <= moveToAttackDelta)) {
                            //System.out.println("THe enemy has finished its attack");
                            playerTurn = true; // it's now the player's turn
                            enemyAttack = null;
                            enemyAttackTiming = null;
                            projectileImage = null;
                            dodgedEnemyAttack = false; // reset dodging the attack
                            pressedKeyInRegion = -1; // reset the status of pressing a key
                            enemyAttackProgress = 0;
                            moveToAttackProgress = 0; //reset the move distance
                        } else {
                            moveToAttackProgress -= moveToAttackDelta;
                        }
                    } else { // the attack is not done yet
                        if (regionCounter != -1 && enemyAttackProgress > enemyAttack.getTimingEnd(regionCounter)) {
                            regionCounter++;
                            if (regionCounter > (int)(enemyAttackTiming.length/2)-1)
                                regionCounter = -1; // done with regions

                            pressedKeyInRegion = -1; // undo the lock to press again
                            if (!dodgedEnemyAttack) {
                                // get damaged when you don't dodge by the end of the interval
                                playerData.setHealth(playerData.getHealth() - enemyAttack.getDamage());
                            }
                            dodgedEnemyAttack = false;
                        }

                        // if you pressed the key in the right region
                        if (regionCounter!= -1 && pressedKeyInRegion == regionCounter) {
                            //System.out.println("you pressed the key in the right region!");
                            dodgedEnemyAttack = true;
                            pressedKeyInRegion = -2; // lock the interval of tiome again
                        } 
                    }
                } else {
//                    System.out.println("Going in for the kill!");
                    moveToAttackProgress += moveToAttackDelta;
                }
            } else {
                // need to chose the attack they're doing
                enemyAttack = fighting.getRandomAttack();
            }
        }

        // update the animations
        if (playerAnimation != null) {
            playerAnimation.update((int) dt);
        }

        if (enemyAnimation != null) {
            enemyAnimation.update((int) dt);
        }

        // make the state change if you win or lose battles
        if (fighting.getHealth() <= 0) {
            // transition into a transition state.
            GameState nextState = null;

            // if you killed teh boss
            if (bossFight) {
                playerData.completeArea(areaInside.getName());
                nextState = new TownState(playerData);
            } else {
                // if you did not kill the boss
                Enemy enemy = areaInside.getRandomEnemy();
                Movie movie = null;
                switch (enemy.getName()) {
                    case "goblin":
                        movie = getAssetManager().getAsset("enter_battle_goblin", Movie.class);
                        break;
                    case "yeti":
                        movie = getAssetManager().getAsset("enter_battle_yeti", Movie.class);
                        break;
                    case "warthog":
                        movie = getAssetManager().getAsset("enter_battle_warthog", Movie.class);
                        break;
                    case "imp":
                        movie = getAssetManager().getAsset("enter_battle_imp", Movie.class);
                        break;
                    default:
                        ErrorLogger.println("No enter battle movie for " + enemy.getName());
                }
                nextState = new TransitionState(
                        getAssetManager().getAsset("win_battle", Movie.class),
                        new TransitionState(
                                movie,
                                new BattleSequenceState(enemy, playerData,
                                        backgroundImage, areaInside, enemyFightingOn + 1)));
            }

            getGameStateRunner().setState(new TransitionState(
                    getAssetManager().getAsset("win_battle", Movie.class),
                    nextState));
        } else if (playerData.getHealth() <= 0) {
            getGameStateRunner().setState(new TransitionState(
                    getAssetManager().getAsset("lose_battle", Movie.class),
                    new MainMenuState())); // lose and go to the main menu
        }
    }

    /**
     * Checks to see if the point in time is inside a region of time in the
     * "timing" array.
     * <p>
     * The "timing" array takes the form of { min, max, min, max } where each
     * min, max pair defines a region.
     *
     * @param time the point in time to test
     * @param timing the array that defines the regions of time that make this
     * check true.
     * @return Returns which region that the timing was inside of. If there was
     * not region then it returns -2
     */
    public int inTimingRegion(int time, int[] timing) {
        for (int i = 0; i < (int) (timing.length / 2); i++) {
            if (time > timing[i * 2] && time < timing[(i * 2) + 1]) {
                return i;
            }
        }
        return -2;
    }

    @Override
    public void render(Object o) {
        Graphics g = (Graphics) o;
        g.drawImage(backgroundImage, 0, 0, null);

        //Draw the player
        if (playerAnimation != null && playerAnimation.getCurrentImage() != null) {
            g.drawImage(playerAnimation.getCurrentImage(), 
                    (int)playerRenderPosition.getX() + 
                            (playerTurn ? moveToAttackProgress : 0), // render the player as closer if the player is attacking
                    (int)playerRenderPosition.getY(), null);
        }

        //Draw the enemy
        if (enemyAnimation != null && enemyAnimation.getCurrentImage() != null) {
            g.drawImage(enemyAnimation.getCurrentImage(), 
                    (int)enemyRenderPosition.getX() - 
                            (!playerTurn ? moveToAttackProgress : 0), // render the enemy as closer if the enemy is attacking
                    (int)enemyRenderPosition.getY(), null);
        }
        
        // draw the projectile
        if (projectileImage != null && playerAttackProgress >= projectileStartTime) {
            g.drawImage(projectileImage, 
                    (int)(playerTurn ? playerRenderPosition.getX() + projectileProgress :
                            enemyRenderPosition.getX() - projectileProgress),
                    (int)(playerTurn ? playerRenderPosition.getY() : 
                            enemyRenderPosition.getY()) - (projectileImage.getHeight()/2), null);
        }

        // debug drawing
        if (playerTurn) {
            if (playerAttackTiming != null) {
                g.setColor(Color.RED);
                if (inTimingRegion(playerAttackProgress, playerAttackTiming) > -1) {
                    g.setColor(Color.GREEN);
                }
                g.fillRect((int) playerRenderPosition.getX(),
                        (int) playerRenderPosition.getY(), 10, 10);
            }
        } else {
            if (enemyAttackTiming != null) {
                g.setColor(Color.BLUE);
                if (inTimingRegion(enemyAttackProgress, enemyAttackTiming) > -1) {
                    g.setColor(Color.CYAN);
                }
                g.fillRect((int) enemyRenderPosition.getX(),
                        (int) enemyRenderPosition.getY(), 10, 10);
            }
        }

        g.drawImage(lowerMenuBackground, 0, 350, null);

        gui.render(o);
        if (abilityButtonsGroup.isEnabled()) {
            for (GuiButton gb : abilityButtons) {
                gb.render(o);
            }
        }

        // draw the health bar for the player
        g.drawImage(getAssetManager().getImage("healthBar"),
                (int) playerRenderPosition.getX(),
                (int) playerRenderPosition.getY() - 50, null);
        g.setColor(Color.RED);
        // 144x24
        g.fillRect((int) playerRenderPosition.getX() + 3,
                (int) playerRenderPosition.getY() - 27,
                (int) (144 * ((double) playerData.getHealth() / playerMaxHealth)),
                24);

        // draw the health bar for the enemy
        g.drawImage(getAssetManager().getImage("healthBar"),
                (int) enemyRenderPosition.getX(),
                (int) enemyRenderPosition.getY() - 50, null);
        g.setColor(Color.RED);
        // 144x24
        g.fillRect((int) enemyRenderPosition.getX() + 3,
                (int) enemyRenderPosition.getY() - 27,
                (int) (144 * ((double) fighting.getHealth() / enemyMaxHealth)),
                24);

    }

    @Override
    public void onEnter() {
        lowerMenuBackground = getImage("lowerMenuBackground");
        selector = getImage("selector");
        projectileImage = null;
        particle = null;
        
        playerAnimation = getAssetManager().getAsset("player", Animation.class);
        playerAnimation.setTrack(0); // the idle animations
        playerAnimation.update(0); // to set the image
        playerRenderPosition = new Point(80, 100); // can change because you might be moving to hit the enemy
        
        enemyAnimation = fighting.getAnimation();
        enemyAnimation.setTrack(0);
        enemyAnimation.update(0);
        enemyRenderPosition = new Point(500, 100);
        
        // adjust the render location for the player and enemy according to the are you're inside
        if (areaInside.getName().equals("savanna")) {
            playerRenderPosition.setLocation(80, 190);
            enemyRenderPosition.setLocation(500, 180);
        } else if (areaInside.getName().equals("fire")) {
            playerRenderPosition.setLocation(80, 200);
        } else if (areaInside.getName().equals("ice")) {
            playerRenderPosition.setLocation(80, 165);
            enemyRenderPosition.setLocation(500, 205);
        } else if (areaInside.getName().equals("swamp")) {
            playerRenderPosition.setLocation(80, 180);
            enemyRenderPosition.setLocation(500, 115);
        }
        
        moveToAttackDistance = (int)(enemyRenderPosition.getX() - 
                playerRenderPosition.getX() - 
                playerAnimation.getCurrentImage().getWidth());
        moveToAttackDelta = moveToAttackDistance / 7; // 7 frames for moving
        GuiGroup main = new GuiGroup();
        //Make the health bars and other Gui elements

        // Gui elements for selecting weapons.
        abilityButtonsGroup = new GuiGroup();
        abilityButtons = new ArrayList<>();
        abilityActions = new ArrayList<>();
        for (int i = 0; i < playerData.getWeapons().size(); i++) {
            //System.out.println("Adding a weapon BUTTON");
            System.out.println("Hover image: " + playerData.getWeapons().get(i).getHoverImage());
            ChosePlayerAbilityAction action = new ChosePlayerAbilityAction(i);
            GuiButton newGuiButton = new GuiButton(250, 390 + (i * 40),
                    240, 30, playerData.getWeapons().get(i).getImage(),
                    playerData.getWeapons().get(i).getImage(),
                    playerData.getWeapons().get(i).getHoverImage(),
                    action); // make a button to chose the weapon
            abilityButtonsGroup.addElement(newGuiButton);
            abilityButtons.add(newGuiButton);
            abilityActions.add(action);
        }

        abilityButtonsGroup.setEnabled(false);
        playerTurn = true;
        // set the max healths
        playerMaxHealth = playerData.getMaxHealth();
        fighting.healCompletely();
        enemyMaxHealth = fighting.getHealth();

    }

    class ChosePlayerAbilityAction implements GuiButtonAction {

        private int indexNum;

        public ChosePlayerAbilityAction(int indexNum) {
            this.indexNum = indexNum;
        }

        @Override
        public void onButtonPress() {
//            System.out.println("THIS IS BEING PRESSED ITS SO COOL");
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
//                System.out.println("This totally happened");
//                System.out.println(x + ", " + y);
                for (GuiButton gb : abilityButtons) {
                    gb.mouseInput(x, y);
                }
            }
        }
    }

    @Override
    public void key(int keycode, boolean pressed) {
        if (keycode == KeyCode.KEY_SPACE && pressed) {
            if (pressedKeyInRegion != -2) { // if you did not try to press the key yet
                if (playerAttackTiming != null) { // if the player was attacking
                    pressedKeyInRegion = inTimingRegion(playerAttackProgress, playerAttackTiming);
                } else if (enemyAttackTiming != null) { // if the enemy was attacking
                    pressedKeyInRegion = inTimingRegion(enemyAttackProgress, enemyAttackTiming);
                }
            }

        }
    }
}
