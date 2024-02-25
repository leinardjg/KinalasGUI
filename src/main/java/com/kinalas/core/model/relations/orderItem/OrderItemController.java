package com.kinalas.core.model.relations.orderItem;

import com.kinalas.core.database.DatabaseController;
import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.order.OrderController;
import com.kinalas.core.model.orderable.item.Item;
import com.kinalas.core.model.orderable.item.ItemController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemController {

    private static final DatabaseController databaseController = DatabaseController.getInstance();
    private static final String tableName = "OrderItems";

    public static String getTableName() {
        return tableName;
    }

    public static String getTableCreatingQuery() {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s(
                orderID TEXT NOT NULL,
                itemID TEXT NOT NULL,
                
                FOREIGN KEY (orderID) REFERENCES %s(id) ON DELETE CASCADE,
                FOREIGN KEY (itemID) REFERENCES %s(id) ON DELETE CASCADE
                );
                """, tableName, OrderController.tableName, ItemController.getTableName());
    }

    public static int create(Order order, Item item) {

        String query = String.format("""
                INSERT INTO %s (orderID, itemID)
                VALUES ('%s', '%s')
                """, tableName, order.getId(), item.getId());
        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ArrayList<Item> getMany(String orderID) {

        String query = String.format("""
                SELECT itemID FROM %s
                WHERE orderID='%s';
                """, tableName, orderID);
        try (ResultSet resultSet = databaseController.executeQuery(query)) {

            ArrayList<String> itemIDs = new ArrayList<>();
            ArrayList<Item> items = new ArrayList<>();

            while (resultSet.next()) {
                itemIDs.add(resultSet.getString("itemID"));
            }

            itemIDs.forEach(id -> {
                items.add(Item.get(id));
            });

            return items;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
