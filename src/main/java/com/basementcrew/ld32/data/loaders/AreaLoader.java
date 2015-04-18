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
 * Loads .area files.
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
public class AreaLoader extends AssetLoader {
    
    @Override
    public void loadAsset(String key, InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            reader.close();
        } catch (IOException e) {
            ErrorLogger.println("Unable to load area file with key " + key + ": " + e);
        }
    }
}
