package model;

public class Player {

    private String name;
    private int points;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public void addPoints(int a) {
        this.points += a;
    }

    public void reset() {
        this.points = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean couldBeYou(String name) {
        return this.name.toLowerCase().contains(name.toLowerCase());
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ", " + points + " points";
    }
}
