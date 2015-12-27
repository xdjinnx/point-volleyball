package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import model.Player;
import util.AlertBox;

import java.util.ArrayList;
import java.util.Optional;

public class ConfigController {

    private ObservableList<Player> content;

    @FXML private ListView playerListView;

    public void setup(ArrayList<Player> playerList) {
        content = FXCollections.observableList(playerList);
        playerListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        playerListView.setItems(content);
    }

    @FXML
    protected void addPlayerButtonAction() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add player");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter name and etc:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
            content.add(new Player(result.get()));
    }

    @FXML
    protected void editPlayerNameButtonAction() {

        Player player = (Player) playerListView.getSelectionModel().getSelectedItem();
        if(player == null) {
            AlertBox.show("Info", "Select a player!");
            return;
        }

        try {
            TextInputDialog dialog = new TextInputDialog(player.getName());
            dialog.setTitle("Edit player name");
            dialog.setHeaderText(null);
            dialog.setContentText("Edit name:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent())
                ((Player) playerListView.getSelectionModel().getSelectedItem()).setName(result.get());

            playerListView.refresh();
        } catch (NullPointerException e) {}
    }

    @FXML
    protected void editPlayerPointsButtonAction() {

        Player player = (Player)playerListView.getSelectionModel().getSelectedItem();
        if(player == null) {
            AlertBox.show("Info", "Select a player!");
            return;
        }

        try {
            TextInputDialog dialog = new TextInputDialog(player.getPoints() + "");
            dialog.setTitle("Edit player points");
            dialog.setHeaderText(null);
            dialog.setContentText("Edit points for " + player.getName() + ":");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent())
                ((Player) playerListView.getSelectionModel().getSelectedItem()).setPoints(Integer.parseInt(result.get()));

            playerListView.refresh();
        } catch (NullPointerException e) {}
    }

    @FXML
    protected void searchPlayerButtonAction() {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Search for player");
        dialog.setHeaderText(null);
        dialog.setContentText("Search for player:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            playerListView.requestFocus();
            for (int i = 0; i < content.size(); i++)
                if(content.get(i).couldBeYou(result.get())) {
                    playerListView.getSelectionModel().select(i);
                    playerListView.getFocusModel().focus(i);
                }
        }
    }

    @FXML
    protected void removePlayerButtonAction() {
        content.remove(playerListView.getSelectionModel().getSelectedItem());
    }

}
