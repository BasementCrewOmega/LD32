/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

import bropals.lib.simplegame.logger.ErrorLogger;
import bropals.lib.simplegame.logger.InfoLogger;

/**
 *
 * @author Jonathon
 */
public class MoveInstruction extends MovieInstruction {

    private String movingName;
    private MovieObject moving;
    private float deltaX;
    private float deltaY;
    private int goalX;
    private int goalY;
    private int milliseconds;
    private int millisecondProgress;

    public MoveInstruction(String movingName, int goalX, int goalY, int milliseconds) {
        this.movingName = movingName;
        this.moving = null;
        this.deltaX = 0;
        this.deltaY = 0;
        this.goalX = goalX;
        this.goalY = goalY;
        this.millisecondProgress = 0;
        this.milliseconds = milliseconds;
    }    
    
    @Override
    public void startInstruction(Movie movie) {
        this.millisecondProgress = 0;
        moving = movie.getObjectInScene(movingName);
        if (moving == null) {
            ErrorLogger.println("No object in scene with the name " + movingName + 
                    " for moving instruction");
        }
        deltaX = (float)( ((float)goalX - (float)moving.getX())/(float)milliseconds );
        deltaY = (float)( ((float)goalY - (float)moving.getY())/(float)milliseconds );
        movie.addMoveInstruction(this);
    }

    @Override
    public void endInstruction(Movie movie) {
    }

    @Override
    public void updateInstruction(Movie movie, long dt) {
        if (moving != null) {
            millisecondProgress += dt;
            moving.translate(deltaX*dt, deltaY*dt);
            if (millisecondProgress >= milliseconds) {
                moving = null;
                movie.removeMoveInstruction(this);
            }
        }
    }

    public MovieObject getMoving() {
        return moving;
    }

    public void stop() {
        moving = null;
    }
    
}
