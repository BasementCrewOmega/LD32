/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data;

import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class PlayerData {
    
    private ArrayList<Weapon> weapons;
    private ArrayList<String> completedAreas;
    private ArrayList<Consumable> consumables;
    private String name;
    private int gold;

    public PlayerData(ArrayList<Weapon> weapons, ArrayList<String> completedAreas, ArrayList<Consumable> consumables, int gold, String name) {
        this.weapons = weapons;
        this.completedAreas = completedAreas;
        this.consumables = consumables;
        this.gold = gold;
        this.name = name;
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
    
}
