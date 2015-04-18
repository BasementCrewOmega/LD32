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
    
    public static String toString(Consumable c) {
        switch(c) {
            case HEALTH_POTION:
                return "health_potion";
            case DODGE_POTION:
                return "dodge_potion";
            case ACCURACY_POTION:
                return "accuracy_potion";
        }
        return null;
    }
}
