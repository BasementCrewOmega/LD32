/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data.loaders;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.io.AssetLoader;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.ErrorLogger;
import com.basementcrew.ld32.data.Attack;
import com.basementcrew.ld32.data.Enemy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Loads enemy files.
 * 
 * The format of enemy files:
 * <p>
 * <code>
 *  attack {<br />
 *  &nbsp;&nbsp;&nbsp;&nbsp;damage:&lt;damage&gt;;<br />
 *  &nbsp;&nbsp;&nbsp;&nbsp;animation:&lt;animation&gt;;<br />
 *  &nbsp;&nbsp;&nbsp;&nbsp;timings:&lt;start_time&gt;&lt;end_time&gt;,...;<br />
 *  }<br />
 *  attack {<br />
 *  &nbsp;&nbsp;&nbsp;&nbsp;...<br />
 * }<br />
 * data {<br />
 *  &nbsp;&nbsp;&nbsp;&nbsp;health:&lt;health&gt;;<br />
 *  &nbsp;&nbsp;&nbsp;&nbsp;idle:&lt;idle_animation&gt;;<br />
 *  }<br />
 * </code>
 * @author Jonathon
 */
public class EnemyLoader extends AssetLoader<Enemy> {

    private AssetManager assetManager;
    
    public EnemyLoader(AssetManager assetManager) {
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
            char current;
            
            ArrayList<Attack> attacks = new ArrayList<>();
            Attack currentAttack = null;
            int health = 0;
            Animation animation = null;
            
            String currentBlock = null;
            String currentProperty = null;
            
            for (int i=0; i<src.length; i++) {
                current = src[i];
                if (current == '{') {
                    //Starting a new block
                    currentBlock = buffer;
                    if (currentBlock.equals("attack")) {
                        currentAttack = new Attack(0, null, null);
                    }
                    buffer = "";
                } else if (current == '}') {
                    currentBlock = null;
                    if (currentBlock.equals("attack")) {
                        attacks.add(currentAttack);
                    }
                    buffer = "";
                } else if (current == ':') {
                    currentProperty = buffer;
                    buffer = "";
                } else if (current == ';') {
                    if (currentBlock.equals("attack")) {
                        if (currentProperty.equals("damage")) {
                            currentAttack.setDamage(Integer.parseInt(buffer));
                        } else if (currentProperty.equals("animation")) {
                            currentAttack.setAnimation(assetManager.getAsset(buffer, Animation.class));
                        } else if (currentProperty.equals("timings")) {
                            String[] timingsSplit = buffer.split(Pattern.quote(","));
                            int[] timings = new int[timingsSplit.length];
                            for (int j=0; j<timings.length; j++) {
                                timings[j] = Integer.parseInt(timingsSplit[j]);
                            }
                            currentAttack.setTimings(timings);
                        }
                    } else if (currentBlock.equals("data")) {
                        if (currentProperty.equals("health")) {
                            health = Integer.parseInt(buffer);
                        } else if (currentProperty.equals("idle")) {
                            animation = assetManager.getAsset(buffer, Animation.class);
                        }
                    }
                    buffer = "";
                } else {
                    buffer += current;
                }
            }
            Enemy enemy = new Enemy((Attack[])attacks.toArray(new Attack[0]), health, animation);
            add(key, enemy);
        } catch(IOException e) {
            ErrorLogger.println("Unable to load enemy file with key " + key +  ": " + e);
        }
    }
}
