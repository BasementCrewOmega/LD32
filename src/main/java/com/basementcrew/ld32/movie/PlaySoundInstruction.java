/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

import bropals.lib.simplegame.sound.SoundEffect;

/**
 *
 * @author Jonathon
 */
public class PlaySoundInstruction extends MovieInstruction {

    private SoundEffect sound;

    public PlaySoundInstruction(SoundEffect sound) {
        this.sound = sound;
    }
        
    @Override
    public void startInstruction(Movie movie) {
        sound.play();
    }

    @Override
    public void endInstruction(Movie movie) {
    }

    @Override
    public void updateInstruction(Movie movie, long dt) {
    }
    
}
