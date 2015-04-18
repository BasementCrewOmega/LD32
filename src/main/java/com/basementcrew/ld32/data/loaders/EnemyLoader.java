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
 * Loads .enemy files.
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
public class EnemyLoader extends AssetLoader {

    @Override
    public void loadAsset(String key, InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        
            reader.close();
        } catch(IOException e) {
            ErrorLogger.println("Unable to load enemy file with key " + key +  ": " + e);
        }
    }
}
