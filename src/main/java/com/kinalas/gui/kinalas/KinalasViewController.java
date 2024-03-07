package com.kinalas.gui.kinalas;

import com.kinalas.core.kinalas.Kinalas;
import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.orderModifier.OrderModifier;
import com.kinalas.core.model.orderable.item.Item;
import com.kinalas.gui.kinalas.components.orderModifiersGridPane.OrderModifierButton;
import com.kinalas.gui.kinalas.components.orderTab.OrderTab;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
        FREE,
        PICK,
        EXTRA,

        NO, _25, _50, _75
    }

    private Kinalas kinalas;
    private final int itemsNumCol = 4;
    private Mode mode = Mode.DEFAULT;
    private final ArrayList<OrderModifierButton> orderModifierButtons = new ArrayList<>();

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
        }
    }

    @FXML
    private void initialize() {

        this.kinalas = Kinalas.getInstance();
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

                pane.setMinHeight(72);

                GridPane.setConstraints(parentPane, i % itemsNumCol, j / itemsNumCol);

                parentPane.setOnMouseClicked(mouseEvent -> {
                    if (kinalas.getCurrentOrder() != null) {
                        if (mode == Mode.DEFAULT) kinalas.getCurrentOrder().getItems().add(Item.get(item.getId()));
                        else {
                            for (Item selectedItem : kinalas.getSelectedItems()) {
                                switch (mode) {
                                    case ADD -> selectedItem.getModifiers().add(new OrderModifier("Add", item, 1, 0));
                                    case PICK -> selectedItem.getModifiers().add(new OrderModifier("Pick", item, 0, 0));
                                    case NO -> selectedItem.getModifiers().add(new OrderModifier("No", item, 0, 0));
                                    case EXTRA -> selectedItem.getModifiers().add(new OrderModifier("Extra", item, 0.5, 0));
                                    case _25 -> selectedItem.getModifiers().add(new OrderModifier("25%", item, 0, 0));
                                    case _50 -> selectedItem.getModifiers().add(new OrderModifier("50%", item, 0, 0));
                                    case _75 -> selectedItem.getModifiers().add(new OrderModifier("75%", item, 0, 0));
                                }
                            }
                        }
                    }
                });

                gridPane.getChildren().add(parentPane);

            }

            for (int i=0; i < itemsNumCol; i++) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setPercentWidth(100.0 / itemsNumCol);
                gridPane.getColumnConstraints().add(columnConstraints);
            }

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(gridPane);
            scrollPane.setFitToWidth(true);
            tab.setContent(scrollPane);
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

        kinalas.getSelectedItems().addListener((ListChangeListener<Item>) change -> {
            if (change.getList().size() < 1) setMode(Mode.DEFAULT);
        });

        kinalas.newOrder();

    }

    private void initializeOrderModifiers() {

        HBox.setHgrow(orderModifiersGridPane, Priority.ALWAYS);

        OrderModifierButton addButton = new OrderModifierButton("ADD");
        addButton.getToggleButton().setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) setMode(Mode.ADD);
            addButton.setSelected(kinalas.getSelectedItems().size() > 0);
        });

        OrderModifierButton freeButton = new OrderModifierButton("FREE");
        freeButton.getToggleButton().setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) setMode(Mode.FREE);
            freeButton.setSelected(kinalas.getSelectedItems().size() > 0);
        });

        OrderModifierButton extraButton = new OrderModifierButton("EXTRA");
        extraButton.getToggleButton().setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) setMode(Mode.EXTRA);
            extraButton.setSelected(kinalas.getSelectedItems().size() > 0);
        });

        OrderModifierButton pickButton = new OrderModifierButton("PICK");
        pickButton.getToggleButton().setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) setMode(Mode.PICK);
            pickButton.setSelected(kinalas.getSelectedItems().size() > 0);
        });

        OrderModifierButton noButton = new OrderModifierButton("NO");
        noButton.getToggleButton().setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) setMode(Mode.NO);
            noButton.setSelected(kinalas.getSelectedItems().size() > 0);
        });

        OrderModifierButton _25Button = new OrderModifierButton("25%");
        _25Button.getToggleButton().setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) setMode(Mode._25);
            _25Button.setSelected(kinalas.getSelectedItems().size() > 0);
        });

        OrderModifierButton _50Button = new OrderModifierButton("50%");
        _50Button.getToggleButton().setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) setMode(Mode._50);
            _50Button.setSelected(kinalas.getSelectedItems().size() > 0);
        });

        OrderModifierButton _75Button = new OrderModifierButton("75%");
        _75Button.getToggleButton().setOnMouseClicked(mouseEvent -> {
            if (kinalas.getSelectedItems().size() > 0) setMode(Mode._75);
            _75Button.setSelected(kinalas.getSelectedItems().size() > 0);
            System.out.println(mode);
        });

        orderModifiersGridPane.add(addButton, 0, 0);
        orderModifiersGridPane.add(pickButton, 1, 0);
        orderModifiersGridPane.add(freeButton, 2, 0);
        orderModifiersGridPane.add(extraButton, 3, 0);
        orderModifiersGridPane.add(noButton, 0, 1);
        orderModifiersGridPane.add(_25Button, 1, 1);
        orderModifiersGridPane.add(_50Button, 2, 1);
        orderModifiersGridPane.add(_75Button, 3, 1);

        orderModifierButtons.add(addButton);
        orderModifierButtons.add(pickButton);
        orderModifierButtons.add(freeButton);
        orderModifierButtons.add(extraButton);
        orderModifierButtons.add(noButton);
        orderModifierButtons.add(_25Button);
        orderModifierButtons.add(_50Button);
        orderModifierButtons.add(_75Button);

    }

    // #region getters and setters

    private void setMode(Mode mode) {

        for (OrderModifierButton button : orderModifierButtons) {
            button.setSelected(false);
        }

        this.mode = mode;
    }

    // #endregion

}