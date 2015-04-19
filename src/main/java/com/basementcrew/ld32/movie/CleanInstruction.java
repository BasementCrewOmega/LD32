/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

/**
 * Deletes the whole scene
 * @author Jonathon
 */
public class CleanInstruction extends MovieInstruction {

    @Override
    public void startInstruction(Movie movie) {
        movie.clean();
    }

    @Override
    public void endInstruction(Movie movie) {
    }

    @Override
    public void updateInstruction(Movie movie, long dt) {
    }
    
}
