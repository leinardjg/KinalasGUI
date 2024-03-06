package com.kinalas.gui.admin.components;

import com.kinalas.core.model.orderable.item.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemsTab extends Tab {

    private final ObservableList<Item> items = FXCollections.observableList(Item.getAll());

    public ItemsTab() {

        this.setText("Items");

        TableView<Item> tableView = new TableView<>();

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

        this.setContent(tableView);

    }

}
