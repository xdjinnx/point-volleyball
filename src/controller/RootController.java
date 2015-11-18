package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Team;
import model.Tournament;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RootController {

    @FXML private MenuItem saveMenu;
    @FXML private Button configPlayersButton;

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
        for(Team t : teamList) {
            for (int i = 0; i < t.size(); i++) {
                System.out.println(t.getPlayer(i));
            }
            System.out.println();
        }
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
    protected void submitButtonAction() {
    }

    private void unlockApplication() {
        saveMenu.setDisable(false);
        configPlayersButton.setDisable(false);
    }

}
