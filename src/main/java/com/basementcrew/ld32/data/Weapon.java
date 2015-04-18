/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data;

/**
 *
 * @author Jonathon
 */
public class Weapon {
    
    private int attackDamage;
    private Effect effect;
    private int[] timings;

    public Weapon(int attackDamage, Effect effect, int[] timings) {
        this.attackDamage = attackDamage;
        this.effect = effect;
        this.timings = timings;
    }    
    
    public Effect getEffect() {
        return effect;
    }

    public int getAttackDamage() {
        return attackDamage;
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
}
