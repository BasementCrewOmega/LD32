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
public class RemoveInstruction extends MovieInstruction {

    private String removing;

    public RemoveInstruction(String removing) {
        this.removing = removing;
    }

    @Override
    public void startInstruction(Movie movie) {
        movie.removeFromScene(removing);
    }

    @Override
    public void endInstruction(Movie movie) {
    }

    @Override
    public void updateInstruction(Movie movie, long dt) {
    }
    
}
