package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Team {

    private ArrayList<Player> playerList = new ArrayList<Player>();
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public void givePoints(int points) {
        for (Player player : playerList)
            player.addPoints(points);
    }

    public List<Player> findPlayers(String name) {
        ArrayList<Player> list = new ArrayList<Player>();
        for (Player player : playerList)
            if (player.couldBeYou(name))
                list.add(player);

        return list;
    }

    public Player getPlayer(int i) {
        return playerList.get(i);
    }

    public String getName() {
        return name;
    }

    public int size() {
        return playerList.size();
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<String>();
        for (Player player : playerList)
            list.add(player.getName());

        return "Team " + name + ": " + String.join(", ", list);
    }

}
