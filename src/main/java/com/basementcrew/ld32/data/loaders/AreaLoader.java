/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data.loaders;

import bropals.lib.simplegame.io.AssetLoader;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.ErrorLogger;
import com.basementcrew.ld32.data.Area;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.Weapon;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Loads area files.
 * <p>
 * <code>
 *  enemies {<br />
 *  &lt;weapon&gt;;<br />
 *  &lt;weapon&gt;;<br />
 *  }<br />
 *  boss:&lt;boss&gt;;<br />
 *  background_image:&lt;image&gt;;<br />
 *  weapon:&lt;weapon&gt;;<br />
 * </code>
 * @author Jonathon
 */
public class AreaLoader extends AssetLoader<Area> {
    
    private AssetManager assetManager;
    
    public AreaLoader(AssetManager assetManager) {
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
            
            String currentProperty = null;
            String buffer = "";
            
            ArrayList<Enemy> enemies = new ArrayList<Enemy>();
            Enemy boss = null;
            Weapon weapon = null;
            BufferedImage backgroundImage = null;
            
            for (int i=0; i<src.length; i++) {
                if (src[i] == '{' || src[i] == ':') {
                    currentProperty = buffer;
                    buffer = "";
                } else if (src[i] == ';') {
                    if (currentProperty.equals("enemies")) {
                        enemies.add(assetManager.getAsset(buffer, Enemy.class));
                    } else if (currentProperty.equals("boss")) {
                        boss = assetManager.getAsset(buffer, Enemy.class);
                    } else if (currentProperty.equals("background_image")) {
                        backgroundImage = assetManager.getImage(buffer);
                    } else if (currentProperty.equals("weapon")) {
                        weapon = assetManager.getAsset(buffer, Weapon.class);
                    } 
                    buffer = "";
                } else {
                    buffer += src[i];
                }
            }
            
            add(key, new Area((Enemy[])enemies.toArray(new Enemy[0]), boss, backgroundImage, weapon));
        } catch (IOException e) {
            ErrorLogger.println("Unable to load area file with key " + key + ": " + e);
        }
    }
}
