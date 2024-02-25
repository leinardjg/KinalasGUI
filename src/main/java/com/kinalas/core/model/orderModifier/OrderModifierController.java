package com.kinalas.core.model.orderModifier;

import com.kinalas.core.database.DatabaseController;
import com.kinalas.core.model.orderable.item.Item;
import com.kinalas.core.model.orderable.item.ItemController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModifierController {
    private static final DatabaseController databaseController = DatabaseController.getInstance();
    private static final String tableName = "ItemOrderModifiers";

    public static String getTableCreatingQuery() {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id TEXT NOT NULL PRIMARY KEY,
                    name TEXT NOT NULL,
                    targetID TEXT NOT NULL,
                    multiplier REAL DEFAULT 0 NOT NULL,
                    adder REAL DEFAULT 0 NOT NULL,
                    
                    FOREIGN KEY (targetID) REFERENCES %s(id) ON DELETE CASCADE
                );
                """, tableName, ItemController.getTableName());
    }

    protected static int create(OrderModifier orderModifier) {

        String query = String.format("""
                INSERT INTO %s(id, name, targetID, multiplier, adder)
                VALUES ('%s', '%s', '%s', '%2f', '%2f')
                """, tableName, orderModifier.getId(), orderModifier.getName(), orderModifier.getTarget().getId(), orderModifier.getMultiplier(), orderModifier.getAdder());

        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static OrderModifier get(String _id) {

        String query = String.format("""
                SELECT * FROM %s
                WHERE id='%s';
                """, tableName, _id);

        try (ResultSet resultSet = databaseController.executeQuery(query)) {
            if (!resultSet.next()) return null;

            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String targetID = resultSet.getString("targetID");
            double multiplier = resultSet.getDouble("multiplier");
            double adder = resultSet.getDouble("adder");

            return new OrderModifier(id, name, Item.get(targetID), multiplier, adder);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static int delete(OrderModifier orderModifier) {

        String query = String.format("""
                DELETE FROM %s
                WHERE id='%s';
                """, tableName, orderModifier.getId());

        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
