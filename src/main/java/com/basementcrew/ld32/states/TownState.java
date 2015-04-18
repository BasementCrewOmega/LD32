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
import bropals.lib.simplegame.gui.GuiImage;
import bropals.lib.simplegame.state.GameState;
import com.basementcrew.ld32.data.Area;
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
        //Add areas to the list of areas.
        //areas.add(assetManager.getAsset("swamp", Area.class));
        //areas.add(assetManager.getAsset("savanna", Area.class));
        //areas.add(assetManager.getAsset("fire", Area.class));
        //areas.add(assetManager.getAsset("ice", Area.class));
        
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
        main.addElement(new GuiImage(350, 250, 100, 100,
                getImage("townIcon")));
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
            getGameStateRunner().setState(new TransitionState(
                    getAssetManager().getAsset("enter_battle", Movie.class),
                    new BattleSequenceState(area.getRandomEnemy(), playerData, area.getBackgroundImage())
            ));
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
