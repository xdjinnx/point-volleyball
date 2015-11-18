package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Peter on 2015-11-18.
 */
public class Team {

    ArrayList<Player> playerList = new ArrayList<Player>();

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public void givePoints(int points) {
        Iterator<Player> iterator = playerList.iterator();

        while(iterator.hasNext()) {
            iterator.next().addPoints(points);
        }
    }

    public Player getPlayer(int i) {
        return playerList.get(i);
    }

    public int size() {
        return playerList.size();
    }

}
