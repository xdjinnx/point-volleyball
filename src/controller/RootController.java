package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Player;
import model.Team;
import model.Tournament;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class RootController {

    @FXML private MenuItem saveMenu;
    @FXML private ListView teamListView;
    @FXML private Button configPlayersButton;
    @FXML private Button givePointsButton;

    private Tournament tournament;

    @FXML
    protected void newMenuAction() {
        tournament = new Tournament();
        unlockApplication();
    }

    @FXML
    protected void openMenuAction() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            tournament = new Tournament(file);
        }
        unlockApplication();
    }

    @FXML
    protected void saveMenuAction() {
        if(!tournament.hasFile()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save tournament");
            fileChooser.setInitialFileName("tournament.xml");
            File file = fileChooser.showSaveDialog(new Stage());
            tournament.setFile(file);
        }
        tournament.save();
    }

    @FXML
    protected void aboutMenuAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Created by Peter Lundberg");
        alert.showAndWait();
    }

    @FXML
    protected void exitMenuAction() {
        System.exit(0);
    }

    @FXML
    protected void shuffleTeamsButtonAction() {
        ArrayList<Team> teamList = tournament.shuffle();
        ObservableList<Team> content = FXCollections.observableList(teamList);
        teamListView.setItems(content);

        givePointsButton.setDisable(false);
    }

    @FXML
    protected void configPlayersButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/config.fxml"));
            Parent root = loader.load();
            ConfigController controller = loader.getController();
            controller.setup(tournament.getPlayerList());
            Stage stage = new Stage();
            stage.setTitle("Config");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void givePointsButtonAction() {

        Team team = ((Team) teamListView.getSelectionModel().getSelectedItem());

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Give points");
        dialog.setHeaderText(null);
        dialog.setContentText("Give points to: " + team);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
            team.givePoints(Integer.parseInt(result.get()));
    }

    private void unlockApplication() {
        saveMenu.setDisable(false);
        configPlayersButton.setDisable(false);
    }

}
