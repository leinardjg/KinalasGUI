package com.kinalas.gui.kinalas;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.orderModifier.OrderModifier;
import com.kinalas.core.model.orderable.item.Item;
import com.kinalas.gui.kinalas.components.orderTab.OrderItem;
import com.kinalas.gui.kinalas.components.orderTab.OrderTab;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KinalasViewController {

    private enum Mode {
        DEFAULT,
        ADD,
        NO,
        PICK
    }

    private Kinalas kinalas;
    private final int itemsNumCol = 6;
    private Item selectedOrderItem;
    private Mode mode = Mode.DEFAULT;

    @FXML private TabPane itemsTabPane;
    @FXML private TabPane ordersTabPane;
    @FXML private Label employeeInfoLabel;
    @FXML private Label timeLabel;
    @FXML private GridPane orderModifiersGridPane;

    @FXML
    private void onRemoveSelectedItems() {
        kinalas.getCurrentOrder().getItems().removeAll(kinalas.getSelectedItems());
    }

    @FXML
    private void onCheckout() {
        Order order = kinalas.getCurrentOrder();
        if (order.create()) {
            kinalas.getCheckedOutOrders().add(order);
            kinalas.getOrders().remove(order);
        }
    }

    @FXML
    private void onAddOrder() {
        kinalas.newOrder();
    }

    @FXML
    private void onDeleteOrder() {
        if (kinalas.getOrders().size() > 1) {
            kinalas.getOrders().remove(kinalas.getCurrentOrder());
            ordersTabPane.getTabs().remove(ordersTabPane.getSelectionModel().getSelectedItem());
        }
    }

    protected void initialize(Kinalas kinalas) {

        this.kinalas = kinalas;
        this.employeeInfoLabel.setText(kinalas.getEmployee().getFirstName() + " " + kinalas.getEmployee().getLastName() + " " + kinalas.getEmployee().getId());
        this.initializeTime();
        this.initializeItems();
        this.initializeOrders();
        this.initializeOrderModifiers();

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
                        if (mode == Mode.DEFAULT) kinalas.getCurrentOrder().getItems().add(Item.get(item.getId()));
                        else {
                            for (Item selectedItem : kinalas.getSelectedItems()) {
                                switch (mode) {
                                    case ADD -> selectedItem.getModifiers().add(new OrderModifier("Add", item, 1, 0));
                                    case NO -> selectedItem.getModifiers().add(new OrderModifier("No", item, 0, 0));
                                    case PICK -> selectedItem.getModifiers().add(new OrderModifier("is", item, 0, 0));
                                }
                            }
                        }
                    }
                    mode = Mode.DEFAULT;
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

                    for (Order order : change.getRemoved()) {
                        ordersTabPane.getTabs().removeIf(tab -> Objects.equals(((OrderTab) tab).getOrder().getId(), order.getId()));
                    }

                    ordersTabPane.getSelectionModel().selectLast();
                }

            }
        });

        kinalas.newOrder();

    }

    private void initializeOrderModifiers() {

        HBox.setHgrow(orderModifiersGridPane, Priority.ALWAYS);
        orderModifiersGridPane.setPadding(new Insets(2));

        ToggleButton addPane = new ToggleButton("add");
        ToggleButton noPane = new ToggleButton("no");
        ToggleButton pickPane = new ToggleButton("pick");

        addPane.setPadding(new Insets(2));
        noPane.setPadding(new Insets(2));
        pickPane.setPadding(new Insets(2));

        addPane.setPrefHeight(64);
        noPane.setPrefHeight(64);
        pickPane.setPrefHeight(64);

        orderModifiersGridPane.add(addPane, 0, 0);
        orderModifiersGridPane.add(noPane, 1, 0);
        orderModifiersGridPane.add(pickPane, 2, 0);

        addPane.setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) {
                mode = Mode.ADD;
                addPane.setSelected(true);
            } else {
                addPane.setSelected(false);
            }
        });
        noPane.setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) {
                mode = Mode.NO;
                noPane.setSelected(true);
            } else {
                noPane.setSelected(false);
            }
        });
        pickPane.setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) {
                mode = Mode.PICK;
                pickPane.setSelected(true);
            } else {
                pickPane.setSelected(false);
            }
        });

    }

}