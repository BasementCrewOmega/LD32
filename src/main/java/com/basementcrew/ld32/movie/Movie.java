/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.logger.ErrorLogger;
import bropals.lib.simplegame.logger.InfoLogger;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 *
 * @author Jonathon
 */
public class Movie {

    /**
     * How much time, in milliseconds, the movie has to wait until it can do the
     * next instruction.
     */
    private int delayLeft = 0;
    private ArrayList<MovieObject> definedObjects = new ArrayList<>();
    private ArrayList<MovieObject> scene = new ArrayList<>();
    private ArrayList<MoveInstruction> moveInstructions = new ArrayList<>();
    private MovieInstruction[] instructions;
    private int current = -1;
    private BufferedImage background;
    private AssetManager assetManager;

    public Movie(MovieInstruction[] movieInstructions, AssetManager assetManager) {
        this.instructions = movieInstructions;
        this.assetManager = assetManager;
    }

    public void updateMovie(long dt) {
        delayLeft -= dt;
        if (delayLeft <= 0) {
            if (current >= 0 && current < instructions.length) {
                instructions[current].endInstruction(this);
            }
            if (current < instructions.length) {
                current++;
            }
            if (!isOver()) {
                if (current < instructions.length) {
                    instructions[current].startInstruction(this);
                }
            }
        } else {
            if (current < instructions.length) {
                instructions[current].updateInstruction(this, dt);
            }
        }
        try {
            for (MovieObject obj : scene) {
                obj.update(dt);
            }
            for (MoveInstruction mi : moveInstructions) {
                mi.updateInstruction(this, dt);
            }
        } catch (ConcurrentModificationException cme) {
            
        }
        InfoLogger.println(delayLeft);
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void reset() {
        definedObjects.clear();
        scene.clear();
        moveInstructions.clear();
        current = -1;
        delayLeft = 0;
        background = null;
    }

    public void renderMovie(Graphics g) {
        g.drawImage(background, 0, 0, null);
        for (MovieObject obj : scene) {
            obj.render(g);
        }
    }

    public void addMoveInstruction(MoveInstruction mi) {
        moveInstructions.add(mi);
    }

    public void removeMoveInstruction(MoveInstruction mi) {
        moveInstructions.remove(mi);
    }

    public boolean isOver() {
        return current >= instructions.length && moveInstructions.isEmpty();
    }

    public void defineImageObject(String name, String image) {
        ImageMovieObject imo = new ImageMovieObject(assetManager.getImage(image));
        imo.setName(name);
        definedObjects.add(imo);
    }

    public void defineAnimationObject(String name, String animation) {
        AnimationMovieObject amo = new AnimationMovieObject(assetManager.getAsset(animation, Animation.class));
        amo.setName(name);
        definedObjects.add(amo);
        // InfoLogger.println("Defined objects: " + definedObjects.size());
    }

    public void addToScene(String name, int x, int y) {
        MovieObject obj = getDefinedObject(name);
        if (obj != null) {
            obj.setX(x);
            obj.setY(y);
            scene.add(obj);
        } else {
            ErrorLogger.println("No object of the name " + name + " defined");
        }
    }

    public void removeFromScene(String name) {
        MovieObject obj = getObjectInScene(name);
        if (obj != null) {
            scene.remove(obj);
        } else {
            ErrorLogger.println("No object of the name " + name + " in the scene");
        }
    }

    public void delay(int milliseconds) {
        delayLeft = milliseconds;
    }

    public MovieObject getObjectInScene(String name) {
        for (MovieObject object : scene) {
            if (object.getName().equals(name)) {
                return object;
            }
        }
        return null;
    }

    public MovieObject getDefinedObject(String name) {
        for (MovieObject object : definedObjects) {
            if (object.getName().equals(name)) {
                return object;

            }
        }
        return null;
    }

    public void forceNext() {
        current++;
    }
}
