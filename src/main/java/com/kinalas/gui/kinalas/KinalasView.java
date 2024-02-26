package com.kinalas.gui.kinalas;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.gui.KinalasGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KinalasView {

    private final Stage stage;
    private final Kinalas kinalas;

    public KinalasView(Stage stage, Kinalas kinalas) {
        this.stage = stage;
        this.kinalas = kinalas;
    }

    public void start() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(KinalasGUI.class.getResource("kinalas-view.fxml"));
        ((KinalasViewController) fxmlLoader.getController()).setKinalas(this.kinalas);

        stage.setResizable(false);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("KinalasView");
        stage.setScene(scene);
        stage.showAndWait();
    }

}