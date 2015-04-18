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
public class Attack {
    
    private int damage;
    private Animation animation;
    private int[] timings;
    
    public Attack(int damage, Animation animation, int[] timings) {
        this.damage = damage;
        this.animation = animation;
        this.timings = timings;
    }

    public int getDamage() {
        return damage;
    }
    
    /**
     * Gets the start time at the specified time slot.
     * @param timing the timing slot to get
     * @return the start time of the specified time slot, in milliseconds.
     */
    public int getTimingStart(int timing) {
        return timings[timing*2];
    }
    
    /**
     * Gets the end time at the specified time slot.
     * @param timing the timing slot to get
     * @return the end time of the specified time slot, in milliseconds.
     */
    public int getTimingEnd(int timing) {
        return timings[(timing*2) + 1];
    }
    
    /**
     * The amount of timing slots.
     * @return the timing slot amount for this attack.
     */
    public int getTimingSlotCount() {
        return timings.length/2;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void setTimings(int[] timings) {
        this.timings = timings;
    }
    
    
}
