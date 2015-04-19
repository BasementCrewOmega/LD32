/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.data.loaders;

import bropals.lib.simplegame.io.AssetLoader;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.ErrorLogger;
import com.basementcrew.ld32.movie.AddInstruction;
import com.basementcrew.ld32.movie.CleanInstruction;
import com.basementcrew.ld32.movie.DefineInstruction;
import com.basementcrew.ld32.movie.DelayInstruction;
import com.basementcrew.ld32.movie.MoveInstruction;
import com.basementcrew.ld32.movie.Movie;
import com.basementcrew.ld32.movie.MovieInstruction;
import com.basementcrew.ld32.movie.PlaySoundInstruction;
import com.basementcrew.ld32.movie.RemoveInstruction;
import com.basementcrew.ld32.movie.SetBackgroundInstruction;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Loads movie files.
 * 
 * <p>
 * 
 * Movie files are simply a list of instructions, with the following codes/
 * <ul>
 *  <li>define &lt;name&gt; &lt;type&gt; &lt;parameter&gt;</li>
 *  <li>add &lt;name&gt; &lt;x&gt; &lt;y&gt;</li>
 *  <li>remove &lt;name&gt;</li>
 *  <li>delay &lt;milliseconds&gt;</li>
 *  <li>set_bg &lt;background_image&gt;</li>
 *  <li>move &lt;name&gt; &lt;goalX&gt; &lt;goalY&gt; &lt;milliseconds&gt;</li>
 *  <li>play_sound &lt;sound&gt;</li>
 *  <li>clean</li>
 * </ul>
 * @author Jonathon
 */
public class MovieLoader extends AssetLoader {

    private AssetManager assetManager;

    public MovieLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }    
    
    @Override
    public void loadAsset(String key, InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            String[] split = null;
            
            ArrayList<MovieInstruction> instructions = new ArrayList<>();
            
            while ( (line = reader.readLine()) != null) {
                split = line.split(Pattern.quote(" "));
                switch(split[0]) {
                    case "add":
                        instructions.add(new AddInstruction(
                                split[1],
                                Integer.parseInt(split[2]),
                                Integer.parseInt(split[3])
                        ));
                        break;
                    case "remove":
                        instructions.add(new RemoveInstruction(split[1]));
                        break;
                    case "define":
                        instructions.add(new DefineInstruction(
                                split[1], 
                                split[2], 
                                split[3], 
                                assetManager));
                        break;
                    case "delay":
                        instructions.add(new DelayInstruction(Integer.parseInt(split[1])));
                        break;
                    case "set_bg":
                        instructions.add(new SetBackgroundInstruction(assetManager.getImage(split[1])));
                        break;
                    case "move":
                        instructions.add(new MoveInstruction(
                                split[1],
                                Integer.parseInt(split[2]),
                                Integer.parseInt(split[3]),
                                Integer.parseInt(split[4])
                        ));
                        break;
                    case "play_sound":
                        instructions.add(new PlaySoundInstruction(assetManager.getSoundEffect(split[1])));
                        break;
                    case "clean":
                        instructions.add(new CleanInstruction());
                        break;
                }
            }
            reader.close();
            MovieInstruction[] instructionArray = new MovieInstruction[instructions.size()];
            for (int i=0; i<instructionArray.length; i++) {
                instructionArray[i] = instructions.get(i);
            }
            add(key, new Movie(instructionArray, assetManager));
        } catch(IOException ioe) {
            ErrorLogger.println("Could not load movie " + key +  ": " + ioe);
        }
    }
}
