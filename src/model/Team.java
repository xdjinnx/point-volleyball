package model;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private ArrayList<Player> playerList = new ArrayList<Player>();
    private String name;

    /**
     * Constructs an empty team.
     * @param name Team name.
     */
    public Team(String name) {
        this.name = name;
    }

    /**
     * Adds a reference to a player.
     * @param player The player.
     */
    public void addPlayer(Player player) {
        playerList.add(player);
    }

    /**
     * Give all players in this team points
     * @param points The amount of points you want to add.
     */
    public void givePoints(int points) {
        for (Player player : playerList)
            player.addPoints(points);
    }

    /**
     * Check for players in this team that has a name containing input param String.
     * @param name Piece of a name looking for.
     * @return
     */
    public List<Player> findPlayers(String name) {
        ArrayList<Player> list = new ArrayList<Player>();
        for (Player player : playerList)
            if (player.couldBeYou(name))
                list.add(player);

        return list;
    }

    /**
     * Get a player located at a certain index.
     * @param i Index for list in team.
     * @return The player indexing for.
     */
    public Player getPlayer(int i) {
        return playerList.get(i);
    }

    /**
     * Get team name.
     * @return Team name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get team size.
     * @return Team size.
     */
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
