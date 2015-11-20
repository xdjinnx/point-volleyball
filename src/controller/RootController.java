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
    private ObservableList<Team> content;

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
        if(!tournament.hasFile())
            tournament.setFile(getFileFromDir("Save tournament", "tournament.xml"));
        tournament.save();
    }

    @FXML
    protected void aboutMenuAction() {
        alertBox("About", "Created by Peter Lundberg");
    }

    @FXML
    protected void exportMenuAction() {
        tournament.export(getFileFromDir("Export .txt", "scorelist.txt"));
    }

    @FXML
    protected void exitMenuAction() {
        System.exit(0);
    }

    @FXML
    protected void shuffleTeamsButtonAction() {
        ArrayList<Team> teamList = tournament.shuffle();
        content = FXCollections.observableList(teamList);
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
            stage.setScene(new Scene(root, 475, 475));
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

    @FXML
    protected void searchPlayerButtonAction() {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Search for player");
        dialog.setHeaderText(null);
        dialog.setContentText("Search for player:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
            for (Team team : content)
                for (Player player : team.findPlayers(result.get())) {
                    alertBox("Found player/s", player.getName() + " found in team " + team.getName());
                }
    }

    private void alertBox(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private File getFileFromDir(String title, String defaultFileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialFileName(defaultFileName);

        return fileChooser.showSaveDialog(new Stage());
    }

    private void unlockApplication() {
        saveMenu.setDisable(false);
        configPlayersButton.setDisable(false);
    }

}
