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
        for (Player player : playerList)
            player.addPoints(points);
    }

    public Player getPlayer(int i) {
        return playerList.get(i);
    }

    public int size() {
        return playerList.size();
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<String>();
        for (Player player : playerList)
            list.add(player.toString());

        return String.join(" - ", list);
    }

}
