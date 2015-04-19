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
public enum Consumable {
    HEALTH_POTION, 
    DODGE_POTION,
    ACCURACY_POTION;
    
    @Override
    public String toString() {
		return name().toLowerCase();
    }
}
