package com.kinalas.gui.admin.components;

import com.kinalas.core.model.employee.Employee;
import com.kinalas.core.model.orderable.item.Item;
import com.kinalas.gui.KinalasGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemsTab extends Tab {

    private final ObservableList<Item> items = FXCollections.observableList(Item.getAll());

    public ItemsTab() {

        this.setText("Items");

        TableView<Item> tableView = new TableView<>();
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TableColumn<Item, String> itemIDColumn = new TableColumn<>("Item ID");
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableView.getColumns().add(itemIDColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(descriptionColumn);
        tableView.getColumns().add(priceColumn);
        tableView.getColumns().add(typeColumn);

        tableView.setItems(items);
        tableView.getColumns().forEach(column -> column.setReorderable(false));

        Label label = new Label("Items");
        label.setStyle("-fx-font-size: 20");

        Hyperlink link = new Hyperlink("Create new");
        link.setPadding(new Insets(0, 8, 0, 8));

        HBox hBox = new HBox(label, link);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.setPadding(new Insets(16, 4, 16, 4));

        this.setContent(new VBox(hBox, tableView));

        // link on click
        link.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(KinalasGUI.class.getResource("admin/create/item/create-item.fxml"));
            stage.setResizable(false);
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Create new item");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.showAndWait();

            items.clear();
            items.addAll(Item.getAll());

        });

    }

}
