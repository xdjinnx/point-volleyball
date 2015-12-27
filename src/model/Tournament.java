package model;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class Tournament {

    private File file;
    private ArrayList<Player> playerList = new ArrayList<Player>();

    /**
     * Constructs a tournament with zero players.
     */
    public Tournament() {}

    /**
     * Constructs a tournament and adding players from a player xml.
     * @param file A xml file constructed from this application.
     */
    public Tournament(File file) {
        this();
        this.file = file;
        load(file);
    }

    /**
     * Saves players to tournament file.
     */
    public void save() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("players");
            doc.appendChild(rootElement);

            for (Player player : playerList) {

                Element elem = doc.createElement("player");
                rootElement.appendChild(elem);

                Attr attr = doc.createAttribute("name");
                attr.setValue(player.getName());
                elem.setAttributeNode(attr);

                attr = doc.createAttribute("points");
                attr.setValue("" + player.getPoints());
                elem.setAttributeNode(attr);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(file));
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load players to tournament from file.
     * @param file A xml file constructed from this application.
     */
    public void load(File file) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList nodeList = doc.getElementsByTagName("player");

            for(int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                playerList.add(new Player(elem.getAttribute("name"), Integer.parseInt(elem.getAttribute("points"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write players and player's points to file.
     * @param file File you want to write to.
     */
    public void export(File file) {
        try {
            PrintStream ps = new PrintStream(file);
            for (Player player : getPlacementList())
                ps.println(player.getName() + " " + player.getPoints() + "points" + "\n");
            ps.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a shuffled list with players from the tournament.
     * @return The shuffled list.
     */
    public ArrayList<Team> shuffle() {

        int teamAmount = playerList.size()/6;
        teamAmount = teamAmount % 2 == 0 ? teamAmount : teamAmount + 1;
        teamAmount = teamAmount <= playerList.size()/5 ? teamAmount : teamAmount - 2;

        if(teamAmount <= 0)
            return new ArrayList<Team>();

        ArrayList<Player> shuffledList = (ArrayList<Player>) playerList.clone();
        Collections.shuffle(shuffledList, new Random(System.nanoTime()));

        ArrayList<Team> teamList = new ArrayList<Team>();
        for (int i = 0; i < teamAmount; i++)
            teamList.add(new Team((i + 1) + ""));

        for (int i = 0; i < shuffledList.size(); i++)
            teamList.get(i % teamAmount).addPlayer(shuffledList.get(i));

        return teamList;
    }

    /**
     * Get a sorted list of the players in the tournament. Sorted on their points.
     * @return Sorted player list.
     */
    public ArrayList<Player> getPlacementList() {
        ArrayList<Player> list = (ArrayList<Player>) playerList.clone();

        list.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                if(o1.getPoints() == o2.getPoints())
                    return 0;
                if(o1.getPoints() < o2.getPoints())
                    return 1;
                return -1;
            }
        });

        return list;
    }

    /**
     * Set tournament file.
     * @param file The file object you want the tournament to have.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Check if tournament object has a tournament file.
     * @return false if no file has been set. True otherwise.
     */
    public boolean hasFile() {
        return file != null;
    }

    /**
     * Get a list of the players in the tournament.
     * @return List of players.
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }


}
