/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.sound.SoundEffect;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class Attack {
    
    private int damage;
    private Animation animation;
    private int[] timings;
    private SoundEffect sound;
    private BufferedImage projectileImage;
    
    public Attack(int damage, Animation animation, int[] timings, SoundEffect sound) {
        this.damage = damage;
        this.animation = animation;
        this.timings = timings;
        this.sound = sound;
    }

    public boolean isMelee() {
        return projectileImage == null;
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
    
    public int getTimingEntireEnded() {
        return timings[timings.length - 1];
    }
    
    public int[] getTimings() {
        return timings;
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

    public SoundEffect getSound() {
        return sound;
    }

    public void setSound(SoundEffect sound) {
        this.sound = sound;
    }

    public void setProjectileImage(BufferedImage projectileImage) {
        this.projectileImage = projectileImage;
    }

    public BufferedImage getProjectileImage() {
        return projectileImage;
    }
}
