package model;

public class Player {

    private String name;
    private int points;

    /**
     * Construct a player with 0 points.
     * @param name Player name.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Construct a player with a set number of points.
     * @param name Player name.
     * @param points Amount of points for player.
     */
    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Add points to player.
     * @param a amount of points you want to add to player.
     */
    public void addPoints(int a) {
        this.points += a;
    }

    /**
     * Set a new name to player.
     * @param name New name for player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set a fix number of points to a player.
     * @param points The fixed number of points.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Check if piece of string exist in players name.
     * @param name The piece of String.
     * @return True if piece exist in name if not, false.
     */
    public boolean couldBeYou(String name) {
        return this.name.toLowerCase().contains(name.toLowerCase());
    }

    /**
     * Get player points.
     * @return The players points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Get players name.
     * @return The players name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ", " + points + " points";
    }
}
