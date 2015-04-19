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
public class AddInstruction extends MovieInstruction {

    private String adding;
    private int x;
    private int y;

    public AddInstruction(String adding, int x, int y) {
        this.adding = adding;
        this.x = x;
        this.y = y;
    }
    
    
    
    @Override
    public void startInstruction(Movie movie) {
        movie.addToScene(adding, x, y);
    }

    @Override
    public void endInstruction(Movie movie) {
        
    }

    @Override
    public void updateInstruction(Movie movie, long dt) {
        
    }
    
}
