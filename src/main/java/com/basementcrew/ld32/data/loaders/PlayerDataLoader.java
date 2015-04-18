/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data.loaders;

import bropals.lib.simplegame.io.AssetLoader;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.ErrorLogger;
import com.basementcrew.ld32.data.Consumable;
import com.basementcrew.ld32.data.PlayerData;
import com.basementcrew.ld32.data.Weapon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Loads playerdata files, which are in the format of.
 * <p>
 *
 * <code>
 *  weapons {<br />
 *  &lt;weapon&gt;;<br />
 *  &lt;weapon&gt;;<br />
 *  }<br />
 *  completed_areas {<br />
 *  &lt;area_name&gt;;<br />
 *  &lt;area_name&gt;;<br />
 * }<br />
 * consumables {<br />
 *  &lt;item&gt;;<br />
 *  &lt;item&gt;;<br />
 *  }<br />
 *  gold:&lt;gold&gt;;
 * </code>
 *
 * @author Jonathon
 */
public class PlayerDataLoader extends AssetLoader {

    private AssetManager assetManager;
    
    public PlayerDataLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    @Override
    public void loadAsset(String key, InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder srcBuilder = new  StringBuilder(100); //Source without whitespace
            int num;
            while ((num = reader.read()) != -1) {
                if (!Character.isWhitespace((char)num)) {
                    srcBuilder.append((char)num);
                }
            }
            reader.close();
            char[] src = srcBuilder.toString().toCharArray();
            
            ArrayList<Weapon> weapons = new ArrayList<>();
            ArrayList<String> areas = new ArrayList<>();
            ArrayList<Consumable> consumables = new ArrayList<>();
            
            int gold = 0;
            
            String block = null;
            String property = null;
            String buffer = "";
            
            for (int i=0; i<src.length; i++) {
                if (src[i] == '{') {
                    block = buffer;
                    buffer = "";
                } else if (src[i] == '}') {
                    block = null;
                    buffer = "";
                } else if (src[i] == ';') {
                    if (block!=null) {
                        if (block.equals("weapons")) {
                            weapons.add(assetManager.getAsset(buffer, Weapon.class));
                        } else if (block.equals("completed_areas")) {
                            areas.add(buffer);
                        } else if (block.equals("consumables")) {
                            //Consumable parsing
                            
                        }
                    } else {
                        if (property.equals("gold")) {
                            gold = Integer.parseInt(buffer);
                        }
                    }
                    buffer = "";
                } else {
                    buffer += src[i];
                }
            }
            add(key, new PlayerData(weapons, areas, consumables, gold));
        } catch (IOException e) {
            ErrorLogger.println("Unable to load player data file with key " + key + ": " + e);
        }
    }

}
