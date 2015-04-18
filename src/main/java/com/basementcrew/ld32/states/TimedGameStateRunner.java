/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.state.GameState;

/**
 *
 * @author Kevin
 */
public class TimedGameStateRunner extends GameStateRunner {

    public TimedGameStateRunner(GameWindow window, AssetManager assetManager) {
        super(window, assetManager);
    }

    @Override
    public void loop() {
        boolean running = true;
        while(running) {
            if (getCurrentState() == null || getCurrentWindow() == null || 
                    getCurrentWindow().isRequestingToClose()) {
                running = false;
                continue;
            }
            
            setStartTime(System.currentTimeMillis());
            GameState runState = getCurrentState(); // in case the state is changed
                                              // in the middle of the loop
            getCurrentWindow().flushInput();
            runState.update();
            renderState(runState);
//            diff = System.currentTimeMillis() - startTime;
//            if (diff < millisBetweenFrames) {
//                try {
//                    Thread.sleep(millisBetweenFrames - diff);
//                } catch(Exception e) {}
//            }
        }
//        currentWindow.destroy();
    }
    
    
    
}
