/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data.loaders;

import bropals.lib.simplegame.io.AssetLoader;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.ErrorLogger;
import bropals.lib.simplegame.sound.SoundEffect;
import com.basementcrew.ld32.data.Effect;
import com.basementcrew.ld32.data.Weapon;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Loads weapon files.
 * <p>
 * 
 * <code>
 *  attack:&lt;attack_damage&gt;;<br />
 *  effect:&lt;effect&gt;;<br />
 *  cooldown:&lt;cooldown_turns&gt;;<br />
 *  timings:&lt;start_time&gt;&lt;end_time&gt;,...;<br />
 *  image:&lt;image&gt;;
 *  sound:&lt;sound&gt;;
 * </code>
 * @author Jonathon
 */
public class WeaponLoader extends AssetLoader<Weapon> {
    
    private AssetManager assetManager;

    public WeaponLoader(AssetManager assetManager) {
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
            
            String buffer = "";
            String property = null;
            
            int cooldown = 0;
            int attack = 0;
            Effect effect = null;
            int[] timings = null;
            BufferedImage image = null;
            SoundEffect sound = null;
            
            for (int i=0; i<src.length; i++) {
                if (src[i] == ';') {
                    if (property.equals("cooldown")) {
                        cooldown = Integer.parseInt(buffer);
                        buffer = "";
                    } else if (property.equals("attack")) {
                        attack = Integer.parseInt(buffer);
                        buffer = "";
                    } else if (property.equals("effect")) {
                        //Parse the effect
                        
                        buffer = "";
                    } else if (property.equals("timings")) {
                        String[] timingsSplit = buffer.split(Pattern.quote(","));
                        timings = new int[timingsSplit.length];
                        for (int j=0; j<timings.length; j++) {
                            timings[j] = Integer.parseInt(timingsSplit[j]);
                        }
                        buffer = "";
                    } else if (property.equals("image")) {
                        image = assetManager.getImage(buffer);
                    } else if (property.equals("sound")) {
                        sound = assetManager.getSoundEffect(buffer);
                    }
                } else if (src[i] == ':') {
                    property = buffer;
                    buffer  = "";
                } else {
                    buffer += src[i];
                }
            }
            add(key, new Weapon(key, attack, effect, timings, cooldown, image, sound));
        } catch (IOException e) {
            ErrorLogger.println("Unable to load weapon file with key " + key + ": " + e);
        }
    }
}
