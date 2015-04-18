/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data;

import bropals.lib.simplegame.animation.Animation;

/**
 *
 * @author Jonathon
 */
public class Enemy {
    
    private Attack[] attacks;
    private int health;
    private Animation idleAnimation;
    private String name;
    
    public Enemy(String name, Attack[] attacks, int health, Animation idleAnimation) {
        this.name = name;
        this.attacks = attacks;
        this.health = health;
        this.idleAnimation = idleAnimation;
    }

    public int getHealth() {
        return health;
    }

    public Animation getIdleAnimation() {
        return idleAnimation;
    }
    
    public int getAttackCount() {
        return attacks.length;
    }
    
    public Attack getAttack(int count) {
        return attacks[count];
    }

    public String getName() {
        return name;
    }
    
    
}
