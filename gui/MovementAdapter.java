package openworld.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import openworld.Coordinates;
import openworld.characters.Healer;
import openworld.characters.Wizard;
import openworld.entityTypes.WorldEntity;
import openworld.terrain.Mountain;
import openworld.terrain.Terrain;

public class MovementAdapter extends KeyAdapter {

    private GameWorld gameWorld;
    private GameWindow gameWindow;

    public MovementAdapter(GameWorld gameWorld, GameWindow gameWindow) {
        this.gameWorld = gameWorld;
        this.gameWindow = gameWindow;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                moveAdventurer(Coordinates.NORTH_VECTOR);
                break;

            case KeyEvent.VK_RIGHT:
                moveAdventurer(Coordinates.EAST_VECTOR);
                break;

            case KeyEvent.VK_DOWN:
                moveAdventurer(Coordinates.SOUTH_VECTOR);
                break;

            case KeyEvent.VK_LEFT:
                moveAdventurer(Coordinates.WEST_VECTOR);
                break;
        }

    }

    public void moveAdventurer(Coordinates vector) {
        if (!gameWorld.getAdventurer().isConscious()) {
            return;
        }

        ArrayList<Coordinates> safeMoves = gameWorld.getAdventurer().getLocation().findAllSafeMoves(gameWorld.getWorld());

        if (safeMoves.contains(vector)) {
            gameWorld.getAdventurer().move(vector);
        }
        else{
            return;
        }
        if (!gameWorld.getAdventurer().isConscious()) {
            return;
        }
        
        List<Terrain> terrain = gameWorld.getWorld().getTerrainHere(gameWorld.getAdventurer().getLocation());
        if (terrain.get(0) instanceof Mountain) {
            Mountain mountain = (Mountain) terrain.get(0);

            MountainDialog dlg = new MountainDialog(gameWindow, mountain);
            dlg.setVisible(true);

            if (dlg.getChoice().equals(mountain)) {
                mountain.explore(this.gameWorld.getAdventurer());
            }

       }
        gameWindow.getControlPanel().update();
    }
}
