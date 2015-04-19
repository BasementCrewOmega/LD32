/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

/**
 *
 * @author Jonathon
 */
public class DelayInstruction extends MovieInstruction {

    private int milliseconds;

    public DelayInstruction(int milliseconds) {
        this.milliseconds = milliseconds;
    }
        
    @Override
    public void startInstruction(Movie movie) {
        movie.delay(milliseconds);
    }

    @Override
    public void endInstruction(Movie movie) {
    }

    @Override
    public void updateInstruction(Movie movie, long dt) {
    }
    
}
