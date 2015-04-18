/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.io.AssetManager;

/**
 *
 * @author Jonathon
 */
public class DefineInstruction extends MovieInstruction {

    private String name;
    private String type;
    private String param;
    private AssetManager assetManager;

    public DefineInstruction(String name, String type, String param, AssetManager assetManager) {
        this.name = name;
        this.type = type;
        this.param = param;
        this.assetManager = assetManager;
    }    
    
    @Override
    public void startInstruction(Movie movie) {
        if (type.equals("image")) {
            movie.defineImageObject(name, param);
        } else if (type.equals("animation")) {
            movie.defineAnimationObject(name, param);
        }
    }

    @Override
    public void endInstruction(Movie movie) {
    }

    @Override
    public void updateInstruction(Movie movie, long dt) {
    }
    
}
