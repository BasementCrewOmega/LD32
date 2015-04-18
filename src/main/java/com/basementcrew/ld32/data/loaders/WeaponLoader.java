/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data.loaders;

import bropals.lib.simplegame.io.AssetLoader;
import bropals.lib.simplegame.logger.ErrorLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Loads .weapon files.
 * <p>
 * 
 * <code>
 *  attack:&lt;attack_damage&gt;;<br />
 *  effect:&lt;effect&gt;;<br />
 *  timings:&lt;start_time&gt;&lt;end_time&gt;,...;<br />
 * </code>
 * @author Jonathon
 */
public class WeaponLoader extends AssetLoader {
    
    @Override
    public void loadAsset(String key, InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            reader.close();
        } catch (IOException e) {
            ErrorLogger.println("Unable to load weapon file with key " + key + ": " + e);
        }
    }
}
