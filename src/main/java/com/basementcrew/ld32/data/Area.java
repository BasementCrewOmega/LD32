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
    private Weapon rewardWeapon;

    public Area(Enemy[] possibleEnemies, Enemy boss, BufferedImage backgroundImage, Weapon rewardWeapon) {
        this.possibleEnemies = possibleEnemies;
        this.boss = boss;
        this.backgroundImage = backgroundImage;
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
        return possibleEnemies[(int)(Math.random()*possibleEnemies.length)];
    }
}
