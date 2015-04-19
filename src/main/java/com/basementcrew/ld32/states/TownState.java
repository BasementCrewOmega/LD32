/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import bropals.lib.simplegame.MouseButton;
import bropals.lib.simplegame.gui.Gui;
import bropals.lib.simplegame.gui.GuiButton;
import bropals.lib.simplegame.gui.GuiButtonAction;
import bropals.lib.simplegame.gui.GuiGroup;
import bropals.lib.simplegame.logger.ErrorLogger;
import bropals.lib.simplegame.state.GameState;
import com.basementcrew.ld32.data.Area;
import com.basementcrew.ld32.data.Enemy;
import com.basementcrew.ld32.data.PlayerData;
import com.basementcrew.ld32.movie.Movie;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The menu where you chose what area you go to
 *
 * @author Kevin
 */
public class TownState extends GameState {

    private ArrayList<Area> areas = new ArrayList<>();
    private Gui gui = new Gui();
    private BufferedImage background;
    private PlayerData playerData;

    public TownState(PlayerData data) {
        playerData = data;
    }
    
    @Override
    public void update() {
        Point p = getWindow().getMousePosition();
        if (p != null) {
            gui.update(p.x, p.y);
        }
    }

    @Override
    public void render(Object o) {
        Graphics g = (Graphics) o;
        g.drawImage(background, 0, 0, null);
        gui.render(o);
    }
    
    @Override
    public void onEnter() {
        if (playerData == null) {
            playerData = getAssetManager().getAsset("default_playerdata", PlayerData.class);
        }
        
        //Add areas to the list of areas.
        areas.add(getAssetManager().getAsset("swamp", Area.class));
        areas.add(getAssetManager().getAsset("savanna", Area.class));
        areas.add(getAssetManager().getAsset("fire", Area.class));
        areas.add(getAssetManager().getAsset("ice", Area.class));
        
        //Main town area GUI
        GuiGroup main = new GuiGroup();
        for (int i = 0; i < areas.size(); i++) {
            main.addElement(new GuiButton(
                    areas.get(i).getIconX(),
                    areas.get(i).getIconY(),
                    100, 100,
                    areas.get(i).getIconImage(),
                    areas.get(i).getIconImage(),
                    areas.get(i).getIconImage(),
                    new GoToAreaButton(areas.get(i))
            ));
        }
        main.addElement(new GuiButton(
                25, 540, 100, 40,
                getImage("shopDown"),
                getImage("shopUp"),
                getImage("shopHover"),
                new GoToShopButton()
        ));

        gui.addGroup("main", main);
        gui.enable("main");

        //Shop menu GUI
        GuiGroup town = new GuiGroup();
        town.addElement(new GuiButton(
            25, 25,
            100, 40,
            getImage("toTownDown"),
            getImage("toTownUp"),
            getImage("toTownHover"),
            new GoToTownButton()
        ));
        
        //What other elements are in the shop gui? (its behavior on item buying?)
        
        gui.addGroup("town", town);
        gui.disable("town");

        background = getImage("townBackground");
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        if (mousebutton == MouseButton.BUTTON_LEFT && pressed) {
            gui.mouseInput(x, y);
        }
    }

    class GoToAreaButton implements GuiButtonAction {

        private Area area;

        public GoToAreaButton(Area area) {
            this.area = area;
        }

        @Override
        public void onButtonPress() {
                Enemy enemy = area.getRandomEnemy();
                Movie movie = null;
                switch(enemy.getName()) {
                    case "goblin":
                        movie = getAssetManager().getAsset("enter_battle_goblin", Movie.class);
                        break;
                    case "yeti":
                        movie = getAssetManager().getAsset("enter_battle_yeti", Movie.class);
                        break;
                    case "warthog":
                        movie = getAssetManager().getAsset("enter_battle_warthog", Movie.class);
                        break;
                    case "imp":
                        movie = getAssetManager().getAsset("enter_battle_imp", Movie.class);
                        break;
                    default:
                        ErrorLogger.println("No enter battle movie for " + enemy.getName());
                }
                getGameStateRunner().setState(new TransitionState(
                    movie,
                    new BattleSequenceState(enemy, playerData, 
                            area.getBackgroundImage(), area, 1)));
        }

    }

    class GoToShopButton implements GuiButtonAction {

        @Override
        public void onButtonPress() {
            gui.disable("main");
            gui.enable("town");
        }
    }

    class GoToTownButton implements GuiButtonAction {

        @Override
        public void onButtonPress() {
            gui.disable("town");
            gui.enable("main");
        }
    }
}
