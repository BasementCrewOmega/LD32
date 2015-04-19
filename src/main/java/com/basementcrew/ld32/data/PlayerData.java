/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data;

import bropals.lib.simplegame.logger.ErrorLogger;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class PlayerData {
    
    private ArrayList<Weapon> weapons;
    private ArrayList<String> completedAreas;
    private ArrayList<Consumable> consumables;
    private int health, maxHealth;
    private String name;
    private int gold;

    public PlayerData(ArrayList<Weapon> weapons, ArrayList<String> completedAreas, 
            ArrayList<Consumable> consumables, int gold, String name, int health) {
        System.out.println("This player data class has " + weapons.size() + " weapon(s)");
        this.weapons = weapons;
        this.completedAreas = completedAreas;
        this.consumables = consumables;
        this.gold = gold;
        this.name = name;
        this.health = health;
        this.maxHealth = health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getGold() {
        return gold;
    }
    
    public void addGold(int gold) {
        this.gold += gold;
    }
    
    public void completeArea(String area) {
        completedAreas.add(area);
    }

    public ArrayList<String> getCompletedAreas() {
        return completedAreas;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Consumable> getConsumables() {
        return consumables;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    
    /**
     * Saves the player data so it can be read by the player data loader again.
     * @param output 
     */
    public void writeTo(OutputStream output) {
        try {
            PrintWriter writer = new PrintWriter(output);
            
            writer.println("weapons {");
            for (Weapon w : weapons) {
                writer.println(w.getName() + ";");
            }
            writer.println("}");
            
            writer.println("completed_areas {");
            for (String areaName : completedAreas) {
                writer.println(areaName + ";");
            }
            writer.println("}");
            
            writer.println("consumables {");
            for (Consumable c : consumables) {
                writer.println(c + ";");
            }
            writer.println("}");
            
            writer.println("gold:" + gold + ";");
            writer.println("name:" + name + ";");
            
            writer.flush();
            writer.close();
        } catch(Exception e) {
            ErrorLogger.println("Unable to save player data: " + e);
        }
    }
}
