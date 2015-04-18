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
            
            
        } catch (IOException e) {
            ErrorLogger.println("Unable to load player data file with key " + key + ": " + e);
        }
    }

}
