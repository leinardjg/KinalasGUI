package com.kinalas.gui.kinalas;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.orderable.item.Item;
import com.kinalas.gui.kinalas.components.orderTab.OrderTab;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class KinalasViewController {

    private Kinalas kinalas;
    private final int itemsNumCol = 6;
    private Item selectedOrderItem;

    @FXML private TabPane itemsTabPane;
    @FXML private TabPane ordersTabPane;
    @FXML private Label employeeInfoLabel;
    @FXML private Label timeLabel;
    @FXML private GridPane orderModifiersGridPane;

    @FXML
    private void onAddOrder() {
        kinalas.newOrder();
    }

    @FXML
    private void onDeleteOrder() {
        kinalas.getOrders().remove(kinalas.getCurrentOrder());
        ordersTabPane.getTabs().remove(ordersTabPane.getSelectionModel().getSelectedItem());
    }

    protected void initialize(Kinalas kinalas) {

        this.kinalas = kinalas;
        this.employeeInfoLabel.setText(kinalas.getEmployee().getFirstName() + " " + kinalas.getEmployee().getLastName() + " " + kinalas.getEmployee().getId());
        this.initializeTime();
        this.initializeItems();
        this.initializeOrders();

    }

    private void initializeTime() {
        this.timeLabel.setText("Live time WIP"); // todo
    }

    private void initializeItems() {
        itemsTabPane.getTabs().clear();
        ArrayList<String> itemTypes = Item.getTypes();

        for (String itemType : itemTypes) {

            Tab tab = new Tab(itemType + "s");
            itemsTabPane.getTabs().add(tab);

            List<Item> items = kinalas.getAvailableItems().stream().filter(item -> item.getType().equals(itemType)).toList();

            GridPane gridPane = new GridPane();
            HBox.setHgrow(gridPane, Priority.ALWAYS);

            gridPane.setPadding(new Insets(2));

            for (int i=0, j=0; i < items.size(); i++, j++) {

                Item item = items.get(i);

                StackPane parentPane = new StackPane();
                parentPane.setPadding(new Insets(2));

                StackPane pane = new StackPane();
                pane.setPadding(new Insets(10));
                pane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

                Text text = new Text(item.getName());
                text.setWrappingWidth(80);
                text.setTextAlignment(TextAlignment.CENTER);

                pane.getChildren().add(text);
                parentPane.getChildren().add(pane);

                GridPane.setConstraints(parentPane, i % itemsNumCol, j / itemsNumCol);

                parentPane.setOnMouseClicked(mouseEvent -> {
                    if (kinalas.getCurrentOrder() != null) {
                        kinalas.getCurrentOrder().getItems().add(Item.get(item.getId()));
                    }
                });

                gridPane.getChildren().add(parentPane);

            }

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / itemsNumCol);

            for (int i=0; i < itemsNumCol; i++) {
                gridPane.getColumnConstraints().add(columnConstraints);
            }

            tab.setContent(gridPane);
        }

    }

    private void initializeOrders() {

        ordersTabPane.getTabs().clear();
        kinalas.getOrders().addListener((ListChangeListener<Order>) change -> {
            while (change.next()) {

                if (change.wasAdded()) {
                    List<? extends Order> added = change.getAddedSubList();
                    for (Order order : added) {
                        OrderTab tab = new OrderTab(order);
                        ordersTabPane.getTabs().add(tab);
                        ordersTabPane.getSelectionModel().selectLast();
                        kinalas.setCurrentOrder(order);

                        tab.setOnSelectionChanged(event -> {
                            if (ordersTabPane.getSelectionModel().getSelectedItem().equals(tab)){
                                kinalas.setCurrentOrder(order);
                            }
                        });

                    }
                } else if (change.wasRemoved()) {
                    if (kinalas.getOrders().size() < 1) kinalas.setCurrentOrder(null);
                }

            }
        });

    }

}