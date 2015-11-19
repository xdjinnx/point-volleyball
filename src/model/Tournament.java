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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Peter on 2015-11-15.
 */
public class Tournament {

    private File file;
    private ArrayList<Player> playerList = new ArrayList<Player>();

    public Tournament() {}

    public Tournament(File file) {
        this();
        this.file = file;
        load(file);
    }

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

    public ArrayList<Team> shuffle() {

        int teamAmount = playerList.size()/6;
        teamAmount = teamAmount % 2 == 0 ? teamAmount : teamAmount + 1;
        teamAmount = teamAmount <= playerList.size()/5 ? teamAmount : teamAmount - 2;

        ArrayList<Player> shuffledList = (ArrayList<Player>) playerList.clone();
        Collections.shuffle(shuffledList, new Random(System.nanoTime()));

        ArrayList<Team> teamList = new ArrayList<Team>();
        for (int i = 0; i < teamAmount; i++)
            teamList.add(new Team((i + 1) + ""));

        for (int i = 0; i < shuffledList.size(); i++)
            teamList.get(i % teamAmount).addPlayer(shuffledList.get(i));

        return teamList;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean hasFile() {
        return file != null;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }


}
