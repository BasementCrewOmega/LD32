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
    private int attackTime;
    
    public Enemy(String name, Attack[] attacks, int health, Animation idleAnimation, int attackTime) {
        this.name = name;
        this.attacks = attacks;
        this.attackTime = attackTime;
        this.health = health;
        this.idleAnimation = idleAnimation;
    }

    public int getHealth() {
        return health;
    }

    public Animation getIdleAnimation() {
        return idleAnimation;
    }

    public int getAttackTime() {
        return attackTime;
    }    
    
    public int getAttackCount() {
        return attacks.length;
    }
    
    public Attack getRandomAttack() {
        return attacks[(int)(Math.random()*attacks.length)];
    }
    
    public Attack getAttack(int count) {
        return attacks[count];
    }

    public String getName() {
        return name;
    }
    
    
}
