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
public abstract class MovieInstruction {
    
    private int ranAtMillisecond = -1;
    
    public abstract void startInstruction(Movie movie);
    public abstract void endInstruction(Movie movie);
    public abstract void updateInstruction(Movie movie, long dt);
        
    public int getRanAtMillisecond() {
        return ranAtMillisecond;
    }

    public void setRanAtMillisecond(int ranAtMillisecond) {
        this.ranAtMillisecond = ranAtMillisecond;
    }
}
