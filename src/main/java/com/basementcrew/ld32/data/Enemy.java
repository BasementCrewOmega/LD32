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
    private int health, maxHealth;
    private Animation animation;
    private String name;
    private int attackTime;
    
    public Enemy(String name, Attack[] attacks, int health, Animation animation, int attackTime) {
        this.name = name;
        this.attacks = attacks;
        this.attackTime = attackTime;
        this.health = health;
        this.maxHealth = health;
        this.animation = animation;
    }

    public void healCompletely() {
        this.health = this.maxHealth;
    }
    
    public void damage(int hp) {
        health -= hp;
    }
    
    public int getHealth() {
        return health;
    }

    public Animation getAnimation() {
        return animation;
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
