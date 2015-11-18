package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import model.Player;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Peter on 2015-11-15.
 */
public class ConfigController {

    private ObservableList<Player> content;

    @FXML private ListView playerListView;

    public void setup(ArrayList<Player> playerList) {
        content = FXCollections.observableList(playerList);
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
    protected void editPlayerButtonAction() {

        try {
            TextInputDialog dialog = new TextInputDialog(((Player) playerListView.getSelectionModel().getSelectedItem()).toString());
            dialog.setTitle("Edit player");
            dialog.setHeaderText(null);
            dialog.setContentText("Edit name and etc:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent())
                ((Player) playerListView.getSelectionModel().getSelectedItem()).setName(result.get());

            playerListView.refresh();
        } catch (NullPointerException e) {}
    }

}
