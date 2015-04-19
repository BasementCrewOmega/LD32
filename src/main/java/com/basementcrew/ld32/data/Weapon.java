/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data;

import bropals.lib.simplegame.sound.SoundEffect;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class Weapon {
    
    private int attackDamage;
    private Effect effect;
    private int[] timings;
    private int cooldown;
    private BufferedImage image;
    private SoundEffect sound;
    private String name;
    
    public Weapon(String name, int attackDamage, Effect effect, int[] timings, int cooldown, BufferedImage image, SoundEffect sound) {
        this.attackDamage = attackDamage;
        this.effect = effect;
        this.timings = timings;
        this.cooldown = cooldown;
        this.sound = sound;
        this.image = image;
        this.name = name;
    }    
    
    public Effect getEffect() {
        return effect;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getName() {
        return name;
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
     * Get the time where the entire weapon's attack is officially ending.
     * @param timing The timing slot (whatever that is)
     * @return The time the entire attack ends in milliseconds
     */
    public int getTimingEntireEnded() {
        return timings[timings.length - 1]; // the last element of the array
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

    public int getCooldown() {
        return cooldown;
    }
    
}
