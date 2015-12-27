package util;

import javafx.scene.control.Alert;

public class AlertBox {

    /**
     * Shows a JavaFX Alert window.
     * @param title Title on window
     * @param content Text shown for the user.
     */
    public static void show(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
