/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class Area {
    
    private Enemy[] possibleEnemies;
    private Enemy boss;
    private BufferedImage backgroundImage;
    private BufferedImage iconImage;
    private int iconX, iconY;
    private Weapon rewardWeapon;
    private String name;

    public Area(String name, Enemy[] possibleEnemies, Enemy boss, BufferedImage backgroundImage, BufferedImage iconImage, int iconX, int iconY, Weapon rewardWeapon) {
        this.name=name;
        this.possibleEnemies = possibleEnemies;
        this.boss = boss;
        this.backgroundImage = backgroundImage;
        this.iconImage = iconImage;
        this.iconX = iconX;
        this.iconY = iconY;
        this.rewardWeapon = rewardWeapon;
    }
    
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public Enemy getBoss() {
        return boss;
    }

    public Weapon getRewardWeapon() {
        return rewardWeapon;
    }
    
    public int getPossibleEnemyCount() {
        return possibleEnemies.length;
    }
    
    public Enemy getRandomEnemy() {
        Enemy enem = possibleEnemies[(int)(Math.random()*possibleEnemies.length)];
        enem.healCompletely(); // heal the new enemy
        return enem;
    }

    public int getIconX() {
        return iconX;
    }

    public int getIconY() {
        return iconY;
    }

    public BufferedImage getIconImage() {
        return iconImage;
    }
    
    
}
