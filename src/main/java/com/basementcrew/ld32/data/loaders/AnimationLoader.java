/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data.loaders;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.animation.Track;
import bropals.lib.simplegame.io.AssetLoader;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.ErrorLogger;
import bropals.lib.simplegame.logger.InfoLogger;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Loads animation files.
 * 
 * Format:
 * ``
 * ImageKey;widthOfEachTile;heighOfEachTile;millisecondsBetweenFrames;
 * ``
 * @author Jonathon
 */
public class AnimationLoader extends AssetLoader<Animation> {

    private AssetManager assetManager;
    
    public AnimationLoader(AssetManager assetManager) {
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
            
            ArrayList<Track> tracks = new ArrayList<>();
            
            String buffer = "";
            BufferedImage image = null;
            int width = -1;
            int height = -1;
            int millisBetween = -1;
            
            for (int i=0; i<src.length; i++) {
                if (src[i] == ';') {
                    if (image == null) {
                        image = assetManager.getImage(buffer);
                        if (image == null) {
                            ErrorLogger.println("No image with key " + buffer + " as requested by animation " + key);
                        } else {
                            InfoLogger.println("Setting image");
                        }
                    } else if (width == -1) {
                        width = Integer.parseInt(buffer);
                            InfoLogger.println("Setting width to " + width);
                    } else if (height == -1) {
                        height = Integer.parseInt(buffer);
                            InfoLogger.println("Setting height to " + height);
                    } else if (millisBetween == -1) {
                        millisBetween = Integer.parseInt(buffer);
                        InfoLogger.println("Setting millisBetween to " + millisBetween);
                    } else {
                        InfoLogger.println("Making track");
                        tracks.add(new Track(image, width, height, millisBetween));
                        
                        image = null;
                        width = -1;
                        height = -1;
                        millisBetween = -1;
                    }
                    buffer = "";
                } else {
                    buffer += src[i];
                }
            }
            InfoLogger.println("Loaded animation " + key + " with " + tracks.size() + " tracks");
            add(key, new Animation(tracks));
        } catch (IOException e) {
            ErrorLogger.println("Unable to load area file with key " + key + ": " + e);
        }
    }

    @Override
    public Animation getAsset(String key) {
        return (Animation)super.getAsset(key).clone();
    }    
}
